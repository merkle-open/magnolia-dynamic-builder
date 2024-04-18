package com.namics.oss.magnolia.appbuilder.builder;

import info.magnolia.ui.contentapp.column.jcr.JcrStatusColumnDefinition;
import info.magnolia.ui.contentapp.column.jcr.JcrTitleColumnDefinition;
import info.magnolia.ui.contentapp.configuration.column.ColumnDefinition;
import info.magnolia.ui.contentapp.configuration.column.ConfiguredColumnDefinition;
import info.magnolia.ui.field.FieldDefinition;

import java.util.Map;
import java.util.Optional;
import java.util.function.Consumer;

import javax.annotation.Nullable;

import com.vaadin.data.ValueProvider;
import com.vaadin.ui.DescriptionGenerator;
import com.vaadin.ui.renderers.AbstractRenderer;

public class ColumnDefinitionBuilder<T> {
	@Nullable
	private Class<? extends AbstractRenderer> renderer;
	@Nullable
	private Class<T> type;
	@Nullable
	private Class<? extends ValueProvider<T, ?>> valueProvider;
	@Nullable
	private Integer expandRatio;
	@Nullable
	private Double width;
	@Nullable
	private Double minimumWidth;
	@Nullable
	private Double maximumWidth;
	@Nullable
	private Boolean minimumWidthFromContent;
	@Nullable
	private Boolean editable;
	@Nullable
	private FieldDefinition<?> filterComponent;
	@Nullable
	private Class<? extends DescriptionGenerator<T>> descriptionGenerator;
	@Nullable
	private Boolean sortable;

	public ColumnDefinitionBuilder<T> renderer(final Class<? extends AbstractRenderer> renderer) {
		this.renderer = renderer;
		return this;
	}

	public ColumnDefinitionBuilder<T> type(final Class<T> type) {
		this.type = type;
		return this;
	}

	public ColumnDefinitionBuilder<T> valueProvider(final Class<? extends ValueProvider<T, ?>> valueProvider) {
		this.valueProvider = valueProvider;
		return this;
	}

	public ColumnDefinitionBuilder<T> width(final double width) {
		this.width = width;
		return this;
	}

	public ColumnDefinitionBuilder<T> minimumWidth(final double minimumWidth) {
		this.minimumWidth = minimumWidth;
		return this;
	}

	public ColumnDefinitionBuilder<T> maximumWidth(final double maximumWidth) {
		this.maximumWidth = maximumWidth;
		return this;
	}

	public ColumnDefinitionBuilder<T> minimumWidthFromContent(final boolean minimumWidthFromContent) {
		this.minimumWidthFromContent = minimumWidthFromContent;
		return this;
	}

	public ColumnDefinitionBuilder<T> editable(final boolean editable) {
		this.editable = editable;
		return this;
	}

	public ColumnDefinitionBuilder<T> filterComponent(final FieldDefinition<?> filterComponent) {
		this.filterComponent = filterComponent;
		return this;
	}

	public ColumnDefinitionBuilder<T> descriptionGenerator(final Class<? extends DescriptionGenerator<T>> descriptionGenerator) {
		this.descriptionGenerator = descriptionGenerator;
		return this;
	}

	public ColumnDefinitionBuilder<T> sortable(final boolean sortable) {
		this.sortable = sortable;
		return this;
	}

	public ColumnDefinition<T> build(final String name) {
		final ConfiguredColumnDefinition<T> definition = new ConfiguredColumnDefinition<>();
		definition.setName(name);
		Optional.ofNullable(renderer).ifPresent(definition::setRenderer);
		Optional.ofNullable(type).ifPresent(definition::setType);
		Optional.ofNullable(valueProvider).ifPresent(definition::setValueProvider);
		Optional.ofNullable(expandRatio).ifPresent(definition::setExpandRatio);
		Optional.ofNullable(width).ifPresent(definition::setWidth);
		Optional.ofNullable(minimumWidth).ifPresent(definition::setMinimumWidth);
		Optional.ofNullable(maximumWidth).ifPresent(definition::setMaximumWidth);
		Optional.ofNullable(minimumWidthFromContent).ifPresent(definition::setMinimumWidthFromContent);
		Optional.ofNullable(editable).ifPresent(definition::setEditable);
		Optional.ofNullable(filterComponent).ifPresent(definition::setFilterComponent);
		Optional.ofNullable(descriptionGenerator).ifPresent(definition::setDescriptionGenerator);
		Optional.ofNullable(sortable).ifPresent(definition::setSortable);
		return definition;
	}

	public static <T> ColumnDefinition<T> title(final Map<String, String> nodeTypeToIcon) {
		return title(nodeTypeToIcon, operator -> {});
	}
	public static <T> ColumnDefinition<T> title(final Map<String, String> nodeTypeToIcon, final Consumer<JcrTitleColumnDefinition> operator) {
		final JcrTitleColumnDefinition title = new JcrTitleColumnDefinition();
		operator.accept(title);
		title.setNodeTypeToIcon(nodeTypeToIcon);
		return (ColumnDefinition<T>)title;
	}

	public static <T> ColumnDefinition<T> modification() {
		return (ColumnDefinition<T>)new ModificationDateColumnDefinition();
	}

	public static <T> ColumnDefinition<T> status() {
		return (ColumnDefinition<T>) new JcrStatusColumnDefinition();
	}
}
