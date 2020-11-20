package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.contentapp.action.CommitActionDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.547234"
)
public class CommitActionBuilder extends CommitActionDefinition {
	public CommitActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public CommitActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public CommitActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public CommitActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public CommitActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public CommitActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public CommitActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public CommitActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public CommitActionBuilder implementationClass(Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public CommitActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}
}
