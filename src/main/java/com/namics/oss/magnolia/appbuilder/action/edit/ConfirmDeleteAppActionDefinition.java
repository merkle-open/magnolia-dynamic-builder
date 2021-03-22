package com.namics.oss.magnolia.appbuilder.action.edit;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.action.deprecated.DeleteConfirmationActionBuilder;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;

public class ConfirmDeleteAppActionDefinition implements AppActionDefinition {
	private final String icon;

	public ConfirmDeleteAppActionDefinition() {
		this(MgnlIcon.DELETE);
	}

	public ConfirmDeleteAppActionDefinition(final String icon) {
		this.icon = icon;
	}

	@Override
	public ConfiguredActionDefinition action() {
		return new DeleteConfirmationActionBuilder()
				.name("confirmDelete")
				.label("actions.confirmDeletion")
				.confirmationHeader("actions.confirmDeletion.confirmationHeader")
				.confirmationMessage("actions.confirmDeletion.confirmationMessage")
				.proceedLabel("actions.confirmDeletion.proceedLabel")
				.cancelLabel("actions.confirmDeletion.cancelLabel")
				.icon(icon)
				.successActionName("delete");
	}

	@Override
	public boolean multiple() {
		return true;
	}
}
