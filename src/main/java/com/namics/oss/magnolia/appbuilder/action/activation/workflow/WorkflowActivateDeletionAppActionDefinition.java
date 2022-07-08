package com.namics.oss.magnolia.appbuilder.action.activation.workflow;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.AvailabilityDefinitionBuilder;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.availability.rule.JcrIsDeletedRuleDefinition;
import info.magnolia.ui.availability.rule.JcrPublishableRuleDefinition;
import info.magnolia.ui.dialog.actions.OpenDialogActionDefinition;

public class WorkflowActivateDeletionAppActionDefinition implements AppActionDefinition {

    @Override
    public ConfiguredActionDefinition action() {
        final OpenDialogActionDefinition definition = new OpenDialogActionDefinition();
        definition.setName("activate");
        definition.setDialogId("workflow-pages:publishDeletion");
        definition.setIcon(MgnlIcon.PUBLISH);
        definition.setLabel("actions.activateDeleted");
        definition.setAvailability(new AvailabilityDefinitionBuilder()
                .access("editor", "publisher")
                .writePermissionRequired(true)
                .rule(new JcrIsDeletedRuleDefinition())
                .rule(new JcrPublishableRuleDefinition())
                .build());
        return definition;
    }

    @Override
    public boolean multiple() {
        return true;
    }
}
