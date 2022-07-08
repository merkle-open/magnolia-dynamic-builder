package com.namics.oss.magnolia.appbuilder.builder;

import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.contentapp.configuration.ContentViewDefinition;
import info.magnolia.ui.contentapp.configuration.ListViewDefinition;
import info.magnolia.ui.contentapp.configuration.TreeViewDefinition;
import info.magnolia.ui.contentapp.configuration.column.ColumnDefinition;

import java.util.List;
import java.util.function.BiFunction;

class DefaultContentViewFactory<T> implements BiFunction<DropConstraintDefinition, List<ColumnDefinition<T>>, List<ContentViewDefinition<T>>> {
	private final DropConstraintDefinition dropConstraint;

	public DefaultContentViewFactory(final DropConstraintDefinition dropConstraint) {
		this.dropConstraint = dropConstraint;
	}

	@Override
	public List<ContentViewDefinition<T>> apply(DropConstraintDefinition dropConstraintDefinition, List<ColumnDefinition<T>> columnDefinitions) {
		final TreeViewDefinition<T> tree = new TreeViewDefinition<>();
		tree.setName("tree");
		tree.setColumns(columnDefinitions);
		tree.setDropConstraint(dropConstraint);

		final ListViewDefinition<T> list = new ListViewDefinition<>();
		list.setName("list");
		list.setColumns(columnDefinitions);
		list.setDropConstraint(dropConstraint);
		return List.of(tree, list);
	}
}
