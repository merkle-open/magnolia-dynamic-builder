package com.namics.oss.magnolia.appbuilder.builder.generated.column;

import info.magnolia.ui.workbench.column.definition.ColumnAvailabilityRule;
import info.magnolia.ui.workbench.column.definition.ColumnFormatter;
import info.magnolia.ui.workbench.column.definition.MetaDataColumnDefinition;
import java.lang.Class;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.918263"
)
public class MetaDataColumnBuilder extends MetaDataColumnDefinition {
	public MetaDataColumnBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public MetaDataColumnBuilder formatterClass(Class<? extends ColumnFormatter> formatterClass) {
		this.setFormatterClass(formatterClass);
		return this;
	}

	public MetaDataColumnBuilder displayInChooseDialog(boolean displayInChooseDialog) {
		this.setDisplayInChooseDialog(displayInChooseDialog);
		return this;
	}

	public MetaDataColumnBuilder width(int width) {
		this.setWidth(width);
		return this;
	}

	public MetaDataColumnBuilder editable(boolean editable) {
		this.setEditable(editable);
		return this;
	}

	public MetaDataColumnBuilder label(String label) {
		this.setLabel(label);
		return this;
	}

	public MetaDataColumnBuilder sortable(boolean sortable) {
		this.setSortable(sortable);
		return this;
	}

	public MetaDataColumnBuilder expandRatio(float expandRatio) {
		this.setExpandRatio(expandRatio);
		return this;
	}

	public MetaDataColumnBuilder searchable(boolean searchable) {
		this.setSearchable(searchable);
		return this;
	}

	public MetaDataColumnBuilder enabled(boolean enabled) {
		this.setEnabled(enabled);
		return this;
	}

	public MetaDataColumnBuilder propertyName(String propertyName) {
		this.setPropertyName(propertyName);
		return this;
	}

	public MetaDataColumnBuilder ruleClass(Class<? extends ColumnAvailabilityRule> ruleClass) {
		this.setRuleClass(ruleClass);
		return this;
	}

	public MetaDataColumnBuilder dateFormat(String dateFormat) {
		this.setDateFormat(dateFormat);
		return this;
	}

	public MetaDataColumnBuilder timeFormat(String timeFormat) {
		this.setTimeFormat(timeFormat);
		return this;
	}
}
