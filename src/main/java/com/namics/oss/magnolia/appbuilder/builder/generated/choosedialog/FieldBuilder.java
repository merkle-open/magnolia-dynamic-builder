package com.namics.oss.magnolia.appbuilder.builder.generated.choosedialog;

import info.magnolia.ui.form.field.definition.ConfiguredFieldDefinition;
import info.magnolia.ui.form.field.transformer.Transformer;
import info.magnolia.ui.form.validator.definition.FieldValidatorDefinition;
import java.lang.Class;
import java.lang.String;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.994590"
)
public class FieldBuilder extends ConfiguredFieldDefinition {
	public FieldBuilder readOnly(boolean readOnly) {
		this.setReadOnly(readOnly);
		return this;
	}

	public FieldBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public FieldBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public FieldBuilder styleName(String styleName) {
		this.setStyleName(styleName);
		return this;
	}

	public FieldBuilder i18n(boolean i18n) {
		this.setI18n(i18n);
		return this;
	}

	public FieldBuilder requiredErrorMessage(String requiredErrorMessage) {
		this.setRequiredErrorMessage(requiredErrorMessage);
		return this;
	}

	public FieldBuilder type(String type) {
		this.setType(type);
		return this;
	}

	public FieldBuilder required(boolean required) {
		this.setRequired(required);
		return this;
	}

	public FieldBuilder validators(List<FieldValidatorDefinition> validators) {
		this.setValidators(validators);
		return this;
	}

	public FieldBuilder validator(FieldValidatorDefinition validator) {
		this.addValidator(validator);
		return this;
	}

	public FieldBuilder defaultValue(String defaultValue) {
		this.setDefaultValue(defaultValue);
		return this;
	}

	public FieldBuilder transformerClass(Class<? extends Transformer<?>> transformerClass) {
		this.setTransformerClass(transformerClass);
		return this;
	}

	public FieldBuilder converterClass(Class<?> converterClass) {
		this.setConverterClass(converterClass);
		return this;
	}

	public FieldBuilder fieldType(String fieldType) {
		this.setFieldType(fieldType);
		return this;
	}

	public FieldBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public FieldBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public FieldBuilder validators(FieldValidatorDefinition... validators) {
		this.setValidators(Arrays.asList(validators));
		return this;
	}
}
