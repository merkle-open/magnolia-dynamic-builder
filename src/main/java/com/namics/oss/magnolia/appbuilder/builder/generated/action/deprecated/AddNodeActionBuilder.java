package com.namics.oss.magnolia.appbuilder.builder.generated.action.deprecated;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.framework.action.AddNodeActionDefinition;
import java.lang.Class;
import java.lang.Deprecated;
import java.lang.String;
import javax.annotation.processing.Generated;

/**
 * @deprecated see {@link info.magnolia.ui.framework.action.AddNodeActionDefinition} for details
 */
@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.288615"
)
@Deprecated
public class AddNodeActionBuilder extends AddNodeActionDefinition {
	public AddNodeActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public AddNodeActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public AddNodeActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public AddNodeActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public AddNodeActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public AddNodeActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public AddNodeActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public AddNodeActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public AddNodeActionBuilder implementationClass(Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public AddNodeActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public AddNodeActionBuilder nodeType(String nodeType) {
		this.setNodeType(nodeType);
		return this;
	}

	public AddNodeActionBuilder baseName(String baseName) {
		this.setBaseName(baseName);
		return this;
	}
}
