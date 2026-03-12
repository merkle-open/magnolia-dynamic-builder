package com.merkle.oss.magnolia.appbuilder.action.edit;

import info.magnolia.cms.security.AccessManager;
import info.magnolia.cms.security.Permission;
import info.magnolia.context.Context;
import info.magnolia.i18nsystem.I18nizer;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.ui.UIComponent;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.i18n.I18NAuthoringSupport;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.datasource.jcr.JcrDatasource;
import info.magnolia.ui.dialog.DialogDefinitionRegistry;
import info.magnolia.ui.dialog.FormDialogDefinition;
import info.magnolia.ui.editor.LocaleContext;
import info.magnolia.ui.editor.validator.NodeNameValidatorDefinition;

import java.lang.invoke.MethodHandles;

import javax.jcr.Node;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.merkle.oss.magnolia.appbuilder.JcrNameValidationAppender;
import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.OpenDialogAction;
import com.merkle.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import com.merkle.oss.magnolia.formbuilder.RootFormView;

import jakarta.annotation.Nullable;
import jakarta.inject.Inject;
import jakarta.inject.Provider;

public class EditAppActionDefinition implements AppActionDefinition {
	public static final EditAppActionDefinition FOLDER = new EditAppActionDefinition(
			"editFolder",
			"ui-framework-jcr:rename",
			MagnoliaIcons.EDIT.getCssClass(),
			RootFormView.ViewType.EDIT,
			"actions.editFolder"
	);
	private final String name;
	private final String dialogId;
	private final String icon;
    private final RootFormView.ViewType viewType;
    @Nullable
	private final String label;

	public EditAppActionDefinition(final String name, final String dialogId) {
		this(name, dialogId, MagnoliaIcons.EDIT.getCssClass(), RootFormView.ViewType.EDIT, null);
	}
	public EditAppActionDefinition(final String name, final String dialogId, final RootFormView.ViewType viewType) {
		this(name, dialogId, MagnoliaIcons.EDIT.getCssClass(), viewType, null);
	}
	public EditAppActionDefinition(final String name, final String dialogId, final String icon, final RootFormView.ViewType viewType, @Nullable final String label) {
		this.name = name;
		this.dialogId = dialogId;
		this.icon = icon;
        this.viewType = viewType;
        this.label = label;
	}

	@Override
	public OpenDialogAction.Definition action(final DropConstraintDefinition dropConstraint) {
		final OpenDialogAction.Definition definition = new OpenDialogAction.Definition(NodeNameValidatorDefinition.Mode.EDIT);
		definition.setImplementationClass(PermissionBasedOpenDialogAction.class);
		definition.setName(name);
		definition.setDialogId(dialogId);
		definition.setLabel(label);
		definition.setIcon(icon);
		definition.setViewType(viewType);
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.rule(new JcrIsNotDeletedRuleDefinition())
				.rule(new PermissionRequiredRuleDefinition(Permission.READ))
				.build()
		);
		return definition;
	}

	public static class PermissionBasedOpenDialogAction extends OpenDialogAction {
		private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
		private final Definition definition;
		private final ValueContext<Node> valueContext;
		private final Provider<Context> contextProvider;

		@Inject
		public PermissionBasedOpenDialogAction(
				final OpenDialogAction.Definition definition,
				final LocaleContext localeContext,
				final ValueContext<Node> valueContext,
				final JcrDatasource jcrDatasource,
				final UIComponent parentView,
				final I18NAuthoringSupport<Node> i18NAuthoringSupport,
				final DialogDefinitionRegistry dialogDefinitionRegistry,
				final I18nizer i18nizer,
				final JcrNameValidationAppender jcrNameValidationAppender,
				final Provider<Context> contextProvider
		) {
			super(definition, localeContext, valueContext, jcrDatasource, parentView, i18NAuthoringSupport, dialogDefinitionRegistry, i18nizer, jcrNameValidationAppender);
			this.definition = definition;
			this.valueContext = valueContext;
			this.contextProvider = contextProvider;
		}

		@Override
		protected FormDialogDefinition<Node> apply(final FormDialogDefinition<Node> formDialog) {
			if(formDialog.getForm() instanceof RootFormView.Definition<Node> rootFormView) {
				rootFormView.setViewType(resolveViewType(definition.getViewType(), valueContext.getSingle().orElse(null)));
			}
			return formDialog;
		}

		private RootFormView.ViewType resolveViewType(final RootFormView.ViewType viewType, @Nullable final Node node) {
			if(RootFormView.ViewType.EDIT.equals(viewType) && !hasEditPermissions(node)) {
				return RootFormView.ViewType.VIEW;
			}
			return viewType;
		}

		private boolean hasEditPermissions(@Nullable final Node node) {
			try {
				final AccessManager ami = contextProvider.get().getAccessManager(node.getSession().getWorkspace().getName());
				return ami.isGranted(node.getPath(), Permission.SET);
			} catch (final Exception e) {
				LOG.error("Failed to evaluate if user has permissions for {}! returning false", NodeUtil.getPathIfPossible(node), e);
			}
			return false;
		}
	}
}
