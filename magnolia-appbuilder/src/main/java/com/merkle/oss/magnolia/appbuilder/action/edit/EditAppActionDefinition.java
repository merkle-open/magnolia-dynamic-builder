package com.merkle.oss.magnolia.appbuilder.action.edit;

import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.editor.validator.NodeNameValidatorDefinition;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.OpenDialogAction;
import com.merkle.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;

import jakarta.annotation.Nullable;

public class EditAppActionDefinition implements AppActionDefinition {
	public static final EditAppActionDefinition FOLDER = new EditAppActionDefinition(
			"editFolder",
			"ui-framework-jcr:rename",
			MagnoliaIcons.EDIT.getCssClass(),
			"actions.editFolder"
	);
	private final String name;
	private final String dialogId;
	private final String icon;
	@Nullable
	private final String label;

	public EditAppActionDefinition(final String name, final String dialogId) {
		this(name, dialogId, MagnoliaIcons.EDIT.getCssClass(), null);
	}

	public EditAppActionDefinition(final String name, final String dialogId, final String icon, @Nullable final String label) {
		this.name = name;
		this.dialogId = dialogId;
		this.icon = icon;
		this.label = label;
	}

	@Override
	public OpenDialogAction.Definition action(final DropConstraintDefinition dropConstraint) {
		final OpenDialogAction.Definition definition = new OpenDialogAction.Definition(NodeNameValidatorDefinition.Mode.EDIT);
		definition.setName(name);
		definition.setDialogId(dialogId);
		definition.setLabel(label);
		definition.setIcon(icon);
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.rule(new JcrIsNotDeletedRuleDefinition())
				.rule(new PermissionRequiredRuleDefinition(Permission.SET | Permission.READ))
				.build()
		);
		return definition;
	}
}
