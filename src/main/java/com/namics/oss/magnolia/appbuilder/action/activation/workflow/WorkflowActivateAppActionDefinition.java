package com.namics.oss.magnolia.appbuilder.action.activation.workflow;

import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.availability.rule.JcrPublishableRuleDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.dialog.actions.OpenDialogActionDefinition;

import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;

public class WorkflowActivateAppActionDefinition implements AppActionDefinition {

    @Override
    public OpenDialogActionDefinition action(final DropConstraintDefinition dropConstraint) {
        final OpenDialogActionDefinition definition = new OpenDialogActionDefinition();
        definition.setName("activate");
        definition.setDialogId("workflow-pages:publish");
        definition.setIcon(MagnoliaIcons.PUBLISH.getCssClass());
        definition.setLabel("actions.activate");
        definition.setAvailability(new AvailabilityDefinitionBuilder()
                .access("editor", "publisher")
                .writePermissionRequired(true)
                .rule(new JcrIsNotDeletedRuleDefinition())
                .rule(new JcrPublishableRuleDefinition())
                .build());
        return definition;
    }

    @Override
    public boolean multiple() {
        return true;
    }
}
