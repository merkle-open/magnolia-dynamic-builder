package com.namics.oss.magnolia.appbuilder.builder.generated.action;

import info.magnolia.ui.api.action.Action;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.contentapp.action.SetDataFilterActionDefinition;
import info.magnolia.ui.filter.DataFilter;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.556531"
)
public class SetDataFilterActionBuilder extends SetDataFilterActionDefinition {
	public SetDataFilterActionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public SetDataFilterActionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public SetDataFilterActionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public SetDataFilterActionBuilder successMessage(String successMessage) {
		this.setSuccessMessage(successMessage);
		return this;
	}

	public SetDataFilterActionBuilder failureMessage(String failureMessage) {
		this.setFailureMessage(failureMessage);
		return this;
	}

	public SetDataFilterActionBuilder errorMessage(String errorMessage) {
		this.setErrorMessage(errorMessage);
		return this;
	}

	public SetDataFilterActionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public SetDataFilterActionBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public SetDataFilterActionBuilder implementationClass(
			Class<? extends Action> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public SetDataFilterActionBuilder description(String description) {
		this.setDescription(description);
		return this;
	}

	public SetDataFilterActionBuilder dataFilter(DataFilter dataFilter) {
		this.setDataFilter(dataFilter);
		return this;
	}
}
