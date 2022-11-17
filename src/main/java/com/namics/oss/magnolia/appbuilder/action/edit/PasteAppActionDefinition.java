package com.namics.oss.magnolia.appbuilder.action.edit;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.JcrIsNotDeletedRuleDefinition;
import com.namics.oss.magnolia.appbuilder.action.edit.clipboard.CanPasteContentRuleDefinition;
import com.namics.oss.magnolia.appbuilder.action.edit.clipboard.PasteContentActionDefinition;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;

public class PasteAppActionDefinition implements AppActionDefinition {
	private final String icon;
	private final String label;

	public PasteAppActionDefinition() {
		this(MgnlIcon.PASTE, "actions.paste");
	}

	public PasteAppActionDefinition(final String icon, final String label) {
		this.icon = icon;
		this.label = label;
	}

	@Override
	public ConfiguredActionDefinition action() {
		final PasteContentActionDefinition definition = new PasteContentActionDefinition();
		definition.setName("paste");
		definition.setLabel(label);
		definition.setIcon(icon);
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.writePermissionRequired(true)
				.rule(new JcrIsNotDeletedRuleDefinition())
				.rule(new CanPasteContentRuleDefinition())
				.build()
		);
		return definition;
	}
}
