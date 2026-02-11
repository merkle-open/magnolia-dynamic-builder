package com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.component;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.pages.app.detail.action.DuplicateComponentActionDefinition;
import info.magnolia.pages.app.detail.action.availability.IsComponentDuplicatableRuleDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

public class DuplicateComponentAppActionDefinition implements AppActionDefinition {

    @Override
    public DuplicateComponentActionDefinition action(final DropConstraintDefinition dropConstraint) {
        final DuplicateComponentActionDefinition definition =new DuplicateComponentActionDefinition();
        definition.setName("duplicateComponent");
        definition.setIcon(MagnoliaIcons.DUPLICATE.getCssClass());
        definition.setAvailability(new AvailabilityDefinitionBuilder()
                .rule(new PermissionRequiredRuleDefinition(Permission.WRITE))
                .rule(new IsComponentDuplicatableRuleDefinition())
                .build()
        );
        return definition;
    }
}
