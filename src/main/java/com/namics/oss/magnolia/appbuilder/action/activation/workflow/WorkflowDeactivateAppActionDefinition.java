package com.namics.oss.magnolia.appbuilder.action.activation.workflow;

import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.availability.rule.JcrPublishedRuleDefinition;
import info.magnolia.ui.dialog.actions.OpenDialogActionDefinition;

import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;

public class WorkflowDeactivateAppActionDefinition implements AppActionDefinition {

    @Override
    public ConfiguredActionDefinition action() {
        final OpenDialogActionDefinition definition = new OpenDialogActionDefinition();
        definition.setName("deactivate");
        definition.setDialogId("workflow-pages:unPublish");
        definition.setIcon(MagnoliaIcons.UNPUBLISH.getCssClass());
        definition.setLabel("actions.deactivate");
        definition.setAvailability(new AvailabilityDefinitionBuilder()
                .access("editor", "publisher")
                .writePermissionRequired(true)
                .rule(new JcrIsNotDeletedRuleDefinition())
                .rule(new JcrPublishedRuleDefinition())
                .build());
        return definition;
    }

    @Override
    public boolean multiple() {
        return true;
    }
}
