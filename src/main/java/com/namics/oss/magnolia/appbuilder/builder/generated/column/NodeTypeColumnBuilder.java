package com.namics.oss.magnolia.appbuilder.builder.generated.column;

import info.magnolia.ui.workbench.column.definition.ColumnAvailabilityRule;
import info.magnolia.ui.workbench.column.definition.ColumnFormatter;
import info.magnolia.ui.workbench.column.definition.NodeTypeColumnDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.923285"
)
public class NodeTypeColumnBuilder extends NodeTypeColumnDefinition {
	public NodeTypeColumnBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public NodeTypeColumnBuilder formatterClass(Class<? extends ColumnFormatter> formatterClass) {
		this.setFormatterClass(formatterClass);
		return this;
	}

	public NodeTypeColumnBuilder displayInChooseDialog(boolean displayInChooseDialog) {
		this.setDisplayInChooseDialog(displayInChooseDialog);
		return this;
	}

	public NodeTypeColumnBuilder width(int width) {
		this.setWidth(width);
		return this;
	}

	public NodeTypeColumnBuilder editable(boolean editable) {
		this.setEditable(editable);
		return this;
	}

	public NodeTypeColumnBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public NodeTypeColumnBuilder sortable(boolean sortable) {
		this.setSortable(sortable);
		return this;
	}

	public NodeTypeColumnBuilder expandRatio(float expandRatio) {
		this.setExpandRatio(expandRatio);
		return this;
	}

	public NodeTypeColumnBuilder searchable(boolean searchable) {
		this.setSearchable(searchable);
		return this;
	}

	public NodeTypeColumnBuilder enabled(boolean enabled) {
		this.setEnabled(enabled);
		return this;
	}

	public NodeTypeColumnBuilder propertyName(String propertyName) {
		this.setPropertyName(propertyName);
		return this;
	}

	public NodeTypeColumnBuilder ruleClass(Class<? extends ColumnAvailabilityRule> ruleClass) {
		this.setRuleClass(ruleClass);
		return this;
	}
}
