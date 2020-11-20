package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.contentapp.browser.action.CopyContentActionDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.866438"
)
public class CopyContentActionBuilder extends CopyContentActionDefinition {
	public CopyContentActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public CopyContentActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public CopyContentActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public CopyContentActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public CopyContentActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public CopyContentActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public CopyContentActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public CopyContentActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public CopyContentActionBuilder implementationClass(Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public CopyContentActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}
}
