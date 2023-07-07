package com.namics.oss.magnolia.appbuilder.action.rule;

import info.magnolia.cms.security.AccessManager;
import info.magnolia.context.Context;
import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.availability.rule.AbstractJcrRule;
import info.magnolia.ui.datasource.jcr.JcrDatasource;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.jcr.Item;
import javax.jcr.Node;
import javax.jcr.RepositoryException;
import java.util.Collection;

public class PermissionRequiredRule extends AbstractJcrRule<PermissionRequiredRuleDefinition> {
	private final JcrDatasource datasource;
	private final Provider<Context> contextProvider;

	@Inject
	public PermissionRequiredRule(
			final AvailabilityDefinition availabilityDefinition,
			final PermissionRequiredRuleDefinition ruleDefinition,
			final JcrDatasource datasource,
			final Provider<Context> contextProvider
	) {
		super(availabilityDefinition, ruleDefinition);
		this.datasource = datasource;
		this.contextProvider = contextProvider;
	}

	@Override
	public boolean isAvailable(final Collection<?> items) {
		if (items.isEmpty()) {
			return isAvailableFor(datasource.getRoot());
		}
		return super.isAvailable(items);
	}

	@Override
	protected boolean isAvailableForJcrItem(final Item item) throws RepositoryException {
		final AccessManager ami = contextProvider.get().getAccessManager(item.getSession().getWorkspace().getName());
		final Node node = item.isNode() ? (Node) item : item.getParent();
		return ami.isGranted(node.getPath(), getRuleDefinition().getRequiredPermissions());
	}
}
