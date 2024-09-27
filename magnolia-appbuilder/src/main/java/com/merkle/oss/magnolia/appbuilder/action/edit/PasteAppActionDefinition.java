package com.merkle.oss.magnolia.appbuilder.action.edit;

import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.edit.clipboard.CanPasteContentRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.action.edit.clipboard.PasteContentActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;

public class PasteAppActionDefinition implements AppActionDefinition {
	private final String icon;
	private final String label;

	public PasteAppActionDefinition() {
		this(MagnoliaIcons.PASTE.getCssClass(), "actions.paste");
	}

	public PasteAppActionDefinition(final String icon, final String label) {
		this.icon = icon;
		this.label = label;
	}

	@Override
	public PasteContentActionDefinition action(final DropConstraintDefinition dropConstraint) {
		final PasteContentActionDefinition definition = new PasteContentActionDefinition();
		definition.setName("paste");
		definition.setLabel(label);
		definition.setIcon(icon);
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.rule(new JcrIsNotDeletedRuleDefinition())
				.rule(new CanPasteContentRuleDefinition())
				.rule(new PermissionRequiredRuleDefinition(Permission.ADD))
				.build()
		);
		return definition;
	}
}
