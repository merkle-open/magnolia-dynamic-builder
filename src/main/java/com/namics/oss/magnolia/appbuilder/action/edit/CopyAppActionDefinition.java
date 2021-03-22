package com.namics.oss.magnolia.appbuilder.action.edit;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.action.CopyContentActionBuilder;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;

public class CopyAppActionDefinition implements AppActionDefinition {
	private final String icon;
	private final String label;

	public CopyAppActionDefinition() {
		this(MgnlIcon.COPY, "actions.copy");
	}

	public CopyAppActionDefinition(final String icon, final String label) {
		this.icon = icon;
		this.label = label;
	}

	@Override
	public ConfiguredActionDefinition action() {
		return new CopyContentActionBuilder()
				.name("copy")
				.label(label)
				.icon(icon);
	}
}
