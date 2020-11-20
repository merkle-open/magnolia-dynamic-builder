package com.namics.oss.magnolia.appbuilder.builder.generated.presenter;

import info.magnolia.ui.workbench.ContentPresenter;
import info.magnolia.ui.workbench.column.definition.ColumnDefinition;
import info.magnolia.ui.workbench.search.SearchPresenterDefinition;
import java.lang.Class;
import java.lang.String;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.253411"
)
public class SearchPresenterBuilder extends SearchPresenterDefinition {
	public SearchPresenterBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public SearchPresenterBuilder implementationClass(
			Class<? extends ContentPresenter> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public SearchPresenterBuilder viewType(String viewType) {
		this.setViewType(viewType);
		return this;
	}

	public SearchPresenterBuilder active(boolean active) {
		this.setActive(active);
		return this;
	}

	public SearchPresenterBuilder column(ColumnDefinition column) {
		this.addColumn(column);
		return this;
	}

	public SearchPresenterBuilder columns(List<ColumnDefinition> columns) {
		this.setColumns(columns);
		return this;
	}

	public SearchPresenterBuilder columns(ColumnDefinition... columns) {
		this.setColumns(Arrays.asList(columns));
		return this;
	}
}
