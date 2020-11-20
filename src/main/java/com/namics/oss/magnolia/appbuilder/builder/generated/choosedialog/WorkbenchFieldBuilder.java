package com.namics.oss.magnolia.appbuilder.builder.generated.choosedialog;

import info.magnolia.ui.contentapp.field.WorkbenchFieldDefinition;
import info.magnolia.ui.form.field.transformer.Transformer;
import info.magnolia.ui.form.validator.definition.FieldValidatorDefinition;
import info.magnolia.ui.imageprovider.definition.ImageProviderDefinition;
import info.magnolia.ui.workbench.definition.WorkbenchDefinition;
import java.lang.Class;
import java.lang.String;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.999307"
)
public class WorkbenchFieldBuilder extends WorkbenchFieldDefinition {
	public WorkbenchFieldBuilder readOnly(boolean readOnly) {
		this.setReadOnly(readOnly);
		return this;
	}

	public WorkbenchFieldBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public WorkbenchFieldBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public WorkbenchFieldBuilder styleName(String styleName) {
		this.setStyleName(styleName);
		return this;
	}

	public WorkbenchFieldBuilder i18n(boolean i18n) {
		this.setI18n(i18n);
		return this;
	}

	public WorkbenchFieldBuilder requiredErrorMessage(String requiredErrorMessage) {
		this.setRequiredErrorMessage(requiredErrorMessage);
		return this;
	}

	public WorkbenchFieldBuilder type(String type) {
		this.setType(type);
		return this;
	}

	public WorkbenchFieldBuilder required(boolean required) {
		this.setRequired(required);
		return this;
	}

	public WorkbenchFieldBuilder validators(List<FieldValidatorDefinition> validators) {
		this.setValidators(validators);
		return this;
	}

	public WorkbenchFieldBuilder validator(FieldValidatorDefinition validator) {
		this.addValidator(validator);
		return this;
	}

	public WorkbenchFieldBuilder defaultValue(String defaultValue) {
		this.setDefaultValue(defaultValue);
		return this;
	}

	public WorkbenchFieldBuilder transformerClass(Class<? extends Transformer<?>> transformerClass) {
		this.setTransformerClass(transformerClass);
		return this;
	}

	public WorkbenchFieldBuilder converterClass(Class<?> converterClass) {
		this.setConverterClass(converterClass);
		return this;
	}

	public WorkbenchFieldBuilder fieldType(String fieldType) {
		this.setFieldType(fieldType);
		return this;
	}

	public WorkbenchFieldBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public WorkbenchFieldBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public WorkbenchFieldBuilder imageProvider(ImageProviderDefinition imageProvider) {
		this.setImageProvider(imageProvider);
		return this;
	}

	public WorkbenchFieldBuilder workbench(WorkbenchDefinition workbench) {
		this.setWorkbench(workbench);
		return this;
	}

	public WorkbenchFieldBuilder validators(FieldValidatorDefinition... validators) {
		this.setValidators(Arrays.asList(validators));
		return this;
	}
}
