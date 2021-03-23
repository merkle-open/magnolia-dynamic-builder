package com.namics.oss.magnolia.appbuilder.action.edit;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.action.PasteContentActionBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityRuleBuilder;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.framework.availability.AcceptsClipboardContent;

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
		return new PasteContentActionBuilder()
				.name("paste")
				.label(label)
				.icon(icon)
				.availability(new AvailabilityBuilder()
						.rules(new AvailabilityRuleBuilder().implementationClass(AcceptsClipboardContent.class))
				);
	}
}
