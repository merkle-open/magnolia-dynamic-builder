package com.namics.oss.magnolia.appbuilder.formatter;

import info.magnolia.ui.contentapp.configuration.column.ConfiguredColumnDefinition;

import javax.jcr.Item;

public class PropertyNameColumnDefinition extends ConfiguredColumnDefinition<Item> {
	private final String propertyName;

	public PropertyNameColumnDefinition(final String name, final String propertyName) {
		this.propertyName = propertyName;
		setName(name);
		setValueProvider(PropertyNameValueProvider.class);
	}

	public String getPropertyName() {
		return propertyName;
	}
}