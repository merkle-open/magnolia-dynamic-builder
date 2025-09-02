package com.merkle.oss.magnolia.appbuilder.action;

import info.magnolia.config.registry.Registry;
import info.magnolia.i18nsystem.I18nizer;
import info.magnolia.ui.UIComponent;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.i18n.I18NAuthoringSupport;
import info.magnolia.ui.contentapp.action.CloseActionDefinition;
import info.magnolia.ui.contentapp.action.CommitActionDefinition;
import info.magnolia.ui.datasource.jcr.JcrDatasource;
import info.magnolia.ui.dialog.DialogDefinition;
import info.magnolia.ui.dialog.DialogDefinitionRegistry;
import info.magnolia.ui.dialog.FormDialogDefinition;
import info.magnolia.ui.dialog.actions.OpenDialogActionDefinition;
import info.magnolia.ui.editor.LocaleContext;
import info.magnolia.ui.editor.validator.NodeNameValidatorDefinition;

import java.util.Optional;

import javax.jcr.Node;

import com.merkle.oss.magnolia.appbuilder.JcrNameValidationAppender;

import jakarta.annotation.Nullable;
import jakarta.inject.Inject;

/**
 * Adds a nodeNameValidator to the field jcrName if it is present in the dialog (must be unique) and allows overriding commit/close actions.
 */
public class OpenDialogAction extends info.magnolia.ui.dialog.actions.OpenDialogAction<Node> {
	private final Definition definition;
	private final LocaleContext localeContext;
	private final ValueContext<Node> valueContext;
	private final JcrDatasource jcrDatasource;
	private final I18NAuthoringSupport<Node> i18NAuthoringSupport;
    private final JcrNameValidationAppender jcrNameValidationAppender;

    @Inject
	public OpenDialogAction(
			final Definition definition,
			final LocaleContext localeContext,
			final ValueContext<Node> valueContext,
			final JcrDatasource jcrDatasource,
			final UIComponent parentView,
			final I18NAuthoringSupport<Node> i18NAuthoringSupport,
			final DialogDefinitionRegistry dialogDefinitionRegistry,
			final I18nizer i18nizer,
			final JcrNameValidationAppender jcrNameValidationAppender
	) {
		super(definition, localeContext, valueContext, parentView, i18NAuthoringSupport, dialogDefinitionRegistry, i18nizer);
		this.definition = definition;
		this.localeContext = localeContext;
		this.valueContext = valueContext;
		this.jcrDatasource = jcrDatasource;
		this.i18NAuthoringSupport = i18NAuthoringSupport;
        this.jcrNameValidationAppender = jcrNameValidationAppender;
	}

	@Override
	protected FormDialogDefinition<Node> getDialogDefinition(final DialogDefinitionRegistry dialogDefinitionRegistry, final I18nizer i18nizer) {
		final DialogDefinition dialogDefinition = dialogDefinitionRegistry.getProvider(getDefinition().getDialogId()).get();
		if (!(dialogDefinition instanceof FormDialogDefinition)) {
			throw new Registry.InvalidDefinitionException("Provided dialog id is not a form dialog!");
		}
		final FormDialogDefinition<Node> formDialogDefinition = (FormDialogDefinition<Node>) dialogDefinition;
		jcrNameValidationAppender.addNodeNameValidatorToJcrNameField(formDialogDefinition.getForm(), definition.getMode());
		Optional.ofNullable(definition.getCustomCommitAction()).ifPresent(commit ->
				formDialogDefinition.getActions().put(CommitActionDefinition.COMMIT_ACTION_NAME, commit) //we can't generate a new map due to byteBuddy (I18nizer), but since it is a mutable hash map this is fine
		);
		Optional.ofNullable(definition.getCustomCloseAction()).ifPresent(close ->
				formDialogDefinition.getActions().put(CloseActionDefinition.CLOSE_ACTION_NAME, close) //we can't generate a new map due to byteBuddy (I18nizer), but since it is a mutable hash map this is fine
		);

		if (valueContext.getSingle().isEmpty() && (!localeContext.isPopulated() || formDialogDefinition.getForm().hasI18NProperties())) {
			localeContext.populateFromI18NAuthoringSupport(jcrDatasource.getRoot(), i18NAuthoringSupport);
		}
		return i18nizer.decorate(formDialogDefinition);
	}

	public static class Definition extends OpenDialogActionDefinition {
		private final NodeNameValidatorDefinition.Mode mode;
		@Nullable
		private CommitActionDefinition customCommitAction;
		@Nullable
		private CloseActionDefinition customCloseAction;

		public Definition(final NodeNameValidatorDefinition.Mode mode) {
			this.mode = mode;
			setPopulate(mode == NodeNameValidatorDefinition.Mode.EDIT);
            setImplementationClass(OpenDialogAction.class);
		}

		public NodeNameValidatorDefinition.Mode getMode() {
			return mode;
		}

		@Nullable //Optional doesn't work with byteBuddy
		public CommitActionDefinition getCustomCommitAction() {
			return customCommitAction;
		}

		public void setCustomCommitAction(final CommitActionDefinition customCommitAction) {
			this.customCommitAction = customCommitAction;
		}

		@Nullable //Optional doesn't work with byteBuddy
		public CloseActionDefinition getCustomCloseAction() {
			return customCloseAction;
		}

		public void setCustomCloseAction(final CloseActionDefinition customCloseAction) {
			this.customCloseAction = customCloseAction;
		}
	}
}
