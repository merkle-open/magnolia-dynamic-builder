package com.namics.oss.magnolia.appbuilder.action.add;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.action.OpenCreateDialogActionBuilder;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;

import javax.annotation.Nullable;

public class AddAppActionDefinition implements AppActionDefinition {
	public static AddAppActionDefinition FOLDER = new AddAppActionDefinition(
			"addFolder",
			NodeTypes.Folder.NAME,
			"ui-framework:folder",
			MgnlIcon.ADD_FOLDER,
			"actions.addFolder"
	);
	private final String name;
	private final String nodeType;
	private final String dialogName;
	private final String icon;
	@Nullable
	private final String label;

	public AddAppActionDefinition(
			final String name,
			final String nodeType,
			final String dialogName,
			final String icon) {
		this(name, nodeType, dialogName, icon, null);
	}

	public AddAppActionDefinition(
			final String name,
			final String nodeType,
			final String dialogName,
			final String icon,
			@Nullable final String label) {
		this.name = name;
		this.nodeType = nodeType;
		this.dialogName = dialogName;
		this.icon = icon;
		this.label = label;
	}

	@Override
	public ConfiguredActionDefinition action() {
		return new OpenCreateDialogActionBuilder()
				.name(name)
				.label(label)
				.dialogName(dialogName)
				.icon(icon)
				.nodeType(nodeType);
	}
}
