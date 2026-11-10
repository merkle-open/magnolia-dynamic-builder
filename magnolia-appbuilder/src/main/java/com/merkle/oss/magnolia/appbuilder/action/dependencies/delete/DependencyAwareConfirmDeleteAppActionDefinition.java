package com.merkle.oss.magnolia.appbuilder.action.dependencies.delete;

import info.magnolia.module.dependencies.field.ReferencesDefinition;
import info.magnolia.module.dependencies.ui.action.DependencyAwareConfirmationActionDefinition;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.contentapp.action.DeleteNodesConfirmationActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import java.util.List;

import com.merkle.oss.magnolia.appbuilder.action.dependencies.ReferenceDefinitionsProvider;
import com.merkle.oss.magnolia.appbuilder.action.edit.ConfirmDeleteAppActionDefinition;

import jakarta.inject.Inject;
import jakarta.inject.Provider;

public class DependencyAwareConfirmDeleteAppActionDefinition extends ConfirmDeleteAppActionDefinition {
	private final ReferenceDefinitionsProvider referenceDefinitionsProvider;

	public DependencyAwareConfirmDeleteAppActionDefinition() {
		this(Components.getComponent(ReferenceDefinitionsProvider.class));
	}
	@Inject
	public DependencyAwareConfirmDeleteAppActionDefinition(final ReferenceDefinitionsProvider referenceDefinitionsProvider) {
		this.referenceDefinitionsProvider = referenceDefinitionsProvider;
	}

	@Override
	public DeleteNodesConfirmationActionDefinition action(final DropConstraintDefinition dropConstraint) {
		final DeleteNodesConfirmationActionDefinition action = super.action(dropConstraint);
		final DependencyAwareConfirmationActionDefinition definition = new DeferredDependencyAwareConfirmationActionDefinition(referenceDefinitionsProvider::get);
		definition.setName(action.getName());
		definition.setLabel(action.getLabel());
		definition.setConfirmationHeader(action.getConfirmationHeader());
		definition.setConfirmationMessage(action.getConfirmationMessage());
		definition.setProceedLabel(action.getProceedLabel());
		definition.setCancelLabel(action.getCancelLabel());
		definition.setIcon(action.getIcon());
		definition.setSuccessActionName(action.getSuccessActionName());
		definition.setAvailability(action.getAvailability());
		return definition;
	}

	public static class DeferredDependencyAwareConfirmationActionDefinition extends DependencyAwareConfirmationActionDefinition {
		private final Provider<List<ReferencesDefinition>> referencesProvider;
		public DeferredDependencyAwareConfirmationActionDefinition(final Provider<List<ReferencesDefinition>> referencesProvider) {
			this.referencesProvider = referencesProvider;
		}
		@Override
		public List<ReferencesDefinition> getReferences() {
			return referencesProvider.get();
		}
	}
}
