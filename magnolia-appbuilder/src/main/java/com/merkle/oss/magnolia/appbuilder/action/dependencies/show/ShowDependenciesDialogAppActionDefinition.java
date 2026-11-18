package com.merkle.oss.magnolia.appbuilder.action.dependencies.show;

import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;

public class ShowDependenciesDialogAppActionDefinition implements AppActionDefinition {
    @Override
    public ConfiguredActionDefinition action(final DropConstraintDefinition dropConstraint) {
        final GenericViewLinksActionDefinition definition = new GenericViewLinksActionDefinition();
        definition.setName("viewLinks");
        definition.setLabel("actions.viewLinks");
        definition.setDialogId("magnolia-appbuilder:dialogs/viewLinks");
        definition.setIcon(MagnoliaIcons.LINK.getCssClass());
        definition.setAvailability(new AvailabilityDefinitionBuilder()
                .rule(new PermissionRequiredRuleDefinition(Permission.READ))
                .rule(new JcrIsNotDeletedRuleDefinition())
                .root(false)
                .multiple(false)
                .writePermissionRequired(false)
                .build()
        );
        return definition;
    }
}
