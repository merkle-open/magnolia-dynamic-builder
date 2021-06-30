package com.namics.oss.magnolia.appbuilder.action.activation;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.action.ActivationActionBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityRuleBuilder;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.framework.availability.IsDeletedRule;

public class ActivateDeletionAppActionDefinition implements AppActionDefinition {

	@Override
	public ConfiguredActionDefinition action() {
		return new ActivationActionBuilder()
				.name("activateDeletion")
				.label("actions.activateDeleted")
				.icon(MgnlIcon.PUBLISH)
				.command("activate")
				.availability(new AvailabilityBuilder()
						.writePermissionRequired(true)
						.rules(new AvailabilityRuleBuilder().implementationClass(IsDeletedRule.class))
				);
	}

	@Override
	public boolean multiple() {
		return true;
	}
}
