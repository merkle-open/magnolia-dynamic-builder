package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.contentapp.browser.action.ShowVersionsActionDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.847496"
)
public class ShowVersionsActionBuilder extends ShowVersionsActionDefinition {
	public ShowVersionsActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public ShowVersionsActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public ShowVersionsActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public ShowVersionsActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public ShowVersionsActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public ShowVersionsActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public ShowVersionsActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public ShowVersionsActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public ShowVersionsActionBuilder implementationClass(Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public ShowVersionsActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}
}
