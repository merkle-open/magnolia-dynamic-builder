package com.merkle.oss.magnolia.appbuilder.action.version;

import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.availability.rule.JcrIsDeletedRuleDefinition;
import info.magnolia.ui.contentapp.action.RestoreJcrVersionActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;

public class RestorePreviousVersionAppActionDefinition implements AppActionDefinition {
	@Override
	public RestoreJcrVersionActionDefinition action(final DropConstraintDefinition dropConstraint) {
		final RestoreJcrVersionActionDefinition definition = new RestoreJcrVersionActionDefinition();
		definition.setName("restorePreviousVersion");
		definition.setLabel("actions.restorePreviousVersion");
		definition.setIcon(MagnoliaIcons.UNDO.getCssClass());
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.writePermissionRequired(true)
				.rule(new JcrIsDeletedRuleDefinition())
				.rule(new PermissionRequiredRuleDefinition(Permission.WRITE))
				.build()
		);
		return definition;
	}
}
