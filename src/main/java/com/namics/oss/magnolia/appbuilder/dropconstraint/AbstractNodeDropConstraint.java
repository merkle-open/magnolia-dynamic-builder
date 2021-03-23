package com.namics.oss.magnolia.appbuilder.dropconstraint;


import com.namics.oss.magnolia.powernode.PowerNode;
import com.namics.oss.magnolia.powernode.PowerNodeService;
import info.magnolia.ui.vaadin.integration.jcr.JcrItemAdapter;
import info.magnolia.ui.workbench.tree.drop.AlwaysTrueDropConstraint;
import info.magnolia.ui.workbench.tree.drop.DropConstraint;

import javax.jcr.Node;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;

public abstract class AbstractNodeDropConstraint extends AlwaysTrueDropConstraint implements DropConstraint {
	private static final String ROOT_NODE_TYPE = "rep:root";

	private final PowerNodeService powerNodeService;
	private final String folderNodeType;
	private final Set<String> fileNodeTypes;
	private final boolean nestingAllowed;
	private final boolean fileNodesAllowedInRoot;

	protected AbstractNodeDropConstraint(
			final PowerNodeService powerNodeService,
			final String folderNodeType,
			final Set<String> fileNodeTypes) {
		this(
				powerNodeService,
				folderNodeType,
				fileNodeTypes,
				false,
				false
		);
	}

	protected AbstractNodeDropConstraint(
			final PowerNodeService powerNodeService,
			final String folderNodeType,
			final Set<String> fileNodeTypes,
			final boolean nestingAllowed,
			final boolean fileNodesAllowedInRoot) {
		this.folderNodeType = folderNodeType;
		this.fileNodeTypes = fileNodeTypes;
		this.nestingAllowed = nestingAllowed;
		this.powerNodeService = powerNodeService;
		this.fileNodesAllowedInRoot = fileNodesAllowedInRoot;
	}

	@Override
	public boolean allowedAsChild(final com.vaadin.v7.data.Item sourceItem, final com.vaadin.v7.data.Item targetItem) {
		if (!isDirectChild(sourceItem, targetItem) && !equals(sourceItem, targetItem)) {
			if (isNodeType(sourceItem, fileNodeTypes.toArray(String[]::new))) {
				return isNodeType(targetItem, folderNodeType) || (fileNodesAllowedInRoot && isNodeType(targetItem, ROOT_NODE_TYPE));
			}
			if (isNodeType(sourceItem, folderNodeType)) {
				return isNodeType(targetItem, ROOT_NODE_TYPE) || (nestingAllowed && isNodeType(targetItem, folderNodeType));
			}
		}
		return false;
	}

	@Override
	public boolean allowedBefore(final com.vaadin.v7.data.Item sourceItem, final com.vaadin.v7.data.Item targetItem) {
		if (isNodeType(sourceItem, fileNodeTypes.toArray(String[]::new))) {
			return isParentNodeType(targetItem, folderNodeType) || (fileNodesAllowedInRoot && isParentNodeType(targetItem, ROOT_NODE_TYPE));
		}
		if (isNodeType(sourceItem, folderNodeType)) {
			return isParentNodeType(targetItem, ROOT_NODE_TYPE) || (nestingAllowed && isParentNodeType(targetItem, folderNodeType));
		}
		return false;
	}

	@Override
	public boolean allowedAfter(final com.vaadin.v7.data.Item sourceItem, final com.vaadin.v7.data.Item targetItem) {
		return allowedBefore(sourceItem, targetItem);
	}

	@Override
	public boolean allowedToMove(final com.vaadin.v7.data.Item sourceItem) {
		return isNodeType(sourceItem, folderNodeType) || isNodeType(sourceItem, fileNodeTypes.toArray(String[]::new));
	}

	private boolean equals(final com.vaadin.v7.data.Item item1, final com.vaadin.v7.data.Item item2) {
		return convert(item1)
				.flatMap(node1 ->
						convert(item2).map(parentNode -> equals(node1, parentNode))
				)
				.orElse(false);
	}

	private boolean isDirectChild(final com.vaadin.v7.data.Item childItem, final com.vaadin.v7.data.Item parentItem) {
		return convert(childItem)
				.map(PowerNode::getParent)
				.flatMap(childsParentNode ->
						convert(parentItem).map(parentNode -> equals(childsParentNode, parentNode))
				)
				.orElse(false);
	}

	private boolean equals(final PowerNode node, final PowerNode other) {
		return Objects.equals(node.getIdentifier(), other.getIdentifier());
	}

	private boolean isParentNodeType(final com.vaadin.v7.data.Item item, final String nodeType) {
		return convert(item)
				.map(PowerNode::getParent)
				.map(node -> node.isNodeType(nodeType))
				.orElse(false);
	}

	private boolean isNodeType(final com.vaadin.v7.data.Item item, final String... nodeTypes) {
		return convert(item)
				.map(node -> Arrays.stream(nodeTypes).anyMatch(node::isNodeType))
				.orElse(false);
	}

	private Optional<PowerNode> convert(com.vaadin.v7.data.Item item) {
		if (item instanceof JcrItemAdapter && ((JcrItemAdapter) item).isNode()) {
			return Optional.of(powerNodeService.convertToPowerNode((Node) ((JcrItemAdapter) item).getJcrItem()));
		}
		return Optional.empty();
	}
}
