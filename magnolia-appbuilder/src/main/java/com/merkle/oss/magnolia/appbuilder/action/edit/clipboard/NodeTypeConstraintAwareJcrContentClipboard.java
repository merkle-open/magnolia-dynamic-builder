package com.merkle.oss.magnolia.appbuilder.action.edit.clipboard;

import info.magnolia.jcr.util.NodeNameHelper;
import info.magnolia.ui.api.ioc.SubAppScoped;
import info.magnolia.ui.contentapp.action.clipboard.JcrClipboard;
import info.magnolia.ui.contentapp.browser.ItemInteractionAvailability;
import info.magnolia.ui.datasource.jcr.JcrDatasource;

import java.lang.invoke.MethodHandles;
import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;

@SubAppScoped
public class NodeTypeConstraintAwareJcrContentClipboard extends JcrClipboard implements JcrContentClipboard {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Inject
	public NodeTypeConstraintAwareJcrContentClipboard(
			final ItemInteractionAvailability<Item> itemInteractionAvailability,
			final NodeNameHelper nodeNameHelper,
			final JcrDatasource jcrDatasource) {
		super(itemInteractionAvailability, nodeNameHelper, jcrDatasource);
	}

	@Override
	protected boolean canPasteInto(final Item source, final Item destination) {
		try {
			if (destination.isNode() && Objects.equals(getWorkspacename(source), getWorkspacename(destination))) {
				if (source.isNode()) {
					return canPasteInto((Node) source, (Node) destination);
				}
				return canPasteInto((Property) source, (Node) destination);
			}
		} catch (RepositoryException e) {
			LOG.error("Failed to check whether the item [" + source + "] can be copied into item [" + destination + "]", e);
		}
		return false;
	}

	private String getWorkspacename(Item source) throws RepositoryException {
		return source.getSession().getWorkspace().getName();
	}

	protected boolean canPasteInto(final Property source, final Node destination) throws RepositoryException {
		if (source.isMultiple()) {
			return destination.getPrimaryNodeType().canSetProperty(source.getName(), source.getValues());
		}
		return destination.getPrimaryNodeType().canSetProperty(source.getName(), source.getValue());
	}

	protected boolean canPasteInto(final Node source, final Node destination) throws RepositoryException {
		for (NodeDefinition allowedChildNodeDefinition : getChildNodeDefinitions(destination)) {
			for (NodeType allowedChildRequiredPrimaryType : allowedChildNodeDefinition.getRequiredPrimaryTypes()) {
				if (Objects.equals(source.getPrimaryNodeType(), allowedChildRequiredPrimaryType)) {
					return true;
				}
			}
		}
		return false;
	}

	private NodeDefinition[] getChildNodeDefinitions(final Node node) throws RepositoryException {
		return Stream.concat(
				Stream.of(node.getPrimaryNodeType()),
				Arrays.stream(node.getMixinNodeTypes())
		).map(NodeType::getChildNodeDefinitions).flatMap(Arrays::stream).toArray(NodeDefinition[]::new);
	}
}
