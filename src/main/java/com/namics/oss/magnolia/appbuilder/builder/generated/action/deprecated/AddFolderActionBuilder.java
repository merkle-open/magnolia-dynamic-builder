package com.namics.oss.magnolia.appbuilder.builder.generated.action.deprecated;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.framework.action.AddFolderActionDefinition;
import java.lang.Class;
import java.lang.Deprecated;
import java.lang.String;
import javax.annotation.processing.Generated;

/**
 * @deprecated see {@link info.magnolia.ui.framework.action.AddFolderActionDefinition} for details
 */
@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.293669"
)
@Deprecated
public class AddFolderActionBuilder extends AddFolderActionDefinition {
	public AddFolderActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public AddFolderActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public AddFolderActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public AddFolderActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public AddFolderActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public AddFolderActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public AddFolderActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public AddFolderActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public AddFolderActionBuilder implementationClass(Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public AddFolderActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public AddFolderActionBuilder nodeType(String nodeType) {
		this.setNodeType(nodeType);
		return this;
	}

	public AddFolderActionBuilder baseName(String baseName) {
		this.setBaseName(baseName);
		return this;
	}
}
