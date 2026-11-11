package com.merkle.oss.magnolia.appbuilder.action.dependencies.delete;

import info.magnolia.module.dependencies.ui.action.DependencyAwareConfirmationActionDefinition;
import info.magnolia.ui.contentapp.action.DeleteNodesConfirmationActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import com.merkle.oss.magnolia.appbuilder.action.edit.ConfirmDeleteAppActionDefinition;

public class DependencyAwareConfirmDeleteAppActionDefinition extends ConfirmDeleteAppActionDefinition {
	@Override
	public DeleteNodesConfirmationActionDefinition action(final DropConstraintDefinition dropConstraint) {
		final DeleteNodesConfirmationActionDefinition action = super.action(dropConstraint);
		final DependencyAwareConfirmationActionDefinition definition = new GenericDependencyAwareConfirmActionDefinition();
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
}
