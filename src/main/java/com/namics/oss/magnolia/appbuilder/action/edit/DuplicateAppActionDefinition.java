package com.namics.oss.magnolia.appbuilder.action.edit;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.action.deprecated.DuplicateNodeActionBuilder;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;

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
		return new DuplicateNodeActionBuilder()
				.name("duplicate")
				.label(label)
				.icon(icon);
	}
}
