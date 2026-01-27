package com.namics.oss.magnolia.appbuilder.action.activation;

import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.availability.rule.JcrPublishedRuleDefinition;
import info.magnolia.ui.contentapp.action.JcrCommandActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;

public class DeactivateAppActionDefinition implements AppActionDefinition {

	@Override
	public JcrCommandActionDefinition action(final DropConstraintDefinition dropConstraint) {
		final JcrCommandActionDefinition definition = new JcrCommandActionDefinition();
		definition.setName("deactivate");
		definition.setLabel("actions.deactivate");
		definition.setIcon(MagnoliaIcons.UNPUBLISH.getCssClass());
		definition.setCommand("unpublish");
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.access("editor", "publisher")
				.writePermissionRequired(true)
				.rule(new JcrIsNotDeletedRuleDefinition())
				.rule(new JcrPublishedRuleDefinition())
				.build());
		return definition;
	}

	@Override
	public boolean multiple() {
		return true;
	}
}
