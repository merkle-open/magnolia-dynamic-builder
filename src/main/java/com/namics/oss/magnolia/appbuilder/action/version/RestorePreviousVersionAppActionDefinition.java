package com.namics.oss.magnolia.appbuilder.action.version;

import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityRuleBuilder;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.contentapp.detail.action.RestorePreviousVersionActionDefinition;
import info.magnolia.ui.framework.availability.IsDeletedRule;

public class RestorePreviousVersionAppActionDefinition implements AppActionDefinition {

	@Override
	public ConfiguredActionDefinition action() {
		ConfiguredActionDefinition actionDefinition = new RestorePreviousVersionActionDefinition();
		actionDefinition.setName("restorePreviousVersion");
		actionDefinition.setLabel("actions.restorePreviousVersion");
		actionDefinition.setIcon("icon-undo");
		actionDefinition.setAvailability(new AvailabilityBuilder()
				.writePermissionRequired(true)
				.rules(new AvailabilityRuleBuilder().implementationClass(IsDeletedRule.class))
		);
		return actionDefinition;
	}
}
