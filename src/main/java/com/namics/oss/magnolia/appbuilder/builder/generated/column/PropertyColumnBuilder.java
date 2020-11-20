package com.namics.oss.magnolia.appbuilder.builder.generated.column;

import info.magnolia.ui.workbench.column.definition.ColumnAvailabilityRule;
import info.magnolia.ui.workbench.column.definition.ColumnFormatter;
import info.magnolia.ui.workbench.column.definition.PropertyColumnDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.927834"
)
public class PropertyColumnBuilder extends PropertyColumnDefinition {
	public PropertyColumnBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public PropertyColumnBuilder formatterClass(Class<? extends ColumnFormatter> formatterClass) {
		this.setFormatterClass(formatterClass);
		return this;
	}

	public PropertyColumnBuilder displayInChooseDialog(boolean displayInChooseDialog) {
		this.setDisplayInChooseDialog(displayInChooseDialog);
		return this;
	}

	public PropertyColumnBuilder width(int width) {
		this.setWidth(width);
		return this;
	}

	public PropertyColumnBuilder editable(boolean editable) {
		this.setEditable(editable);
		return this;
	}

	public PropertyColumnBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public PropertyColumnBuilder sortable(boolean sortable) {
		this.setSortable(sortable);
		return this;
	}

	public PropertyColumnBuilder expandRatio(float expandRatio) {
		this.setExpandRatio(expandRatio);
		return this;
	}

	public PropertyColumnBuilder searchable(boolean searchable) {
		this.setSearchable(searchable);
		return this;
	}

	public PropertyColumnBuilder enabled(boolean enabled) {
		this.setEnabled(enabled);
		return this;
	}

	public PropertyColumnBuilder propertyName(String propertyName) {
		this.setPropertyName(propertyName);
		return this;
	}

	public PropertyColumnBuilder ruleClass(Class<? extends ColumnAvailabilityRule> ruleClass) {
		this.setRuleClass(ruleClass);
		return this;
	}
}
