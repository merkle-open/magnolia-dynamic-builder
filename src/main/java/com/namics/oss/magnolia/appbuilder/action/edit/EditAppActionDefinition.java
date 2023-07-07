package com.namics.oss.magnolia.appbuilder.action.edit;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.dialog.actions.OpenDialogActionDefinition;

import javax.annotation.Nullable;

public class EditAppActionDefinition implements AppActionDefinition {
	public static final EditAppActionDefinition FOLDER = new EditAppActionDefinition(
			"editFolder",
			"ui-framework-jcr:rename",
			MgnlIcon.EDIT,
			"actions.editFolder"
	);
	private final String name;
	private final String dialogId;
	private final String icon;
	@Nullable
	private final String label;

	public EditAppActionDefinition(final String name, final String dialogId) {
		this(name, dialogId, MgnlIcon.EDIT, null);
	}

	public EditAppActionDefinition(final String name, final String dialogId, final String icon, @Nullable final String label) {
		this.name = name;
		this.dialogId = dialogId;
		this.icon = icon;
		this.label = label;
	}

	@Override
	public ConfiguredActionDefinition action() {
		final OpenDialogActionDefinition definition = new OpenDialogActionDefinition();
		definition.setName(name);
		definition.setDialogId(dialogId);
		definition.setLabel(label);
		definition.setIcon(icon);
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.rule(new JcrIsNotDeletedRuleDefinition())
				.build()
		);
		return definition;
	}
}
