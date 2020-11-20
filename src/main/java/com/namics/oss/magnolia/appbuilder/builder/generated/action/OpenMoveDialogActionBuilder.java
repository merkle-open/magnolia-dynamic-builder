package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.contentapp.movedialog.action.OpenMoveDialogActionDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.840732"
)
public class OpenMoveDialogActionBuilder extends OpenMoveDialogActionDefinition {
	public OpenMoveDialogActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public OpenMoveDialogActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public OpenMoveDialogActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public OpenMoveDialogActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public OpenMoveDialogActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public OpenMoveDialogActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public OpenMoveDialogActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public OpenMoveDialogActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public OpenMoveDialogActionBuilder implementationClass(
			Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public OpenMoveDialogActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}
}
