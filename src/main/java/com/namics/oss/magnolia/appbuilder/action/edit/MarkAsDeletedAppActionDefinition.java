package com.namics.oss.magnolia.appbuilder.action.edit;

import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.contentapp.action.MarkAsDeletedCommandActionDefinition;

public class MarkAsDeletedAppActionDefinition implements AppActionDefinition {

	@Override
	public ConfiguredActionDefinition action() {
		final MarkAsDeletedCommandActionDefinition definition = new MarkAsDeletedCommandActionDefinition();
		definition.setName("delete");
		definition.setLabel("actions.delete");
		definition.setCommand("markAsDeleted");
		return definition;
	}

	@Override
	public boolean multiple() {
		return true;
	}

	@Override
	public boolean isCallback() {
		return true;
	}
}
