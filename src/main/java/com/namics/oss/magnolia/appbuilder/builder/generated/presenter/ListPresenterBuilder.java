package com.namics.oss.magnolia.appbuilder.builder.generated.presenter;

import info.magnolia.ui.workbench.ContentPresenter;
import info.magnolia.ui.workbench.column.definition.ColumnDefinition;
import info.magnolia.ui.workbench.list.ListPresenterDefinition;
import java.lang.Class;
import java.lang.String;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.245195"
)
public class ListPresenterBuilder extends ListPresenterDefinition {
	public ListPresenterBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public ListPresenterBuilder implementationClass(
			Class<? extends ContentPresenter> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public ListPresenterBuilder viewType(String viewType) {
		this.setViewType(viewType);
		return this;
	}

	public ListPresenterBuilder active(boolean active) {
		this.setActive(active);
		return this;
	}

	public ListPresenterBuilder column(ColumnDefinition column) {
		this.addColumn(column);
		return this;
	}

	public ListPresenterBuilder columns(List<ColumnDefinition> columns) {
		this.setColumns(columns);
		return this;
	}

	public ListPresenterBuilder columns(ColumnDefinition... columns) {
		this.setColumns(Arrays.asList(columns));
		return this;
	}
}
