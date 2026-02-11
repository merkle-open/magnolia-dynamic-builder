package com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.component;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.pages.app.detail.action.MoveComponentActionDefinition;
import info.magnolia.pages.app.detail.action.availability.IsComponentMovableRuleDefinition;
import info.magnolia.pages.app.detail.action.availability.IsComponentMovingRuleDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

public class StopMoveComponentAppActionDefinition implements AppActionDefinition {
    @Override
    public MoveComponentActionDefinition action(final DropConstraintDefinition dropConstraint) {
        final MoveComponentActionDefinition definition = new MoveComponentActionDefinition();
        definition.setName("stopMoveComponent");
        definition.setStart(false);
        definition.setServerSide(true);
        definition.setIcon(MagnoliaIcons.DELETE.getCssClass());
        definition.setAvailability(new AvailabilityDefinitionBuilder()
                .rule(new PermissionRequiredRuleDefinition(Permission.WRITE))
                .rule(new IsComponentMovingRuleDefinition())
                .build()
        );
        return definition;
    }
}
