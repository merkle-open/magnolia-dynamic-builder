package com.namics.oss.magnolia.appbuilder.action.activation;

import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.availability.rule.JcrPublishableRuleDefinition;
import info.magnolia.ui.contentapp.action.JcrCommandActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import java.util.Map;

import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;

public class ActivateRecursiveAppActionDefinition implements AppActionDefinition {

	@Override
	public JcrCommandActionDefinition action(final DropConstraintDefinition dropConstraint) {
		final JcrCommandActionDefinition definition = new JcrCommandActionDefinition();
		definition.setName("activateRecursive");
		definition.setLabel("actions.activateRecursive");
		definition.setIcon(MagnoliaIcons.PUBLISH_INCL_SUB.getCssClass());
		definition.setCommand("publish");
		definition.setParams(Map.of("recursive", true));
		definition.setAsynchronous(true);
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.access("editor", "publisher")
				.writePermissionRequired(true)
				.rule(new JcrIsNotDeletedRuleDefinition())
				.rule(new JcrPublishableRuleDefinition())
				.build());
		return definition;
	}

	@Override
	public boolean multiple() {
		return true;
	}
}
