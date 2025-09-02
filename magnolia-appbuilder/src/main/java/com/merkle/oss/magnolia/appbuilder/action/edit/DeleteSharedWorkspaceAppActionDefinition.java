package com.merkle.oss.magnolia.appbuilder.action.edit;

import info.magnolia.jcr.util.NodeUtil;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.action.AbstractAction;
import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.api.action.ConfiguredActionDefinition;
import info.magnolia.ui.contentapp.action.JcrCommandActionDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;

import javax.jcr.Node;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;

import jakarta.inject.Inject;

/**
 * Doesn't trigger unpublication.<br>
 * Default DeleteAction executes delete command, which executes delete and unpublish
 */
public class DeleteSharedWorkspaceAppActionDefinition implements AppActionDefinition {

	@Override
	public DeleteAction.Definition action(final DropConstraintDefinition dropConstraint) {
		final JcrCommandActionDefinition deleteAction = new DeleteAppActionDefinition().action(dropConstraint);
		final DeleteAction.Definition definition = new DeleteAction.Definition();
		definition.setName(deleteAction.getName());
		definition.setLabel(deleteAction.getLabel());
		definition.setAvailability(deleteAction.getAvailability());
		return definition;
	}

	@Override
	public boolean multiple() {
		return true;
	}

	@Override
	public boolean isCallback() {
		return true;
	}

	public static class DeleteAction extends AbstractAction<DeleteAction.Definition> {
        private final ValueContext<Node> valueContext;

        @Inject
		public DeleteAction(
				final Definition definition,
				final ValueContext<Node> valueContext
		) {
			super(definition);
            this.valueContext = valueContext;
        }

		@Override
		public void execute() throws ActionExecutionException {
            try {
				final Node node = valueContext.getSingleOrThrow();
                node.remove();
            	node.getSession().save();
            } catch (Exception e) {
                throw new ActionExecutionException("Failed to delete node " + valueContext.getSingle().map(NodeUtil::getPathIfPossible), e);
            }
		}

		public static class Definition extends ConfiguredActionDefinition {
			public Definition() {
				setImplementationClass(DeleteAction.class);
			}
		}
	}
}
