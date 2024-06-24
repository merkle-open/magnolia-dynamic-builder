package com.namics.oss.magnolia.appbuilder.action.edit;

import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.contentapp.action.DeleteNodesConfirmationActionDefinition;

import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.namics.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;

public class ConfirmDeleteAppActionDefinition implements AppActionDefinition {
	private final String icon;

	public ConfirmDeleteAppActionDefinition() {
		this(MagnoliaIcons.DELETE.getCssClass());
	}

	public ConfirmDeleteAppActionDefinition(final String icon) {
		this.icon = icon;
	}

	@Override
	public ConfiguredActionDefinition action() {
		final DeleteNodesConfirmationActionDefinition definition = new DeleteNodesConfirmationActionDefinition();
		definition.setName("confirmDelete");
		definition.setLabel("actions.confirmDeletion");
		definition.setConfirmationHeader("actions.confirmDeletion.confirmationHeader");
		definition.setConfirmationMessage("actions.confirmDeletion.confirmationMessage");
		definition.setProceedLabel("actions.confirmDeletion.proceedLabel");
		definition.setCancelLabel("actions.confirmDeletion.cancelLabel");
		definition.setIcon(icon);
		definition.setSuccessActionName("delete");
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.rule(new JcrIsNotDeletedRuleDefinition())
				.rule(new PermissionRequiredRuleDefinition(Permission.REMOVE))
				.build()
		);
		return definition;
	}

	@Override
	public boolean multiple() {
		return true;
	}
}
