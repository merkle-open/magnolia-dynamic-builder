package com.merkle.oss.magnolia.appbuilder.action.dependencies.show;

import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.module.dependencies.field.ReferencesDefinition;
import info.magnolia.module.dependencies.ui.action.ViewLinksActionDefinition;
import info.magnolia.objectfactory.Components;

import java.util.List;

import com.merkle.oss.magnolia.appbuilder.action.dependencies.ReferenceDefinitionsProvider;
import com.merkle.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;

import jakarta.inject.Inject;

public class GenericViewLinksActionDefinition extends ViewLinksActionDefinition {
    private final ReferenceDefinitionsProvider referenceDefinitionsProvider;

    public GenericViewLinksActionDefinition() {
        this(Components.getComponent(ReferenceDefinitionsProvider.class));
    }

    @Inject
    public GenericViewLinksActionDefinition(final ReferenceDefinitionsProvider referenceDefinitionsProvider) {
        this.referenceDefinitionsProvider = referenceDefinitionsProvider;
        setName("viewLinks");
        setLabel("actions.viewLinks");
        setDialogId("magnolia-appbuilder:dialogs/viewLinks");
        setIcon(MagnoliaIcons.LINK.getCssClass());
        setAvailability(new AvailabilityDefinitionBuilder()
                .rule(new PermissionRequiredRuleDefinition(Permission.READ))
                .rule(new JcrIsNotDeletedRuleDefinition())
                .root(false)
                .multiple(false)
                .writePermissionRequired(false)
                .build()
        );
    }

    @Override
    public List<ReferencesDefinition> getReferences() {
        return referenceDefinitionsProvider.get();
    }
}
