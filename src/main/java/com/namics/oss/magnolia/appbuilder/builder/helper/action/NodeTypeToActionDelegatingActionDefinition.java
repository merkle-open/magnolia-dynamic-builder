package com.namics.oss.magnolia.appbuilder.builder.helper.action;

import info.magnolia.ui.api.action.ConfiguredActionDefinition;

import java.util.HashMap;
import java.util.Map;

public class NodeTypeToActionDelegatingActionDefinition extends ConfiguredActionDefinition {

	private Map<String, String> nodeTypeActionMapping = new HashMap<>();
	private String fallbackAction;

	public NodeTypeToActionDelegatingActionDefinition() {
		setImplementationClass(NodeTypeToActionDelegatingAction.class);
	}

	public Map<String, String> getNodeTypeActionMapping() {
		return nodeTypeActionMapping;
	}

	public void setNodeTypeActionMapping(Map<String, String> nodeTypeActionMapping) {
		this.nodeTypeActionMapping = nodeTypeActionMapping;
	}

	public String getFallbackAction() {
		return fallbackAction;
	}

	public void setFallbackAction(String fallbackAction) {
		this.fallbackAction = fallbackAction;
	}

}
