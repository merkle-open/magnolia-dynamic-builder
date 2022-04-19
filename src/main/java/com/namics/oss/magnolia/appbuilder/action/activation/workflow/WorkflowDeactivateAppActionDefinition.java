package com.namics.oss.magnolia.appbuilder.action.activation.workflow;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityRuleBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.permission.AccessBuilder;
import info.magnolia.module.workflow.action.OpenPublicationDialogActionDefinition;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.framework.availability.IsNotDeletedRule;
import info.magnolia.ui.framework.availability.IsPublishedRule;

import java.util.Map;

public class WorkflowDeactivateAppActionDefinition implements AppActionDefinition {
    private final Map<String, Class<?>> formTypes;

    public WorkflowDeactivateAppActionDefinition() {
        this(FormTypeProvider.DEFAULT_FORM_TYPES);
    }

    public WorkflowDeactivateAppActionDefinition(final Map<String, Class<?>> formTypes) {
        this.formTypes = formTypes;
    }

    @Override
    public ConfiguredActionDefinition action() {
        final OpenPublicationDialogActionDefinition action = new OpenPublicationDialogActionDefinition();
        action.setCommand("deactivate");
        action.setCatalog("workflow");
        action.setName("deactivate");
        action.setLabel("actions.deactivate");
        action.setDialogName("workflow:unpublish");
        action.setIcon(MgnlIcon.UNPUBLISH);
        action.setFormTypes(FormTypeProvider.getFormTypes(formTypes));
        action.setAvailability(new AvailabilityBuilder()
                .access(new AccessBuilder().roles("editor", "publisher"))
                .writePermissionRequired(true)
                .rules(
                        new AvailabilityRuleBuilder().implementationClass(IsNotDeletedRule.class),
                        new AvailabilityRuleBuilder().implementationClass(IsPublishedRule.class)
                )
        );
        return action;
    }

    @Override
    public boolean multiple() {
        return true;
    }
}
