package com.namics.oss.magnolia.appbuilder.builder.generated.actionbar;

import info.magnolia.ui.actionbar.definition.ActionbarSectionDefinition;
import info.magnolia.ui.actionbar.definition.ConfiguredActionbarDefinition;
import java.lang.String;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.261501"
)
public class ActionbarBuilder extends ConfiguredActionbarDefinition {
	public ActionbarBuilder section(ActionbarSectionDefinition section) {
		this.addSection(section);
		return this;
	}

	public ActionbarBuilder defaultAction(String defaultAction) {
		this.setDefaultAction(defaultAction);
		return this;
	}

	public ActionbarBuilder deleteAction(String deleteAction) {
		this.setDeleteAction(deleteAction);
		return this;
	}

	public ActionbarBuilder sections(List<ActionbarSectionDefinition> sections) {
		this.setSections(sections);
		return this;
	}

	public ActionbarBuilder sections(ActionbarSectionDefinition... sections) {
		this.setSections(Arrays.asList(sections));
		return this;
	}
}
