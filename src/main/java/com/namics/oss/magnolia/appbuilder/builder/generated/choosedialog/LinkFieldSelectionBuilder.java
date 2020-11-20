package com.namics.oss.magnolia.appbuilder.builder.generated.choosedialog;

import info.magnolia.ui.contentapp.field.LinkFieldSelectionDefinition;
import info.magnolia.ui.form.field.transformer.Transformer;
import info.magnolia.ui.form.validator.definition.FieldValidatorDefinition;
import java.lang.Class;
import java.lang.String;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:02.004310"
)
public class LinkFieldSelectionBuilder extends LinkFieldSelectionDefinition {
	public LinkFieldSelectionBuilder readOnly(boolean readOnly) {
		this.setReadOnly(readOnly);
		return this;
	}

	public LinkFieldSelectionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public LinkFieldSelectionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public LinkFieldSelectionBuilder styleName(String styleName) {
		this.setStyleName(styleName);
		return this;
	}

	public LinkFieldSelectionBuilder i18n(boolean i18n) {
		this.setI18n(i18n);
		return this;
	}

	public LinkFieldSelectionBuilder requiredErrorMessage(String requiredErrorMessage) {
		this.setRequiredErrorMessage(requiredErrorMessage);
		return this;
	}

	public LinkFieldSelectionBuilder type(String type) {
		this.setType(type);
		return this;
	}

	public LinkFieldSelectionBuilder required(boolean required) {
		this.setRequired(required);
		return this;
	}

	public LinkFieldSelectionBuilder validators(List<FieldValidatorDefinition> validators) {
		this.setValidators(validators);
		return this;
	}

	public LinkFieldSelectionBuilder validator(FieldValidatorDefinition validator) {
		this.addValidator(validator);
		return this;
	}

	public LinkFieldSelectionBuilder defaultValue(String defaultValue) {
		this.setDefaultValue(defaultValue);
		return this;
	}

	public LinkFieldSelectionBuilder transformerClass(
			Class<? extends Transformer<?>> transformerClass) {
		this.setTransformerClass(transformerClass);
		return this;
	}

	public LinkFieldSelectionBuilder converterClass(Class<?> converterClass) {
		this.setConverterClass(converterClass);
		return this;
	}

	public LinkFieldSelectionBuilder fieldType(String fieldType) {
		this.setFieldType(fieldType);
		return this;
	}

	public LinkFieldSelectionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public LinkFieldSelectionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public LinkFieldSelectionBuilder displayTextField(boolean displayTextField) {
		this.setDisplayTextField(displayTextField);
		return this;
	}

	public LinkFieldSelectionBuilder displayTextFieldOnTop(boolean displayTextFieldOnTop) {
		this.setDisplayTextFieldOnTop(displayTextFieldOnTop);
		return this;
	}

	public LinkFieldSelectionBuilder validators(FieldValidatorDefinition... validators) {
		this.setValidators(Arrays.asList(validators));
		return this;
	}
}
