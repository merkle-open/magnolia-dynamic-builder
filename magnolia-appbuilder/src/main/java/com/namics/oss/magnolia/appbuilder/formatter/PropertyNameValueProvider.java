package com.namics.oss.magnolia.appbuilder.formatter;

import info.magnolia.jcr.util.PropertyUtil;

import java.util.Optional;

import javax.inject.Inject;
import javax.jcr.Node;

public class PropertyNameValueProvider extends AbstractValueProvider {
	private final PropertyNameColumnDefinition definition;

	@Inject
	public PropertyNameValueProvider(final PropertyNameColumnDefinition definition) {
		this.definition = definition;
	}

	@Override
	protected Optional<String> getValue(final Node node) {
		return Optional.ofNullable(PropertyUtil.getString(node, definition.getPropertyName()));
	}
}
