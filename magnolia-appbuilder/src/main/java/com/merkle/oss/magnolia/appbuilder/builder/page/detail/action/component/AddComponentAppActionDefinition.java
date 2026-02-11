package com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.component;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.OpenDialogAction;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.pages.app.detail.action.availability.IsAreaAddibleRuleDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.editor.validator.NodeNameValidatorDefinition;

public class AddComponentAppActionDefinition implements AppActionDefinition {

    @Override
    public OpenDialogAction.Definition action(final DropConstraintDefinition dropConstraint) {
        final OpenDialogAction.Definition definition = new OpenDialogAction.Definition(NodeNameValidatorDefinition.Mode.ADD);
        definition.setName("addComponent");
        definition.setDialogId("pages-app:newComponent");
        definition.setIcon(MagnoliaIcons.ADD_ITEM.getCssClass());
        definition.setAvailability(new AvailabilityDefinitionBuilder()
                .rule(new PermissionRequiredRuleDefinition(Permission.WRITE))
                .rule(new IsAreaAddibleRuleDefinition())
                .build()
        );
        return definition;
    }
}
