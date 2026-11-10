package com.merkle.oss.magnolia.appbuilder.action.dependencies.show;

import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.module.dependencies.field.ReferencesDefinition;
import info.magnolia.module.dependencies.ui.action.ViewLinksActionDefinition;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import java.util.List;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.dependencies.ReferenceDefinitionsProvider;
import com.merkle.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;

import jakarta.inject.Inject;
import jakarta.inject.Provider;

public class ShowDependenciesDialogAppActionDefinition implements AppActionDefinition {
    private final ReferenceDefinitionsProvider referenceDefinitionsProvider;

    public ShowDependenciesDialogAppActionDefinition() {
        this(Components.getComponent(ReferenceDefinitionsProvider.class));
    }

    @Inject
    public ShowDependenciesDialogAppActionDefinition(final ReferenceDefinitionsProvider referenceDefinitionsProvider) {
        this.referenceDefinitionsProvider = referenceDefinitionsProvider;
    }

    @Override
    public ConfiguredActionDefinition action(final DropConstraintDefinition dropConstraint) {
        final ViewLinksActionDefinition definition = new DeferredViewLinksActionDefinition(referenceDefinitionsProvider::get);
        definition.setName("viewLinks");
        definition.setLabel("actions.viewLinks");
        definition.setDialogId("magnolia-appbuilder:dialogs/viewLinks");
        definition.setIcon(MagnoliaIcons.LINK.getCssClass());
        definition.setAvailability(new AvailabilityDefinitionBuilder()
                .rule(new PermissionRequiredRuleDefinition(Permission.READ))
                .rule(new JcrIsNotDeletedRuleDefinition())
                .root(false)
                .multiple(false)
                .writePermissionRequired(false)
                .build()
        );
        return definition;
    }

    public static class DeferredViewLinksActionDefinition extends ViewLinksActionDefinition {
        private final Provider<List<ReferencesDefinition>> referencesProvider;

        public DeferredViewLinksActionDefinition(final Provider<List<ReferencesDefinition>> referencesProvider) {
            this.referencesProvider = referencesProvider;
        }

        @Override
        public List<ReferencesDefinition> getReferences() {
            return referencesProvider.get();
        }
    }
}
