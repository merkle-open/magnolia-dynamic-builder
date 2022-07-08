package com.namics.oss.magnolia.appbuilder.action.edit;

import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.JcrIsNotDeletedRuleDefinition;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.contentapp.action.JcrCommandActionDefinition;

public class DeleteAppActionDefinition implements AppActionDefinition {

	@Override
	public ConfiguredActionDefinition action() {
		final JcrCommandActionDefinition definition = new JcrCommandActionDefinition();
		definition.setName("delete");
		definition.setLabel("actions.delete");
		definition.setAsynchronous(true);
		definition.setCommand("delete");
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.writePermissionRequired(true)
				.rule(new JcrIsNotDeletedRuleDefinition())
				.build()
		);
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
