package com.namics.oss.magnolia.appbuilder.action.add;

import com.namics.oss.magnolia.appbuilder.action.JcrNameValidatingOpenDialogAction;
import info.magnolia.ui.contentapp.action.CommitActionDefinition;
import info.magnolia.ui.editor.validator.NodeNameValidatorDefinition;

public class OpenCreateDialogActionDefinition extends JcrNameValidatingOpenDialogAction.Definition {
	private final CommitActionDefinition commitAction;

	public OpenCreateDialogActionDefinition(final CommitActionDefinition commitAction) {
		super(NodeNameValidatorDefinition.Mode.ADD);
		this.commitAction = commitAction;
		setPopulate(false);
		setImplementationClass(OpenCreateDialogAction.class);
	}

	public CommitActionDefinition getCommitAction() {
		return commitAction;
	}
}
