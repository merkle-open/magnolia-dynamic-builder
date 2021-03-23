package com.namics.oss.magnolia.appbuilder.action.importexport;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.action.OpenExportDialogActionBuilder;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;

public class ExportAppActionDefinition implements AppActionDefinition {

	@Override
	public ConfiguredActionDefinition action() {
		return new OpenExportDialogActionBuilder()
				.name("export")
				.label("actions.export")
				.icon(MgnlIcon.EXPORT)
				.dialogName("ui-admincentral:export");
	}

	@Override
	public boolean multiple() {
		return true;
	}
}
