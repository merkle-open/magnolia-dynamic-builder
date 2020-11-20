package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.framework.action.OpenEditDialogActionDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.830495"
)
public class OpenEditDialogActionBuilder extends OpenEditDialogActionDefinition {
	public OpenEditDialogActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public OpenEditDialogActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public OpenEditDialogActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public OpenEditDialogActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public OpenEditDialogActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public OpenEditDialogActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public OpenEditDialogActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public OpenEditDialogActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public OpenEditDialogActionBuilder implementationClass(
			Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public OpenEditDialogActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public OpenEditDialogActionBuilder dialogName(String dialogName) {
		this.setDialogName(dialogName);
		return this;
	}
}
