package com.namics.oss.magnolia.appbuilder.action.add;

import info.magnolia.i18nsystem.I18nizer;
import info.magnolia.ui.UIComponent;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.i18n.I18NAuthoringSupport;
import info.magnolia.ui.dialog.DialogDefinition;
import info.magnolia.ui.dialog.DialogDefinitionRegistry;
import info.magnolia.ui.dialog.actions.OpenDialogAction;
import info.magnolia.ui.editor.LocaleContext;

import javax.jcr.Node;

public class OpenCreateDialogAction extends OpenDialogAction<Node> {
	private final OpenCreateDialogActionDefinition definition;

	public OpenCreateDialogAction(
			final OpenCreateDialogActionDefinition definition,
			final LocaleContext localeContext,
			final ValueContext<Node> valueContext,
			final UIComponent parentView,
			final I18NAuthoringSupport<Node> i18NAuthoringSupport,
			final DialogDefinitionRegistry dialogDefinitionRegistry,
			final I18nizer i18nizer) {
		super(definition, localeContext, valueContext, parentView, i18NAuthoringSupport, dialogDefinitionRegistry, i18nizer);
		this.definition = definition;
	}

	@Override
	protected DialogDefinition getDialogDefinition(final DialogDefinitionRegistry dialogDefinitionRegistry, final I18nizer i18nizer) {
		final DialogDefinition dialogDefinition = dialogDefinitionRegistry.getProvider(getDefinition().getDialogId()).get();
		dialogDefinition.getActions().put("commit", definition.getCommitAction()); //we can't generate a new map due to byteBuddy (I18nizer), but since it is a mutable hash map this is fine
		return i18nizer.decorate(dialogDefinition);
	}
}