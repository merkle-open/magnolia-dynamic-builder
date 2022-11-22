package com.namics.oss.magnolia.appbuilder.action.importexport;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.JcrIsNotDeletedRuleDefinition;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.dialog.actions.OpenDialogActionDefinition;

public class ImportAppActionDefinition implements AppActionDefinition {

	@Override
	public ConfiguredActionDefinition action() {
		final OpenDialogActionDefinition definition = new OpenDialogActionDefinition();
		definition.setName("import");
		definition.setDialogId("ui-framework-jcr:import");
		definition.setIcon(MgnlIcon.EXPORT);
		definition.setLabel("actions.import");
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.writePermissionRequired(true)
				.rule(new JcrIsNotDeletedRuleDefinition())
				.build()
		);
		return definition;
	}
}
