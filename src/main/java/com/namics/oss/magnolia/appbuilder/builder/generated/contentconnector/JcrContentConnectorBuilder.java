package com.namics.oss.magnolia.appbuilder.builder.generated.contentconnector;

import info.magnolia.ui.vaadin.integration.contentconnector.ConfiguredJcrContentConnectorDefinition;
import info.magnolia.ui.vaadin.integration.contentconnector.ContentConnector;
import info.magnolia.ui.vaadin.integration.contentconnector.NodeTypeDefinition;
import java.lang.Class;
import java.lang.String;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.940691"
)
public class JcrContentConnectorBuilder extends ConfiguredJcrContentConnectorDefinition {
	public JcrContentConnectorBuilder implementationClass(
			Class<? extends ContentConnector> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public JcrContentConnectorBuilder nodeTypes(List<NodeTypeDefinition> nodeTypes) {
		this.setNodeTypes(nodeTypes);
		return this;
	}

	public JcrContentConnectorBuilder nodeType(NodeTypeDefinition nodeType) {
		this.addNodeType(nodeType);
		return this;
	}

	public JcrContentConnectorBuilder workspace(String workspace) {
		this.setWorkspace(workspace);
		return this;
	}

	public JcrContentConnectorBuilder rootPath(String rootPath) {
		this.setRootPath(rootPath);
		return this;
	}

	public JcrContentConnectorBuilder includeProperties(boolean includeProperties) {
		this.setIncludeProperties(includeProperties);
		return this;
	}

	public JcrContentConnectorBuilder includeSystemNodes(boolean includeSystemNodes) {
		this.setIncludeSystemNodes(includeSystemNodes);
		return this;
	}

	public JcrContentConnectorBuilder defaultOrder(String defaultOrder) {
		this.setDefaultOrder(defaultOrder);
		return this;
	}

	public JcrContentConnectorBuilder nodeTypes(NodeTypeDefinition... nodeTypes) {
		this.setNodeTypes(Arrays.asList(nodeTypes));
		return this;
	}
}
