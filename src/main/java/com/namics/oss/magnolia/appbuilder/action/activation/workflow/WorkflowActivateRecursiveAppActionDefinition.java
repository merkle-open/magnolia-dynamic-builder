package com.namics.oss.magnolia.appbuilder.action.activation.workflow;

import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.availability.rule.JcrPublishableRuleDefinition;
import info.magnolia.ui.dialog.actions.OpenDialogActionDefinition;

import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;

public class WorkflowActivateRecursiveAppActionDefinition implements AppActionDefinition {

    @Override
    public ConfiguredActionDefinition action() {
        final OpenDialogActionDefinition definition = new OpenDialogActionDefinition();
        definition.setName("activateRecursive");
        definition.setDialogId("workflow-pages:publishRecursive");
        definition.setIcon(MagnoliaIcons.PUBLISH_INCL_SUB.getCssClass());
        definition.setLabel("actions.activateRecursive");
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
