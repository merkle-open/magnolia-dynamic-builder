package com.merkle.oss.magnolia.appbuilder.action.activation;

import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.availability.rule.JcrIsDeletedRuleDefinition;
import info.magnolia.ui.availability.rule.JcrPublishableRuleDefinition;
import info.magnolia.ui.contentapp.action.JcrCommandActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;

public class ActivateDeletionAppActionDefinition implements AppActionDefinition {

	@Override
	public JcrCommandActionDefinition action(final DropConstraintDefinition dropConstraint) {
		final JcrCommandActionDefinition definition = new JcrCommandActionDefinition();
		definition.setName("activateDeletion");
		definition.setLabel("actions.activateDeleted");
		definition.setIcon(MagnoliaIcons.PUBLISH.getCssClass());
		definition.setCommand("publish");
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
