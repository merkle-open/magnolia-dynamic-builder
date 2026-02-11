package com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.area;

import com.merkle.oss.magnolia.appbuilder.action.OpenDialogAction;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.EditElementAppActionDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import info.magnolia.cms.security.Permission;
import info.magnolia.pages.app.detail.action.availability.IsAreaCreatedRuleDefinition;
import info.magnolia.pages.app.detail.action.availability.IsElementEditableRuleDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

public class EditAreaAppActionDefinition extends EditElementAppActionDefinition {

    @Override
    public OpenDialogAction.Definition action(final DropConstraintDefinition dropConstraint) {
        final OpenDialogAction.Definition definition = super.action(dropConstraint);
        definition.setName("editArea");
        definition.setAvailability(new AvailabilityDefinitionBuilder()
                .rule(new PermissionRequiredRuleDefinition(Permission.WRITE))
                .rule(new IsElementEditableRuleDefinition())
                .rule(new IsAreaCreatedRuleDefinition())
                .build()
        );
        return definition;
    }
}
