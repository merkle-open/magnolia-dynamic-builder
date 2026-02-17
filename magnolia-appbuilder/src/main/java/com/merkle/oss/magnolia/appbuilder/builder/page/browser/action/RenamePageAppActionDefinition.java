package com.merkle.oss.magnolia.appbuilder.builder.page.browser.action;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.OpenDialogAction;
import com.merkle.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.editor.validator.NodeNameValidatorDefinition;

public class RenamePageAppActionDefinition implements AppActionDefinition {

    @Override
    public OpenDialogAction.Definition action(final DropConstraintDefinition dropConstraint) {
        final OpenDialogAction.Definition definition = new OpenDialogAction.Definition(NodeNameValidatorDefinition.Mode.EDIT);
        definition.setName("renamePage");
        definition.setDialogId("pages-app:renamePage");
        definition.setIcon(MagnoliaIcons.EDIT.getCssClass());
        definition.setAvailability(new AvailabilityDefinitionBuilder()
                .rule(new PermissionRequiredRuleDefinition(Permission.WRITE))
                .rule(new JcrIsNotDeletedRuleDefinition())
                .build()
        );
        return definition;
    }
}
