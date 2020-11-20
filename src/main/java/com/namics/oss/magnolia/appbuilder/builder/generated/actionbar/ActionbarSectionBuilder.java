package com.namics.oss.magnolia.appbuilder.builder.generated.actionbar;

import info.magnolia.ui.actionbar.definition.ActionbarGroupDefinition;
import info.magnolia.ui.actionbar.definition.ConfiguredActionbarSectionDefinition;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import java.lang.String;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.273339"
)
public class ActionbarSectionBuilder extends ConfiguredActionbarSectionDefinition {
	public ActionbarSectionBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public ActionbarSectionBuilder i18nBasename(String i18nBasename) {
		this.setI18nBasename(i18nBasename);
		return this;
	}

	public ActionbarSectionBuilder groups(List<ActionbarGroupDefinition> groups) {
		this.setGroups(groups);
		return this;
	}

	public ActionbarSectionBuilder group(ActionbarGroupDefinition group) {
		this.addGroup(group);
		return this;
	}

	public ActionbarSectionBuilder availability(AvailabilityDefinition availability) {
		this.setAvailability(availability);
		return this;
	}

	public ActionbarSectionBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public ActionbarSectionBuilder groups(ActionbarGroupDefinition... groups) {
		this.setGroups(Arrays.asList(groups));
		return this;
	}
}
