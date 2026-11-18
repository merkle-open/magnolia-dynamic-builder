package com.merkle.oss.magnolia.appbuilder.action.dependencies.delete;

import info.magnolia.module.dependencies.field.ReferencesDefinition;
import info.magnolia.module.dependencies.ui.action.DependencyAwareConfirmationActionDefinition;
import info.magnolia.objectfactory.Components;

import java.util.List;

import com.merkle.oss.magnolia.appbuilder.action.dependencies.ReferenceDefinitionsProvider;

import jakarta.inject.Inject;

public class GenericDependencyAwareConfirmActionDefinition extends DependencyAwareConfirmationActionDefinition {
	private final ReferenceDefinitionsProvider referenceDefinitionsProvider;

	public GenericDependencyAwareConfirmActionDefinition() {
		this(Components.getComponent(ReferenceDefinitionsProvider.class));
	}
	@Inject
	public GenericDependencyAwareConfirmActionDefinition(final ReferenceDefinitionsProvider referenceDefinitionsProvider) {
		this.referenceDefinitionsProvider = referenceDefinitionsProvider;
	}

	@Override
	public List<ReferencesDefinition> getReferences() {
		return referenceDefinitionsProvider.get();
	}
}
