package com.namics.oss.magnolia.appbuilder.action.edit;

import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.contentapp.action.MoveActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.namics.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;

public class MoveAppActionDefinition<T> implements AppActionDefinition {
	private final String icon;
	private final String label;

	public MoveAppActionDefinition() {
		this(MagnoliaIcons.MOVE.getCssClass(), "actions.move");
	}

	public MoveAppActionDefinition(final String icon, final String label) {
		this.icon = icon;
		this.label = label;
	}

	@Override
	public MoveActionDefinition<T> action(final DropConstraintDefinition dropConstraint) {
		final MoveActionDefinition<T> definition = new MoveActionDefinition<>();
		definition.setName("move");
		definition.setDialogId("magnolia-appbuilder:move");
		definition.setLabel(label);
		definition.setIcon(icon);
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.rule(new JcrIsNotDeletedRuleDefinition())
				.rule(new PermissionRequiredRuleDefinition(Permission.ALL))
				.build()
		);
		return definition;
	}
}
