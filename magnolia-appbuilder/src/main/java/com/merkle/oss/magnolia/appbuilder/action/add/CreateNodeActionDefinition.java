package com.merkle.oss.magnolia.appbuilder.action.add;

import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.contentapp.action.CommitActionDefinition;
import info.magnolia.ui.editor.FormView;

import javax.jcr.Node;

public class CreateNodeActionDefinition extends CommitActionDefinition {
	private final String nodeType;
	private final Class<? extends NodeNameProvider> nodeNameProviderClass;

	public CreateNodeActionDefinition(final String nodeType, final Class<? extends NodeNameProvider> nodeNameProviderClass) {
		this.nodeType = nodeType;
        this.nodeNameProviderClass = nodeNameProviderClass;
        setImplementationClass(CreateNodeAction.class);
	}

	public String getNodeType() {
		return nodeType;
	}

	public Class<? extends NodeNameProvider> getNodeNameProviderClass() {
		return nodeNameProviderClass;
	}

	public interface NodeNameProvider {
		String get(FormView<Node> formView, Node parent) throws ActionExecutionException;
	}
}
