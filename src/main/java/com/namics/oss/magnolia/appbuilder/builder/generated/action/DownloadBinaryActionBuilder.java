package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.framework.action.DownloadBinaryActionDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.727003"
)
public class DownloadBinaryActionBuilder extends DownloadBinaryActionDefinition {
	public DownloadBinaryActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public DownloadBinaryActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public DownloadBinaryActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public DownloadBinaryActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public DownloadBinaryActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public DownloadBinaryActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public DownloadBinaryActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public DownloadBinaryActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public DownloadBinaryActionBuilder implementationClass(
			Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public DownloadBinaryActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public DownloadBinaryActionBuilder binaryNodeName(String binaryNodeName) {
		this.setBinaryNodeName(binaryNodeName);
		return this;
	}

	public DownloadBinaryActionBuilder dataProperty(String dataProperty) {
		this.setDataProperty(dataProperty);
		return this;
	}

	public DownloadBinaryActionBuilder fileNameProperty(String fileNameProperty) {
		this.setFileNameProperty(fileNameProperty);
		return this;
	}

	public DownloadBinaryActionBuilder extensionProperty(String extensionProperty) {
		this.setExtensionProperty(extensionProperty);
		return this;
	}
}
