package com.namics.oss.magnolia.appbuilder.action.importexport;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.JcrIsNotDeletedRuleDefinition;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.dialog.actions.OpenDialogActionDefinition;

public class ExportAppActionDefinition implements AppActionDefinition {

	@Override
	public ConfiguredActionDefinition action() {
		final OpenDialogActionDefinition definition = new OpenDialogActionDefinition();
		definition.setName("export");
		definition.setDialogId("ui-admincentral:export");
		definition.setIcon(MgnlIcon.EXPORT);
		definition.setLabel("actions.export");
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.rule(new JcrIsNotDeletedRuleDefinition())
				.build()
		);
		return definition;
	}

	@Override
	public boolean multiple() {
		return true;
	}
}
