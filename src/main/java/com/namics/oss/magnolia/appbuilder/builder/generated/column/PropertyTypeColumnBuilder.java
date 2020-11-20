package com.namics.oss.magnolia.appbuilder.builder.generated.column;

import info.magnolia.ui.workbench.column.definition.ColumnAvailabilityRule;
import info.magnolia.ui.workbench.column.definition.ColumnFormatter;
import info.magnolia.ui.workbench.column.definition.PropertyTypeColumnDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.931088"
)
public class PropertyTypeColumnBuilder extends PropertyTypeColumnDefinition {
	public PropertyTypeColumnBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public PropertyTypeColumnBuilder formatterClass(Class<? extends ColumnFormatter> formatterClass) {
		this.setFormatterClass(formatterClass);
		return this;
	}

	public PropertyTypeColumnBuilder displayInChooseDialog(boolean displayInChooseDialog) {
		this.setDisplayInChooseDialog(displayInChooseDialog);
		return this;
	}

	public PropertyTypeColumnBuilder width(int width) {
		this.setWidth(width);
		return this;
	}

	public PropertyTypeColumnBuilder editable(boolean editable) {
		this.setEditable(editable);
		return this;
	}

	public PropertyTypeColumnBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public PropertyTypeColumnBuilder sortable(boolean sortable) {
		this.setSortable(sortable);
		return this;
	}

	public PropertyTypeColumnBuilder expandRatio(float expandRatio) {
		this.setExpandRatio(expandRatio);
		return this;
	}

	public PropertyTypeColumnBuilder searchable(boolean searchable) {
		this.setSearchable(searchable);
		return this;
	}

	public PropertyTypeColumnBuilder enabled(boolean enabled) {
		this.setEnabled(enabled);
		return this;
	}

	public PropertyTypeColumnBuilder propertyName(String propertyName) {
		this.setPropertyName(propertyName);
		return this;
	}

	public PropertyTypeColumnBuilder ruleClass(Class<? extends ColumnAvailabilityRule> ruleClass) {
		this.setRuleClass(ruleClass);
		return this;
	}
}
