package com.namics.oss.magnolia.appbuilder.action.add;

import info.magnolia.ui.contentapp.action.CommitActionDefinition;

public class CreateNodeActionDefinition extends CommitActionDefinition {
	private final String nodeType;
	private final String nodeNameProperty;

	public CreateNodeActionDefinition(final String nodeType, final String nodeNameProperty) {
		this.nodeType = nodeType;
		this.nodeNameProperty = nodeNameProperty;
		setImplementationClass(CreateNodeAction.class);
	}

	public String getNodeType() {
		return nodeType;
	}

	public String getNodeNameProperty() {
		return nodeNameProperty;
	}
}
