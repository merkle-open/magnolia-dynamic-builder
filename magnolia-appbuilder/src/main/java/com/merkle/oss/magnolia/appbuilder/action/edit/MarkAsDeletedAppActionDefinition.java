package com.merkle.oss.magnolia.appbuilder.action.edit;

import info.magnolia.cms.security.Permission;
import info.magnolia.ui.contentapp.action.MarkAsDeletedCommandActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;

public class MarkAsDeletedAppActionDefinition implements AppActionDefinition {

	@Override
	public MarkAsDeletedCommandActionDefinition action(final DropConstraintDefinition dropConstraint) {
		final MarkAsDeletedCommandActionDefinition definition = new MarkAsDeletedCommandActionDefinition();
		definition.setName("delete");
		definition.setLabel("actions.delete");
		definition.setCommand("markAsDeleted");
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.rule(new JcrIsNotDeletedRuleDefinition())
				.rule(new PermissionRequiredRuleDefinition(Permission.REMOVE))
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
