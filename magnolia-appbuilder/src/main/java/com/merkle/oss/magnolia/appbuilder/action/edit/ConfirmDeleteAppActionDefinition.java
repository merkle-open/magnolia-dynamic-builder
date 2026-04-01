package com.merkle.oss.magnolia.appbuilder.action.edit;

import info.magnolia.cms.security.Permission;
import info.magnolia.context.Context;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.app.SubAppContext;
import info.magnolia.ui.api.context.UiContext;
import info.magnolia.ui.contentapp.action.DeleteNodesConfirmationAction;
import info.magnolia.ui.contentapp.action.DeleteNodesConfirmationActionDefinition;
import info.magnolia.ui.contentapp.browser.ActionExecutionService;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.datasource.jcr.JcrDatasourceDefinition;
import info.magnolia.ui.warp.service.IframeOverlayComponentFactory;
import info.magnolia.ui.warp.service.IframeOverlayComponentStateUpdater;
import info.magnolia.warp.component.IframeOverlayComponent;

import java.util.Optional;
import java.util.Set;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import com.vaadin.ui.Component;
import com.vaadin.ui.ComponentContainer;

import jakarta.inject.Inject;

public class ConfirmDeleteAppActionDefinition implements AppActionDefinition {
	private final String icon;
    private boolean warpDialogDisabled;

    public ConfirmDeleteAppActionDefinition() {
		this(MagnoliaIcons.DELETE.getCssClass());
	}

	public ConfirmDeleteAppActionDefinition(final String icon) {
		this.icon = icon;
	}

	public ConfirmDeleteAppActionDefinition warpDialogDisabled(final boolean warpDialogDisabled) {
        this.warpDialogDisabled = warpDialogDisabled;
		return this;
    }

	@Override
	public DeleteNodesConfirmationActionDefinition action(final DropConstraintDefinition dropConstraint) {
		final DeleteNodesConfirmationActionDefinition definition = new DeleteNodesConfirmationActionDefinition();
		if(warpDialogDisabled) {
			definition.setImplementationClass(LegacyDeleteNodesConfirmationAction.class);
		}
		definition.setName("confirmDelete");
		definition.setLabel("actions.confirmDeletion");
		definition.setConfirmationHeader("actions.confirmDeletion.confirmationHeader");
		definition.setConfirmationMessage("actions.confirmDeletion.confirmationMessage");
		definition.setProceedLabel("actions.confirmDeletion.proceedLabel");
		definition.setCancelLabel("actions.confirmDeletion.cancelLabel");
		definition.setIcon(icon);
		definition.setSuccessActionName("delete");
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.rule(new JcrIsNotDeletedRuleDefinition())
				.rule(new PermissionRequiredRuleDefinition(Permission.REMOVE))
				.build()
		);
		return definition;
	}

	@Override
	public boolean multiple() {
		return true;
	}

	public static class LegacyDeleteNodesConfirmationAction<D extends DeleteNodesConfirmationActionDefinition> extends DeleteNodesConfirmationAction<D> {
		@Inject
		public LegacyDeleteNodesConfirmationAction(
				final D definition,
				final UiContext uiContext,
				final ActionExecutionService actionExecutionService,
				final SimpleTranslator i18n,
				final ValueContext valueContext,
				final JcrDatasourceDefinition datasourceDefinition,
				final IgnoreIframeOverlayComponentFactory iframeOverlayComponentFactory,
				final SubAppContext subAppContext
		) {
			super(definition, uiContext, actionExecutionService, i18n, valueContext, datasourceDefinition, iframeOverlayComponentFactory, subAppContext);
		}

		public static class IgnoreIframeOverlayComponentFactory<T> extends IframeOverlayComponentFactory<T> {
			@Inject
			public IgnoreIframeOverlayComponentFactory(final Set<IframeOverlayComponentStateUpdater> set, ComponentProvider componentProvider) {
				super(set, componentProvider);
			}
			@Override
			public Optional<Component> initializeOverlayComponent(final ComponentContainer componentContainer, final ValueContext<T> valueContext, final IframeOverlayComponent.IframeInfo iframeInfo, final Context context) {
				return Optional.empty();
			}
		}
	}
}
