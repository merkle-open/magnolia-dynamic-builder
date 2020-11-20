package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.contentapp.browser.action.DelegateByNodeTypeActionDefinition;
import java.lang.Class;
import java.lang.String;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.862401"
)
public class DelegateByNodeTypeActionBuilder extends DelegateByNodeTypeActionDefinition {
	public DelegateByNodeTypeActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public DelegateByNodeTypeActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public DelegateByNodeTypeActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public DelegateByNodeTypeActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public DelegateByNodeTypeActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public DelegateByNodeTypeActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public DelegateByNodeTypeActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public DelegateByNodeTypeActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public DelegateByNodeTypeActionBuilder implementationClass(
			Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public DelegateByNodeTypeActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public DelegateByNodeTypeActionBuilder nodeTypeToActionMappings(
			List<DelegateByNodeTypeActionDefinition.NodeTypeToActionMapping> nodeTypeToActionMappings) {
		this.setNodeTypeToActionMappings(nodeTypeToActionMappings);
		return this;
	}

	public DelegateByNodeTypeActionBuilder nodeTypeToActionMappings(
			DelegateByNodeTypeActionDefinition.NodeTypeToActionMapping... nodeTypeToActionMappings) {
		this.setNodeTypeToActionMappings(Arrays.asList(nodeTypeToActionMappings));
		return this;
	}
}
