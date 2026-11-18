package com.merkle.oss.magnolia.appbuilder.action.dependencies.show;

import info.magnolia.module.dependencies.field.ReferencesDefinition;
import info.magnolia.module.dependencies.ui.action.ViewLinksActionDefinition;
import info.magnolia.objectfactory.Components;

import java.util.List;

import com.merkle.oss.magnolia.appbuilder.action.dependencies.ReferenceDefinitionsProvider;

import jakarta.inject.Inject;

public class GenericViewLinksActionDefinition extends ViewLinksActionDefinition {
    private final ReferenceDefinitionsProvider referenceDefinitionsProvider;

    public GenericViewLinksActionDefinition() {
        this(Components.getComponent(ReferenceDefinitionsProvider.class));
    }

    @Inject
    public GenericViewLinksActionDefinition(final ReferenceDefinitionsProvider referenceDefinitionsProvider) {
        this.referenceDefinitionsProvider = referenceDefinitionsProvider;
    }

    @Override
    public List<ReferencesDefinition> getReferences() {
        return referenceDefinitionsProvider.get();
    }
}
