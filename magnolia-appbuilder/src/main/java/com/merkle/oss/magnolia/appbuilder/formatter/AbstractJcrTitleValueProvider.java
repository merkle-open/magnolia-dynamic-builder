package com.merkle.oss.magnolia.appbuilder.formatter;

import info.magnolia.ui.contentapp.column.jcr.JcrTitleColumnDefinition;
import info.magnolia.ui.datasource.jcr.JcrDatasourceDefinition;

import java.util.Optional;

import javax.jcr.Item;
import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;

public abstract class AbstractJcrTitleValueProvider extends JcrTitleColumnDefinition.JcrTitleValueProvider {

	protected AbstractJcrTitleValueProvider(
			final JcrTitleColumnDefinition definition,
			final JcrDatasourceDefinition jcrDatasourceDefinition
	) {
		super(definition, jcrDatasourceDefinition);
	}

	@Override
	protected String getTitle(final Item item) {
		return Optional
				.of(item)
				.filter(Item::isNode)
				.map(Node.class::cast)
				.flatMap(this::getTitle)
				.orElse(StringUtils.EMPTY);
	}

	protected abstract Optional<String> getTitle(final Node node);
}
