package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.framework.action.OpenCreateDialogActionDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.826489"
)
public class OpenCreateDialogActionBuilder extends OpenCreateDialogActionDefinition {
	public OpenCreateDialogActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public OpenCreateDialogActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public OpenCreateDialogActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public OpenCreateDialogActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public OpenCreateDialogActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public OpenCreateDialogActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public OpenCreateDialogActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public OpenCreateDialogActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public OpenCreateDialogActionBuilder implementationClass(
			Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public OpenCreateDialogActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public OpenCreateDialogActionBuilder nodeType(String nodeType) {
		this.setNodeType(nodeType);
		return this;
	}

	public OpenCreateDialogActionBuilder dialogName(String dialogName) {
		this.setDialogName(dialogName);
		return this;
	}
}
