package com.namics.oss.magnolia.appbuilder.builder.generated.action.deprecated;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.framework.action.DeleteConfirmationActionDefinition;
import java.lang.Class;
import java.lang.Deprecated;
import java.lang.String;
import javax.annotation.processing.Generated;

/**
 * @deprecated see {@link info.magnolia.ui.framework.action.DeleteConfirmationActionDefinition} for details
 */
@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.427893"
)
@Deprecated
public class DeleteConfirmationActionBuilder extends DeleteConfirmationActionDefinition {
	public DeleteConfirmationActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public DeleteConfirmationActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public DeleteConfirmationActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public DeleteConfirmationActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public DeleteConfirmationActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public DeleteConfirmationActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public DeleteConfirmationActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public DeleteConfirmationActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public DeleteConfirmationActionBuilder implementationClass(
			Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public DeleteConfirmationActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public DeleteConfirmationActionBuilder successActionName(String successActionName) {
		this.setSuccessActionName(successActionName);
		return this;
	}

	public DeleteConfirmationActionBuilder cancelActionName(String cancelActionName) {
		this.setCancelActionName(cancelActionName);
		return this;
	}

	public DeleteConfirmationActionBuilder confirmationMessage(String confirmationMessage) {
		this.setConfirmationMessage(confirmationMessage);
		return this;
	}

	public DeleteConfirmationActionBuilder confirmationHeader(String confirmationHeader) {
		this.setConfirmationHeader(confirmationHeader);
		return this;
	}

	public DeleteConfirmationActionBuilder proceedLabel(String proceedLabel) {
		this.setProceedLabel(proceedLabel);
		return this;
	}

	public DeleteConfirmationActionBuilder cancelLabel(String cancelLabel) {
		this.setCancelLabel(cancelLabel);
		return this;
	}

	public DeleteConfirmationActionBuilder defaultCancel(boolean defaultCancel) {
		this.setDefaultCancel(defaultCancel);
		return this;
	}
}
