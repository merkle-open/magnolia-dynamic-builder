package com.namics.oss.magnolia.appbuilder.action.activation;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.AvailabilityDefinitionBuilder;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.availability.rule.JcrIsDeletedRuleDefinition;
import info.magnolia.ui.availability.rule.JcrPublishableRuleDefinition;
import info.magnolia.ui.contentapp.action.JcrCommandActionDefinition;

public class ActivateDeletionAppActionDefinition implements AppActionDefinition {

	@Override
	public ConfiguredActionDefinition action() {
		final JcrCommandActionDefinition definition = new JcrCommandActionDefinition();
		definition.setName("activateDeletion");
		definition.setLabel("actions.activateDeleted");
		definition.setIcon(MgnlIcon.PUBLISH);
		definition.setCommand("activate");
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.access("editor", "publisher")
				.writePermissionRequired(true)
				.rule(new JcrIsDeletedRuleDefinition())
				.rule(new JcrPublishableRuleDefinition())
				.build());
		return definition;
	}

	@Override
	public boolean multiple() {
		return true;
	}
}
