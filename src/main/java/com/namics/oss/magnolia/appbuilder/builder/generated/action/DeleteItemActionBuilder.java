package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.framework.action.DeleteItemActionDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.723181"
)
public class DeleteItemActionBuilder extends DeleteItemActionDefinition {
	public DeleteItemActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public DeleteItemActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public DeleteItemActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public DeleteItemActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public DeleteItemActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public DeleteItemActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public DeleteItemActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public DeleteItemActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public DeleteItemActionBuilder implementationClass(Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public DeleteItemActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}
}
