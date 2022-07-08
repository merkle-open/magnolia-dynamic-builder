package com.namics.oss.magnolia.appbuilder.action.edit;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.JcrIsNotDeletedRuleDefinition;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.contentapp.action.DuplicateNodeActionDefinition;

public class DuplicateAppActionDefinition implements AppActionDefinition {
	private final String icon;
	private final String label;

	public DuplicateAppActionDefinition() {
		this(MgnlIcon.DUPLICATE, "actions.duplicate");
	}

	public DuplicateAppActionDefinition(final String icon, final String label) {
		this.icon = icon;
		this.label = label;
	}

	@Override
	public ConfiguredActionDefinition action() {
		final DuplicateNodeActionDefinition definition = new DuplicateNodeActionDefinition();
		definition.setName("duplicate");
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
