package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.contentapp.action.DeleteNodesConfirmationActionDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.459719"
)
public class DeleteNodesConfirmationActionBuilder extends DeleteNodesConfirmationActionDefinition {
	public DeleteNodesConfirmationActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public DeleteNodesConfirmationActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public DeleteNodesConfirmationActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public DeleteNodesConfirmationActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public DeleteNodesConfirmationActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public DeleteNodesConfirmationActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public DeleteNodesConfirmationActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public DeleteNodesConfirmationActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public DeleteNodesConfirmationActionBuilder implementationClass(
			Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public DeleteNodesConfirmationActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public DeleteNodesConfirmationActionBuilder successActionName(String successActionName) {
		this.setSuccessActionName(successActionName);
		return this;
	}

	public DeleteNodesConfirmationActionBuilder cancelActionName(String cancelActionName) {
		this.setCancelActionName(cancelActionName);
		return this;
	}

	public DeleteNodesConfirmationActionBuilder confirmationMessage(String confirmationMessage) {
		this.setConfirmationMessage(confirmationMessage);
		return this;
	}

	public DeleteNodesConfirmationActionBuilder confirmationHeader(String confirmationHeader) {
		this.setConfirmationHeader(confirmationHeader);
		return this;
	}

	public DeleteNodesConfirmationActionBuilder proceedLabel(String proceedLabel) {
		this.setProceedLabel(proceedLabel);
		return this;
	}

	public DeleteNodesConfirmationActionBuilder cancelLabel(String cancelLabel) {
		this.setCancelLabel(cancelLabel);
		return this;
	}

	public DeleteNodesConfirmationActionBuilder defaultCancel(boolean defaultCancel) {
		this.setDefaultCancel(defaultCancel);
		return this;
	}
}
