package com.namics.oss.magnolia.appbuilder.action.edit;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.action.OpenEditDialogActionBuilder;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;

import javax.annotation.Nullable;

public class EditAppActionDefinition implements AppActionDefinition {
	public static EditAppActionDefinition FOLDER = new EditAppActionDefinition(
			"editFolder",
			"ui-framework:folder",
			MgnlIcon.EDIT,
			"actions.editFolder"
	);
	private final String name;
	private final String dialogName;
	private final String icon;
	@Nullable
	private final String label;

	public EditAppActionDefinition(final String name, final String dialogName) {
		this(name, dialogName, MgnlIcon.EDIT, null);
	}

	public EditAppActionDefinition(final String name, final String dialogName, final String icon, @Nullable final String label) {
		this.name = name;
		this.dialogName = dialogName;
		this.icon = icon;
		this.label = label;
	}

	@Override
	public ConfiguredActionDefinition action() {
		return new OpenEditDialogActionBuilder()
				.name(name)
				.label(label)
				.icon(icon)
				.dialogName(dialogName);
	}
}
