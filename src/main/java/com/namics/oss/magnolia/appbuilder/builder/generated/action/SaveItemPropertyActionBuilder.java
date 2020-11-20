package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.contentapp.browser.action.SaveItemPropertyActionDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.844013"
)
public class SaveItemPropertyActionBuilder extends SaveItemPropertyActionDefinition {
	public SaveItemPropertyActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public SaveItemPropertyActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public SaveItemPropertyActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public SaveItemPropertyActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public SaveItemPropertyActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public SaveItemPropertyActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public SaveItemPropertyActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public SaveItemPropertyActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public SaveItemPropertyActionBuilder implementationClass(
			Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public SaveItemPropertyActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}
}
