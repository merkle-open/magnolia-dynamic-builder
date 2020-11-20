package com.namics.oss.magnolia.appbuilder.builder.generated.presenter;

import info.magnolia.ui.workbench.ContentPresenter;
import info.magnolia.ui.workbench.column.definition.ColumnDefinition;
import info.magnolia.ui.workbench.definition.ConfiguredContentPresenterDefinition;
import java.lang.Class;
import java.lang.String;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.257892"
)
public class ContentPresenterBuilder extends ConfiguredContentPresenterDefinition {
	public ContentPresenterBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public ContentPresenterBuilder implementationClass(
			Class<? extends ContentPresenter> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public ContentPresenterBuilder viewType(String viewType) {
		this.setViewType(viewType);
		return this;
	}

	public ContentPresenterBuilder active(boolean active) {
		this.setActive(active);
		return this;
	}

	public ContentPresenterBuilder column(ColumnDefinition column) {
		this.addColumn(column);
		return this;
	}

	public ContentPresenterBuilder columns(List<ColumnDefinition> columns) {
		this.setColumns(columns);
		return this;
	}

	public ContentPresenterBuilder columns(ColumnDefinition... columns) {
		this.setColumns(Arrays.asList(columns));
		return this;
	}
}
