package com.namics.oss.magnolia.appbuilder.action.activation;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.action.deprecated.DeactivationActionBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.permission.AccessBuilder;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;

public class DeactivateAppActionDefinition implements AppActionDefinition {

	@Override
	public ConfiguredActionDefinition action() {
		return new DeactivationActionBuilder()
				.name("deactivate")
				.label("actions.deactivate")
				.icon(MgnlIcon.UNPUBLISH)
				.command("deactivate")
				.availability(new AvailabilityBuilder().access(new AccessBuilder().roles("publisher")));
	}

	@Override
	public boolean multiple() {
		return true;
	}
}
