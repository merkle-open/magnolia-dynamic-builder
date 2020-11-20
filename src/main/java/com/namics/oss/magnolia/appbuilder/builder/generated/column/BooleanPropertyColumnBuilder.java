package com.namics.oss.magnolia.appbuilder.builder.generated.column;

import info.magnolia.ui.workbench.column.definition.BooleanPropertyColumnDefinition;
import info.magnolia.ui.workbench.column.definition.ColumnAvailabilityRule;
import info.magnolia.ui.workbench.column.definition.ColumnFormatter;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.913436"
)
public class BooleanPropertyColumnBuilder extends BooleanPropertyColumnDefinition {
	public BooleanPropertyColumnBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public BooleanPropertyColumnBuilder formatterClass(
			Class<? extends ColumnFormatter> formatterClass) {
		this.setFormatterClass(formatterClass);
		return this;
	}

	public BooleanPropertyColumnBuilder displayInChooseDialog(boolean displayInChooseDialog) {
		this.setDisplayInChooseDialog(displayInChooseDialog);
		return this;
	}

	public BooleanPropertyColumnBuilder width(int width) {
		this.setWidth(width);
		return this;
	}

	public BooleanPropertyColumnBuilder editable(boolean editable) {
		this.setEditable(editable);
		return this;
	}

	public BooleanPropertyColumnBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public BooleanPropertyColumnBuilder sortable(boolean sortable) {
		this.setSortable(sortable);
		return this;
	}

	public BooleanPropertyColumnBuilder expandRatio(float expandRatio) {
		this.setExpandRatio(expandRatio);
		return this;
	}

	public BooleanPropertyColumnBuilder searchable(boolean searchable) {
		this.setSearchable(searchable);
		return this;
	}

	public BooleanPropertyColumnBuilder enabled(boolean enabled) {
		this.setEnabled(enabled);
		return this;
	}

	public BooleanPropertyColumnBuilder propertyName(String propertyName) {
		this.setPropertyName(propertyName);
		return this;
	}

	public BooleanPropertyColumnBuilder ruleClass(Class<? extends ColumnAvailabilityRule> ruleClass) {
		this.setRuleClass(ruleClass);
		return this;
	}

	public BooleanPropertyColumnBuilder displayMode(
			BooleanPropertyColumnDefinition.DisplayMode displayMode) {
		this.setDisplayMode(displayMode);
		return this;
	}

	public BooleanPropertyColumnBuilder falseIcon(String falseIcon) {
		this.setFalseIcon(falseIcon);
		return this;
	}

	public BooleanPropertyColumnBuilder trueIcon(String trueIcon) {
		this.setTrueIcon(trueIcon);
		return this;
	}

	public BooleanPropertyColumnBuilder falseLabel(String falseLabel) {
		this.setFalseLabel(falseLabel);
		return this;
	}

	public BooleanPropertyColumnBuilder trueLabel(String trueLabel) {
		this.setTrueLabel(trueLabel);
		return this;
	}
}
