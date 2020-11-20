package com.namics.oss.magnolia.appbuilder.builder.generated.column;

import info.magnolia.ui.workbench.column.definition.ColumnAvailabilityRule;
import info.magnolia.ui.workbench.column.definition.ColumnFormatter;
import info.magnolia.ui.workbench.column.definition.StatusColumnDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.934064"
)
public class StatusColumnBuilder extends StatusColumnDefinition {
	public StatusColumnBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public StatusColumnBuilder formatterClass(Class<? extends ColumnFormatter> formatterClass) {
		this.setFormatterClass(formatterClass);
		return this;
	}

	public StatusColumnBuilder displayInChooseDialog(boolean displayInChooseDialog) {
		this.setDisplayInChooseDialog(displayInChooseDialog);
		return this;
	}

	public StatusColumnBuilder width(int width) {
		this.setWidth(width);
		return this;
	}

	public StatusColumnBuilder editable(boolean editable) {
		this.setEditable(editable);
		return this;
	}

	public StatusColumnBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public StatusColumnBuilder sortable(boolean sortable) {
		this.setSortable(sortable);
		return this;
	}

	public StatusColumnBuilder expandRatio(float expandRatio) {
		this.setExpandRatio(expandRatio);
		return this;
	}

	public StatusColumnBuilder searchable(boolean searchable) {
		this.setSearchable(searchable);
		return this;
	}

	public StatusColumnBuilder enabled(boolean enabled) {
		this.setEnabled(enabled);
		return this;
	}

	public StatusColumnBuilder propertyName(String propertyName) {
		this.setPropertyName(propertyName);
		return this;
	}

	public StatusColumnBuilder ruleClass(Class<? extends ColumnAvailabilityRule> ruleClass) {
		this.setRuleClass(ruleClass);
		return this;
	}

	public StatusColumnBuilder activation(boolean activation) {
		this.setActivation(activation);
		return this;
	}

	public StatusColumnBuilder permissions(boolean permissions) {
		this.setPermissions(permissions);
		return this;
	}
}
