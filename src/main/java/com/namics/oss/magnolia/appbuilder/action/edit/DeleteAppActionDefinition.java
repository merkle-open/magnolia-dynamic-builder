package com.namics.oss.magnolia.appbuilder.action.edit;

import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.action.DeleteActionBuilder;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;

public class DeleteAppActionDefinition implements AppActionDefinition {

	@Override
	public ConfiguredActionDefinition action() {
		return new DeleteActionBuilder()
				.name("delete")
				.label("actions.delete")
				.asynchronous(true);
	}

	@Override
	public boolean multiple() {
		return true;
	}

	@Override
	public boolean isCallback() {
		return true;
	}
}
