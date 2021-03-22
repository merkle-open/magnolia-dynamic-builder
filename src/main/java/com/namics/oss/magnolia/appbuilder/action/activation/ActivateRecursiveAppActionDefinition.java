package com.namics.oss.magnolia.appbuilder.action.activation;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.action.ActivationActionBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.permission.AccessBuilder;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;

public class ActivateRecursiveAppActionDefinition implements AppActionDefinition {

	@Override
	public ConfiguredActionDefinition action() {
		return new ActivationActionBuilder()
				.name("activateRecursive")
				.label("actions.activateRecursive")
				.icon(MgnlIcon.PUBLISH_INCL_SUB)
				.command("activate")
				.recursive(true)
				.asynchronous(true)
				.availability(new AvailabilityBuilder().access(new AccessBuilder().roles("publisher")));
	}

	@Override
	public boolean multiple() {
		return true;
	}
}
