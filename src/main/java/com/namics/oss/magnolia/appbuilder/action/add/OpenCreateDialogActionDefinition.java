package com.namics.oss.magnolia.appbuilder.action.add;

import info.magnolia.ui.contentapp.action.CommitActionDefinition;
import info.magnolia.ui.dialog.actions.OpenDialogActionDefinition;

public class OpenCreateDialogActionDefinition extends OpenDialogActionDefinition {
	private final CommitActionDefinition commitAction;

	public OpenCreateDialogActionDefinition(final CommitActionDefinition commitAction) {
		this.commitAction = commitAction;
		setPopulate(false);
		setImplementationClass(OpenCreateDialogAction.class);
	}

	public CommitActionDefinition getCommitAction() {
		return commitAction;
	}
}
