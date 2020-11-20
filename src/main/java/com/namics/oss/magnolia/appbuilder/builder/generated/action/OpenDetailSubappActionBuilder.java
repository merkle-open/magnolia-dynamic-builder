package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.contentapp.action.OpenDetailSubappActionDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.550338"
)
public class OpenDetailSubappActionBuilder extends OpenDetailSubappActionDefinition {
	public OpenDetailSubappActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public OpenDetailSubappActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public OpenDetailSubappActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public OpenDetailSubappActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public OpenDetailSubappActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public OpenDetailSubappActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public OpenDetailSubappActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public OpenDetailSubappActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public OpenDetailSubappActionBuilder implementationClass(
			Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public OpenDetailSubappActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public OpenDetailSubappActionBuilder appName(String appName) {
		this.setAppName(appName);
		return this;
	}

	public OpenDetailSubappActionBuilder subAppName(String subAppName) {
		this.setSubAppName(subAppName);
		return this;
	}

	public OpenDetailSubappActionBuilder viewType(String viewType) {
		this.setViewType(viewType);
		return this;
	}
}
