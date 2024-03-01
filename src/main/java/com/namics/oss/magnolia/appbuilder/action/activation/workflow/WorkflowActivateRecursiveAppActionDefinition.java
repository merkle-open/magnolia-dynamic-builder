package com.namics.oss.magnolia.appbuilder.action.activation.workflow;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.availability.rule.JcrPublishableRuleDefinition;
import info.magnolia.ui.dialog.actions.OpenDialogActionDefinition;

public class WorkflowActivateRecursiveAppActionDefinition implements AppActionDefinition {

    @Override
    public ConfiguredActionDefinition action() {
        final OpenDialogActionDefinition definition = new OpenDialogActionDefinition();
        definition.setName("activateRecursive");
        definition.setDialogId("workflow-pages:publishRecursive");
        definition.setIcon(MgnlIcon.PUBLISH_INCL_SUB);
        definition.setLabel("actions.activateRecursive");
        definition.setAvailability(new AvailabilityDefinitionBuilder()
                .access("editor", "publisher")
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
