package com.merkle.oss.magnolia.appbuilder.action.edit;

import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.edit.clipboard.CopyContentActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;

public class CopyAppActionDefinition implements AppActionDefinition {
	private final String icon;
	private final String label;

	public CopyAppActionDefinition() {
		this(MagnoliaIcons.COPY.getCssClass(), "actions.copy");
	}

	public CopyAppActionDefinition(final String icon, final String label) {
		this.icon = icon;
		this.label = label;
	}

	@Override
	public CopyContentActionDefinition action(final DropConstraintDefinition dropConstraint) {
		final CopyContentActionDefinition definition = new CopyContentActionDefinition();
		definition.setName("copy");
		definition.setLabel(label);
		definition.setIcon(icon);
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.rule(new JcrIsNotDeletedRuleDefinition())
				.rule(new PermissionRequiredRuleDefinition(Permission.READ))
				.build()
		);
		return definition;
	}
}
