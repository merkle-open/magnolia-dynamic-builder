package com.namics.oss.magnolia.appbuilder.action;

import info.magnolia.i18nsystem.I18nizer;
import info.magnolia.ui.UIComponent;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.i18n.I18NAuthoringSupport;
import info.magnolia.ui.dialog.DialogDefinition;
import info.magnolia.ui.dialog.DialogDefinitionRegistry;
import info.magnolia.ui.dialog.FormDialogDefinition;
import info.magnolia.ui.dialog.actions.OpenDialogAction;
import info.magnolia.ui.dialog.actions.OpenDialogActionDefinition;
import info.magnolia.ui.editor.LocaleContext;
import info.magnolia.ui.editor.validator.NodeNameValidatorDefinition;
import info.magnolia.ui.field.ConfiguredFieldDefinition;

import javax.inject.Inject;
import javax.jcr.Node;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Adds a nodeNameValidator to the field jcrName if it is present in the dialog (must be unique).
 */
public class JcrNameValidatingOpenDialogAction extends OpenDialogAction<Node> {
	private final NodeNameValidatorDefinition nodeNameValidatorDefinition;

	@Inject
	public JcrNameValidatingOpenDialogAction(
			final Definition definition,
			final LocaleContext localeContext,
			final ValueContext<Node> valueContext,
			final UIComponent parentView,
			final I18NAuthoringSupport<Node> i18NAuthoringSupport,
			final DialogDefinitionRegistry dialogDefinitionRegistry,
			final I18nizer i18nizer
	) {
		super(definition, localeContext, valueContext, parentView, i18NAuthoringSupport, dialogDefinitionRegistry, i18nizer);
		this.nodeNameValidatorDefinition = new NodeNameValidatorDefinition();
		this.nodeNameValidatorDefinition.setMode(definition.getMode());
	}

	@Override
	protected DialogDefinition getDialogDefinition(final DialogDefinitionRegistry dialogDefinitionRegistry, final I18nizer i18nizer) {
		final DialogDefinition dialogDefinition = super.getDialogDefinition(dialogDefinitionRegistry, i18nizer);
		addNodeNameValidatorToJcrNameField(dialogDefinition);
		return dialogDefinition;
	}

	protected void addNodeNameValidatorToJcrNameField(final DialogDefinition dialogDefinition) {
		Optional
				.of(dialogDefinition)
				.filter(FormDialogDefinition.class::isInstance)
				.map(definition -> (FormDialogDefinition<Node>) definition)
				.map(FormDialogDefinition::getForm)
				.flatMap(formDialog -> formDialog.getFieldDefinition("jcrName"))
				.filter(ConfiguredFieldDefinition.class::isInstance)
				.map(definition -> (ConfiguredFieldDefinition<Node>) definition)
				.filter(field -> field.getValidators().stream().map(Object::getClass).noneMatch(NodeNameValidatorDefinition.class::equals))
				.ifPresent(field ->
						field.setValidators(Stream.concat(
								field.getValidators().stream(),
								Stream.of(nodeNameValidatorDefinition)
						).collect(Collectors.toList()))
				);
	}

	public static class Definition extends OpenDialogActionDefinition {
		private final NodeNameValidatorDefinition.Mode mode;

		public Definition(final NodeNameValidatorDefinition.Mode mode) {
			setImplementationClass(JcrNameValidatingOpenDialogAction.class);
			this.mode = mode;
		}

		public NodeNameValidatorDefinition.Mode getMode() {
			return mode;
		}
	}
}
