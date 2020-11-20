package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.contentapp.browser.action.PasteContentActionDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.854766"
)
public class PasteContentActionBuilder extends PasteContentActionDefinition {
	public PasteContentActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public PasteContentActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public PasteContentActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public PasteContentActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public PasteContentActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public PasteContentActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public PasteContentActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public PasteContentActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public PasteContentActionBuilder implementationClass(Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public PasteContentActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}
}
