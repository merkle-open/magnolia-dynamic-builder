package com.namics.oss.magnolia.appbuilder.dropconstraint;

import info.magnolia.ui.api.ioc.SubAppScoped;
import info.magnolia.ui.contentapp.browser.JcrContentClipboard;
import info.magnolia.ui.vaadin.integration.jcr.JcrItemId;
import info.magnolia.ui.vaadin.integration.jcr.JcrItemUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nullable;
import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.Property;
import javax.jcr.RepositoryException;
import javax.jcr.nodetype.NodeDefinition;
import javax.jcr.nodetype.NodeType;
import java.lang.invoke.MethodHandles;
import java.util.Objects;

@SubAppScoped
public class NodeTypeConstraintAwareJcrContentClipboard extends JcrContentClipboard {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Override
	protected boolean canPasteInto(final JcrItemId source, final JcrItemId destination) {
		try {
			@Nullable final Item src = JcrItemUtil.getJcrItem(source);
			@Nullable final Item dst = JcrItemUtil.getJcrItem(destination);
			if (src != null && dst != null && dst.isNode() && Objects.equals(source.getWorkspace(), destination.getWorkspace())) {
				if (src.isNode()) {
					return canPasteInto((Node) src, (Node) dst);
				}
				return canPasteInto((Property) src, (Node) dst);
			}
		} catch (RepositoryException e) {
			LOG.error("Failed to check whether the item with UUID [{}] from workspace [{}] can be copied into the item with UUID [{}] from workspace [{}] due to: {}", source.getUuid(), source.getWorkspace(), destination.getUuid(), destination.getWorkspace(), e.getMessage(), e);
		}
		return false;
	}

	private boolean canPasteInto(final Property source, final Node destination) throws RepositoryException {
		if (source.isMultiple()) {
			return destination.getPrimaryNodeType().canSetProperty(source.getName(), source.getValues());
		}
		return destination.getPrimaryNodeType().canSetProperty(source.getName(), source.getValue());
	}

	private boolean canPasteInto(final Node source, final Node destination) throws RepositoryException {
		for (NodeDefinition allowedChildNodeDefinition : destination.getPrimaryNodeType().getChildNodeDefinitions()) {
			for (NodeType allowedChildRequiredPrimaryType : allowedChildNodeDefinition.getRequiredPrimaryTypes()) {
				if (Objects.equals(source.getPrimaryNodeType(), allowedChildRequiredPrimaryType)) {
					return true;
				}
			}
		}
		return false;
	}
}
