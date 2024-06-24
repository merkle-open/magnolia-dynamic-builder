package com.namics.oss.magnolia.appbuilder.action.importexport;

import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.importexport.command.JcrExportCommand;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.contentapp.action.JcrExportActionDefinition;

import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.rule.HasValueContextRule;
import com.namics.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.namics.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;

public class ExportAppActionDefinition implements AppActionDefinition {

	@Override
	public ConfiguredActionDefinition action() {
		final JcrExportActionDefinition definition = new JcrExportActionDefinition();
		definition.setFormat(JcrExportCommand.Format.XML);
		definition.setName("export");
		definition.setIcon(MagnoliaIcons.EXPORT.getCssClass());
		definition.setLabel("actions.export");
		definition.setAvailability(new AvailabilityDefinitionBuilder()
				.rule(new HasValueContextRule.Definition())
				.rule(new JcrIsNotDeletedRuleDefinition())
				.rule(new PermissionRequiredRuleDefinition(Permission.READ))
				.build()
		);
		return definition;
	}
}
