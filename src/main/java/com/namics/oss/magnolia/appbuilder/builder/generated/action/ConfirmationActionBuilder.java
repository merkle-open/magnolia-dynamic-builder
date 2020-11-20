package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.contentapp.action.ConfirmationActionDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.469577"
)
public class ConfirmationActionBuilder extends ConfirmationActionDefinition {
	public ConfirmationActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public ConfirmationActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public ConfirmationActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public ConfirmationActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public ConfirmationActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public ConfirmationActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public ConfirmationActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public ConfirmationActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public ConfirmationActionBuilder implementationClass(Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public ConfirmationActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public ConfirmationActionBuilder successActionName(String successActionName) {
		this.setSuccessActionName(successActionName);
		return this;
	}

	public ConfirmationActionBuilder cancelActionName(String cancelActionName) {
		this.setCancelActionName(cancelActionName);
		return this;
	}

	public ConfirmationActionBuilder confirmationMessage(String confirmationMessage) {
		this.setConfirmationMessage(confirmationMessage);
		return this;
	}

	public ConfirmationActionBuilder confirmationHeader(String confirmationHeader) {
		this.setConfirmationHeader(confirmationHeader);
		return this;
	}

	public ConfirmationActionBuilder proceedLabel(String proceedLabel) {
		this.setProceedLabel(proceedLabel);
		return this;
	}

	public ConfirmationActionBuilder cancelLabel(String cancelLabel) {
		this.setCancelLabel(cancelLabel);
		return this;
	}

	public ConfirmationActionBuilder defaultCancel(boolean defaultCancel) {
		this.setDefaultCancel(defaultCancel);
		return this;
	}
}
