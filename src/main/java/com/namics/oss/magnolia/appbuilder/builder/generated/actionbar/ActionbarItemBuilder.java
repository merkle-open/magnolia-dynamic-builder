package com.namics.oss.magnolia.appbuilder.builder.generated.actionbar;

import info.magnolia.ui.actionbar.definition.ConfiguredActionbarItemDefinition;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.270208"
)
public class ActionbarItemBuilder extends ConfiguredActionbarItemDefinition {
	public ActionbarItemBuilder name(String name) {
		this.setName(name);
		return this;
	}
}
