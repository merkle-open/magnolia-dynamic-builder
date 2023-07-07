package com.namics.oss.magnolia.appbuilder.action.add;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;

import javax.annotation.Nullable;

public class AddAppActionDefinition implements AppActionDefinition {
	public static final AddAppActionDefinition FOLDER = new AddAppActionDefinition(
			"addFolder",
			NodeTypes.Folder.NAME,
			"magnolia-appbuilder:createFolder",
			MgnlIcon.ADD_FOLDER,
			"actions.addFolder"
	);
	private final String name;
	private final String nodeType;
	private final String dialogId;
	private final String icon;
	@Nullable
	private final String label;
	@Nullable
	private final String nodeNameProperty;

	public AddAppActionDefinition(
			final String name,
			final String nodeType,
			final String dialogId,
			final String icon) {
		this(name, nodeType, dialogId, icon, null);
	}

	public AddAppActionDefinition(
			final String name,
			final String nodeType,
			final String dialogId,
			final String icon,
			@Nullable final String label) {
		this(name, nodeType, dialogId, icon, label, "jcrName");
	}

	public AddAppActionDefinition(
			final String name,
			final String nodeType,
			final String dialogId,
			final String icon,
			@Nullable final String label,
			@Nullable final String nodeNameProperty) {
		this.name = name;
		this.nodeType = nodeType;
		this.dialogId = dialogId;
		this.icon = icon;
		this.label = label;
		this.nodeNameProperty = nodeNameProperty;
	}

	@Override
	public ConfiguredActionDefinition action() {
		final CreateNodeActionDefinition commitAction = new CreateNodeActionDefinition(nodeType, nodeNameProperty);
		final OpenCreateDialogActionDefinition definition = new OpenCreateDialogActionDefinition(commitAction);
		definition.setName(name);
		definition.setDialogId(dialogId);
		definition.setLabel(label);
		definition.setIcon(icon);
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.writePermissionRequired(true)
				.rule(new JcrIsNotDeletedRuleDefinition())
				.build()
		);
		return definition;
	}
}
