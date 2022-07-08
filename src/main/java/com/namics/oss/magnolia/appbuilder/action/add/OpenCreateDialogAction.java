package com.namics.oss.magnolia.appbuilder.action.add;

import info.magnolia.i18nsystem.I18nizer;
import info.magnolia.ui.UIComponent;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.i18n.I18NAuthoringSupport;
import info.magnolia.ui.dialog.ConfiguredDialogDefinition;
import info.magnolia.ui.dialog.DialogDefinition;
import info.magnolia.ui.dialog.DialogDefinitionRegistry;
import info.magnolia.ui.dialog.actions.OpenDialogAction;
import info.magnolia.ui.editor.LocaleContext;

import javax.jcr.Node;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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
	protected DialogDefinition getDialogDefinition(DialogDefinitionRegistry dialogDefinitionRegistry, I18nizer i18nizer) {
		final ConfiguredDialogDefinition dialogDefinition = (ConfiguredDialogDefinition) super.getDialogDefinition(dialogDefinitionRegistry, i18nizer);
		dialogDefinition.setActions(Stream.concat(
				dialogDefinition.getActions().entrySet().stream(),
				Stream.of(Map.entry("commit", definition.getCommitAction()))
		).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (d1, d2) -> d2)));
		return dialogDefinition;
	}
}