package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.framework.action.OpenLocationActionDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.837547"
)
public class OpenLocationActionBuilder extends OpenLocationActionDefinition {
	public OpenLocationActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public OpenLocationActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public OpenLocationActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public OpenLocationActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public OpenLocationActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public OpenLocationActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public OpenLocationActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public OpenLocationActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public OpenLocationActionBuilder implementationClass(Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public OpenLocationActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public OpenLocationActionBuilder appType(String appType) {
		this.setAppType(appType);
		return this;
	}

	public OpenLocationActionBuilder subAppId(String subAppId) {
		this.setSubAppId(subAppId);
		return this;
	}

	public OpenLocationActionBuilder parameter(String parameter) {
		this.setParameter(parameter);
		return this;
	}

	public OpenLocationActionBuilder appName(String appName) {
		this.setAppName(appName);
		return this;
	}
}
