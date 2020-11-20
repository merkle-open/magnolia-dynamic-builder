package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.contentapp.browser.action.RestoreVersionActionDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.850951"
)
public class RestoreVersionActionBuilder extends RestoreVersionActionDefinition {
	public RestoreVersionActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public RestoreVersionActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public RestoreVersionActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public RestoreVersionActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public RestoreVersionActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public RestoreVersionActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public RestoreVersionActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public RestoreVersionActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public RestoreVersionActionBuilder implementationClass(
			Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public RestoreVersionActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public RestoreVersionActionBuilder createVersionBeforeRestore(boolean createVersionBeforeRestore) {
		this.setCreateVersionBeforeRestore(createVersionBeforeRestore);
		return this;
	}
}
