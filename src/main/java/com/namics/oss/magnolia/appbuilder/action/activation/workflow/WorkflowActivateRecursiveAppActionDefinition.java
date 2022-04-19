package com.namics.oss.magnolia.appbuilder.action.activation.workflow;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityRuleBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.permission.AccessBuilder;
import info.magnolia.module.workflow.action.OpenPublicationDialogActionDefinition;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.framework.availability.IsNotDeletedRule;
import info.magnolia.ui.framework.availability.IsPublishableRule;

import java.util.Map;

public class WorkflowActivateRecursiveAppActionDefinition implements AppActionDefinition {
    private final Map<String, Class<?>> formTypes;

    public WorkflowActivateRecursiveAppActionDefinition() {
        this(FormTypeProvider.DEFAULT_FORM_TYPES);
    }

    public WorkflowActivateRecursiveAppActionDefinition(final Map<String, Class<?>> formTypes) {
        this.formTypes = formTypes;
    }

    @Override
    public ConfiguredActionDefinition action() {
        final OpenPublicationDialogActionDefinition action = new OpenPublicationDialogActionDefinition();
        action.setCommand("activate");
        action.setCatalog("workflow");
        action.setName("activateRecursive");
        action.setLabel("actions.activateRecursive");
        action.setDialogName("workflow:publishRecursive");
        action.setIcon(MgnlIcon.PUBLISH_INCL_SUB);
        action.setFormTypes(FormTypeProvider.getFormTypes(formTypes));
        action.setAvailability(new AvailabilityBuilder()
                .writePermissionRequired(true)
                .access(new AccessBuilder().roles("editor", "publisher"))
                .rules(
                        new AvailabilityRuleBuilder().implementationClass(IsPublishableRule.class),
                        new AvailabilityRuleBuilder().implementationClass(IsNotDeletedRule.class)
                )
        );
        return action;
    }

    @Override
    public boolean multiple() {
        return true;
    }
}
