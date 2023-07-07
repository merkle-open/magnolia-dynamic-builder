package com.namics.oss.magnolia.appbuilder.action.edit;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.contentapp.action.MoveActionDefinition;

public class MoveAppActionDefinition<T> implements AppActionDefinition {
	private final String icon;
	private final String label;

	public MoveAppActionDefinition() {
		this(MgnlIcon.MOVE, "actions.move");
	}

	public MoveAppActionDefinition(final String icon, final String label) {
		this.icon = icon;
		this.label = label;
	}

	@Override
	public ConfiguredActionDefinition action() {
		final MoveActionDefinition<T> definition = new MoveActionDefinition<>();
		definition.setName("move");
		definition.setDialogId("magnolia-appbuilder:move");
		definition.setLabel(label);
		definition.setIcon(icon);
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.writePermissionRequired(true)
				.rule(new JcrIsNotDeletedRuleDefinition())
				.build()
		);
		return definition;
	}
}
