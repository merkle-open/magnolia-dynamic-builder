package com.merkle.oss.magnolia.appbuilder.dropconstraint;

import info.magnolia.ui.contentapp.browser.drop.DropConstraint;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.vaadin.shared.ui.grid.DropLocation;

public class NodeTypeConstraintAwareDropConstraint implements DropConstraint<Item> {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	public boolean isAllowedAt(final Item source, final Item target, final DropLocation location) {
		switch (location) {
			case ON_TOP:
				return allowedAsChild(source, target);
			case BELOW:
			case ABOVE:
				return allowedAsSibling(source, target);
		}
		return false;
	}

	private boolean allowedAsChild(final Item sourceItem, final Item targetItem) {
		return convert(sourceItem).filter(Item::isNode).flatMap(src ->
				convert(targetItem).filter(Item::isNode).map(dst ->
						allowedAsChild(src, dst)
				)
		).orElse(false);
	}

	private boolean allowedAsSibling(final Item sourceItem, final Item targetItem) {
		return convert(sourceItem).filter(Item::isNode).flatMap(src ->
				convert(targetItem).flatMap(this::getParent).filter(Item::isNode).map(dst ->
						allowedAsChild(src, dst)
				)
		).orElse(false);
	}

	protected boolean allowedAsChild(final Node src, final Node dst) {
		try {
			if(Objects.equals(src.getSession().getWorkspace().getName(), dst.getSession().getWorkspace().getName())) {
				for (NodeDefinition allowedChildNodeDefinition : getChildNodeDefinitions(dst)) {
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

	private NodeDefinition[] getChildNodeDefinitions(final Node node) throws RepositoryException {
		return Stream.concat(
				Stream.of(node.getPrimaryNodeType()),
				Arrays.stream(node.getMixinNodeTypes())
		).map(NodeType::getChildNodeDefinitions).flatMap(Arrays::stream).toArray(NodeDefinition[]::new);
	}

	private Optional<Node> getParent(final Node node) {
		try {
			return Optional.ofNullable(node.getParent());
		} catch (RepositoryException e) {
			return Optional.empty();
		}
	}

	private Optional<Node> convert(final Item item) {
		if (item.isNode()) {
			return Optional.ofNullable((Node) item);
		}
		return Optional.empty();
	}
}

