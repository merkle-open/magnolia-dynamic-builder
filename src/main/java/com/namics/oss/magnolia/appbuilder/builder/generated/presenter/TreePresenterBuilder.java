package com.namics.oss.magnolia.appbuilder.builder.generated.presenter;

import info.magnolia.ui.workbench.ContentPresenter;
import info.magnolia.ui.workbench.column.definition.ColumnDefinition;
import info.magnolia.ui.workbench.tree.TreePresenterDefinition;
import java.lang.Class;
import java.lang.String;
import java.util.Arrays;
import java.util.List;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.240186"
)
public class TreePresenterBuilder extends TreePresenterDefinition {
	public TreePresenterBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}

	public TreePresenterBuilder implementationClass(
			Class<? extends ContentPresenter> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}

	public TreePresenterBuilder viewType(String viewType) {
		this.setViewType(viewType);
		return this;
	}

	public TreePresenterBuilder active(boolean active) {
		this.setActive(active);
		return this;
	}

	public TreePresenterBuilder column(ColumnDefinition column) {
		this.addColumn(column);
		return this;
	}

	public TreePresenterBuilder columns(List<ColumnDefinition> columns) {
		this.setColumns(columns);
		return this;
	}

	public TreePresenterBuilder sortable(boolean sortable) {
		this.setSortable(sortable);
		return this;
	}

	public TreePresenterBuilder columns(ColumnDefinition... columns) {
		this.setColumns(Arrays.asList(columns));
		return this;
	}
}
