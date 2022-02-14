package com.namics.oss.magnolia.appbuilder.dropconstraint;

import info.magnolia.ui.vaadin.integration.jcr.JcrItemAdapter;
import info.magnolia.ui.workbench.tree.drop.AlwaysTrueDropConstraint;
import info.magnolia.ui.workbench.tree.drop.DropConstraint;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import java.lang.invoke.MethodHandles;
import java.util.Objects;
import java.util.Optional;

public class NodeTypeConstraintAwareDropConstraint extends AlwaysTrueDropConstraint implements DropConstraint {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	public boolean allowedAsChild(final com.vaadin.v7.data.Item sourceItem, final com.vaadin.v7.data.Item targetItem) {
		return convert(sourceItem).filter(Item::isNode).flatMap(src ->
				convert(targetItem).filter(Item::isNode).map(dst ->
						allowedAsChild(src, dst)
				)
		).orElse(false);
	}

	@Override
	public boolean allowedBefore(final com.vaadin.v7.data.Item sourceItem, final com.vaadin.v7.data.Item targetItem) {
		return convert(sourceItem).filter(Item::isNode).flatMap(src ->
				convert(targetItem).flatMap(this::getParent).filter(Item::isNode).map(dst ->
						allowedAsChild(src, dst)
				)
		).orElse(false);
	}

	@Override
	public boolean allowedAfter(final com.vaadin.v7.data.Item sourceItem, final com.vaadin.v7.data.Item targetItem) {
		return allowedBefore(sourceItem, targetItem);
	}

	@Override
	public boolean allowedToMove(final com.vaadin.v7.data.Item sourceItem) {
		return true;
	}

	private boolean allowedAsChild(final Node src, final Node dst) {
		try {
			if(Objects.equals(src.getSession().getWorkspace().getName(), dst.getSession().getWorkspace().getName())) {
				for (NodeDefinition allowedChildNodeDefinition : dst.getPrimaryNodeType().getChildNodeDefinitions()) {
					for (NodeType allowedChildRequiredPrimaryType : allowedChildNodeDefinition.getRequiredPrimaryTypes()) {
						if (Objects.equals(src.getPrimaryNodeType(), allowedChildRequiredPrimaryType)) {
							return true;
						}
					}
				}
			}
		} catch (RepositoryException e) {
			LOG.error("Failed to check whether [{}] is allowed as child of [{}] due to: {}", src, dst, e);
		}
		return false;
	}

	private Optional<Node> getParent(final Node node) {
		try {
			return Optional.ofNullable(node.getParent());
		} catch (RepositoryException e) {
			return Optional.empty();
		}
	}

	private Optional<Node> convert(com.vaadin.v7.data.Item item) {
		if (item instanceof JcrItemAdapter && ((JcrItemAdapter) item).isNode()) {
			return Optional.ofNullable((Node) ((JcrItemAdapter) item).getJcrItem());
		}
		return Optional.empty();
	}
}
