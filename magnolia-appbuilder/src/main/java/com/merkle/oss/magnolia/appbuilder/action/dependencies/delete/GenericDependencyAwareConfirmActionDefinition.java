package com.merkle.oss.magnolia.appbuilder.action.dependencies.delete;

import info.magnolia.cms.security.Permission;
import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.module.dependencies.field.ReferencesDefinition;
import info.magnolia.module.dependencies.ui.action.DependencyAwareConfirmationActionDefinition;
import info.magnolia.objectfactory.Components;

import java.util.List;

import com.merkle.oss.magnolia.appbuilder.action.dependencies.ReferenceDefinitionsProvider;
import com.merkle.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.merkle.oss.magnolia.appbuilder.action.rule.PermissionRequiredRuleDefinition;
import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;

import jakarta.inject.Inject;

public class GenericDependencyAwareConfirmActionDefinition extends DependencyAwareConfirmationActionDefinition {
	private final ReferenceDefinitionsProvider referenceDefinitionsProvider;

	public GenericDependencyAwareConfirmActionDefinition() {
		this(Components.getComponent(ReferenceDefinitionsProvider.class));
	}
	@Inject
	public GenericDependencyAwareConfirmActionDefinition(final ReferenceDefinitionsProvider referenceDefinitionsProvider) {
		this.referenceDefinitionsProvider = referenceDefinitionsProvider;
		setName("confirmDelete");
		setLabel("actions.confirmDeletion");
		setConfirmationHeader("actions.confirmDeletion.confirmationHeader");
		setConfirmationMessage("actions.confirmDeletion.confirmationMessage");
		setProceedLabel("actions.confirmDeletion.proceedLabel");
		setCancelLabel("actions.confirmDeletion.cancelLabel");
		setIcon(MagnoliaIcons.DELETE.getCssClass());
		setSuccessActionName("delete");
		setAvailability(new AvailabilityDefinitionBuilder()
				.rule(new JcrIsNotDeletedRuleDefinition())
				.rule(new PermissionRequiredRuleDefinition(Permission.REMOVE))
				.build()
		);
	}

	@Override
	public List<ReferencesDefinition> getReferences() {
		return referenceDefinitionsProvider.get();
	}
}
