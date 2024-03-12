package com.namics.oss.magnolia.appbuilder.action.add;

import info.magnolia.ui.contentapp.action.CommitActionDefinition;

public class CreateNodeActionDefinition extends CommitActionDefinition {
	private final String nodeType;

	public CreateNodeActionDefinition(final String nodeType) {
		this.nodeType = nodeType;
		setImplementationClass(CreateNodeAction.class);
	}

	public String getNodeType() {
		return nodeType;
	}
}
