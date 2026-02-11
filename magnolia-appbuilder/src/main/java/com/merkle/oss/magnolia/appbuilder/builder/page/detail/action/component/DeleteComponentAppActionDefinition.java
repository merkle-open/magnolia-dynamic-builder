package com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.component;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.DeleteElementAppActionDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.pages.app.detail.action.availability.IsComponentDeletableRuleDefinition;
import info.magnolia.ui.contentapp.action.ConfirmationActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

public class DeleteComponentAppActionDefinition implements AppActionDefinition {

    @Override
    public ConfirmationActionDefinition action(final DropConstraintDefinition dropConstraint) {
        final ConfirmationActionDefinition definition = new ConfirmationActionDefinition();
        definition.setName("deleteComponent");
        definition.setIcon(MagnoliaIcons.DELETE.getCssClass());
        definition.setSuccessActionName(DeleteElementAppActionDefinition.NAME);
        definition.setAvailability(new AvailabilityDefinitionBuilder()
                .rule(new PermissionRequiredRuleDefinition(Permission.WRITE))
                .rule(new IsComponentDeletableRuleDefinition())
                .build()
        );
        return definition;
    }
}
