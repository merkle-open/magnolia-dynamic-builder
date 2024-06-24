package com.namics.oss.magnolia.appbuilder.action.edit;

import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.contentapp.action.DuplicateNodeActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.namics.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;

public class DuplicateAppActionDefinition implements AppActionDefinition {
	private final String icon;
	private final String label;

	public DuplicateAppActionDefinition() {
		this(MagnoliaIcons.DUPLICATE.getCssClass(), "actions.duplicate");
	}

	public DuplicateAppActionDefinition(final String icon, final String label) {
		this.icon = icon;
		this.label = label;
	}

	@Override
	public DuplicateNodeActionDefinition action(final DropConstraintDefinition dropConstraint) {
		final DuplicateNodeActionDefinition definition = new DuplicateNodeActionDefinition();
		definition.setName("duplicate");
		definition.setLabel(label);
		definition.setIcon(icon);
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.rule(new JcrIsNotDeletedRuleDefinition())
				.rule(new PermissionRequiredRuleDefinition(Permission.ADD|Permission.READ))
				.build()
		);
		return definition;
	}
}
