package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.559855"
)
public class ActionBuilder extends ConfiguredActionDefinition {
	public ActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public ActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public ActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public ActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public ActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public ActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public ActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public ActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public ActionBuilder implementationClass(Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public ActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}
}
