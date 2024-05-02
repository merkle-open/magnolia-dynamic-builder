package com.namics.oss.magnolia.appbuilder.builder;

import info.magnolia.ui.contentapp.column.jcr.JcrTitleColumnDefinition;
import info.magnolia.ui.contentapp.configuration.column.ColumnDefinition;

import java.util.Map;
import java.util.function.Consumer;

import com.merkle.oss.magnolia.definition.builder.contentapp.column.JcrStatusColumnDefinitionBuilder;
import com.merkle.oss.magnolia.definition.builder.contentapp.column.JcrTitleColumnDefinitionBuilder;
import com.merkle.oss.magnolia.definition.custom.contentapp.column.modificationdate.ModificationDateColumnDefinitionBuilder;

/**
 * @deprecated use definitionBuilders
 */
@Deprecated
public class ColumnDefinitionBuilder<T> extends com.merkle.oss.magnolia.definition.builder.contentapp.column.ColumnDefinitionBuilder<T> {

	public static <T> ColumnDefinition<T> title(final Map<String, String> nodeTypeToIcon) {
		return title(nodeTypeToIcon, operator -> {});
	}
	public static <T> ColumnDefinition<T> title(final Map<String, String> nodeTypeToIcon, final Consumer<JcrTitleColumnDefinition> operator) {
		final JcrTitleColumnDefinition title = new JcrTitleColumnDefinitionBuilder().nodeTypeToIcon(nodeTypeToIcon).build();
		operator.accept(title);
		return (ColumnDefinition<T>)title;
	}

	public static <T> ColumnDefinition<T> modification() {
		return (ColumnDefinition<T>)new ModificationDateColumnDefinitionBuilder().build();
	}

	public static <T> ColumnDefinition<T> status() {
		return (ColumnDefinition<T>) new JcrStatusColumnDefinitionBuilder().build();
	}
}
