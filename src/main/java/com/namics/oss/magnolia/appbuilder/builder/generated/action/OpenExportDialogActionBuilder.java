package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.framework.action.OpenExportDialogActionDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.833897"
)
public class OpenExportDialogActionBuilder extends OpenExportDialogActionDefinition {
	public OpenExportDialogActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public OpenExportDialogActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public OpenExportDialogActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public OpenExportDialogActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public OpenExportDialogActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public OpenExportDialogActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public OpenExportDialogActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public OpenExportDialogActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public OpenExportDialogActionBuilder implementationClass(
			Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public OpenExportDialogActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public OpenExportDialogActionBuilder nodeType(String nodeType) {
		this.setNodeType(nodeType);
		return this;
	}

	public OpenExportDialogActionBuilder dialogName(String dialogName) {
		this.setDialogName(dialogName);
		return this;
	}
}
