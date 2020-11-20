package com.namics.oss.magnolia.appbuilder.builder.helper.action;

import com.vaadin.v7.data.Item;
import info.magnolia.ui.api.action.AbstractAction;
import info.magnolia.ui.api.action.ActionExecutionException;
import info.magnolia.ui.api.action.ActionExecutor;
import info.magnolia.ui.vaadin.integration.jcr.JcrNodeAdapter;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.lang.invoke.MethodHandles;
import java.util.Collections;
import java.util.List;

public class NodeTypeToActionDelegatingAction<D extends NodeTypeToActionDelegatingActionDefinition> extends AbstractAction<D> {

	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final List<? extends Item> items;
	private final ActionExecutor actionExecutor;


	public NodeTypeToActionDelegatingAction(D definition, Item item, ActionExecutor actionExecutor) {
		super(definition);
		this.items = Collections.singletonList(item);
		this.actionExecutor = actionExecutor;
	}

	@Inject
	public NodeTypeToActionDelegatingAction(D definition, List<? extends Item> items, ActionExecutor actionExecutor) {
		super(definition);
		this.items = items;
		this.actionExecutor = actionExecutor;
	}

	@Override
	public void execute() throws ActionExecutionException {
		try {
			if (items.size() == 1) {
				Item currentItem = items.get(0);
				Node node = ((JcrNodeAdapter) currentItem).getJcrItem();
				String nodeType = node.getPrimaryNodeType().getName();
				String fallbackAction = getDefinition().getFallbackAction();
				String action = getDefinition().getNodeTypeActionMapping().getOrDefault(nodeType, fallbackAction);
				if (StringUtils.isNotBlank(action)) {
					actionExecutor.execute(action, items.get(0));
				} else {
					LOG.warn("No action specified for NodeType '{}', also no fallback action", nodeType);
				}
			} else {
				LOG.debug("No default action for multiple items");
			}
		} catch (RepositoryException e) {
			throw new ActionExecutionException(e);
		}
	}

}
