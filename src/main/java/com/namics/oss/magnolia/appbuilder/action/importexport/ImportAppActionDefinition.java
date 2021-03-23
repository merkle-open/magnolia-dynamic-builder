package com.namics.oss.magnolia.appbuilder.action.importexport;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.action.OpenCreateDialogActionBuilder;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;

public class ImportAppActionDefinition implements AppActionDefinition {

	@Override
	public ConfiguredActionDefinition action() {
		return new OpenCreateDialogActionBuilder()
				.name("import")
				.label("actions.import")
				.icon(MgnlIcon.IMPORT)
				.dialogName("ui-admincentral:import");
	}

	@Override
	public boolean multiple() {
		return true;
	}
}
