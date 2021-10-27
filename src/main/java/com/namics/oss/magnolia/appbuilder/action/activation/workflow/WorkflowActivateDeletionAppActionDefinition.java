package com.namics.oss.magnolia.appbuilder.action.activation.workflow;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityRuleBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.permission.AccessBuilder;
import info.magnolia.module.workflow.action.OpenPublicationDialogActionDefinition;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.framework.availability.IsDeletedRule;

public class WorkflowActivateDeletionAppActionDefinition implements AppActionDefinition {

    @Override
    public ConfiguredActionDefinition action() {
        final OpenPublicationDialogActionDefinition action = new OpenPublicationDialogActionDefinition();
        action.setCommand("activate");
        action.setCatalog("workflow");
        action.setName("activateDeletion");
        action.setLabel("actions.activateDeleted");
        action.setDialogName("workflow:publishDeletion");
        action.setIcon(MgnlIcon.PUBLISH);
        action.setAvailability(new AvailabilityBuilder()
                .access(new AccessBuilder().roles("editor", "publisher"))
                .writePermissionRequired(true)
                .rules(
                        new AvailabilityRuleBuilder().implementationClass(IsDeletedRule.class)
                )
        );
        return action;
    }

    @Override
    public boolean multiple() {
        return true;
    }
}
