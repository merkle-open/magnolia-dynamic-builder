package com.namics.oss.magnolia.appbuilder.builder;

import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.contentapp.configuration.ContentViewDefinition;
import info.magnolia.ui.contentapp.configuration.column.ColumnDefinition;

import java.util.List;
import java.util.function.BiFunction;

import com.merkle.oss.magnolia.definition.builder.contentapp.contentview.ListViewDefinitionBuilder;
import com.merkle.oss.magnolia.definition.builder.contentapp.contentview.TreeViewDefinitionBuilder;

public class DefaultContentViewFactory<T> implements BiFunction<DropConstraintDefinition, List<ColumnDefinition<T>>, List<ContentViewDefinition<T>>> {
    private final boolean multiSelect;

    public DefaultContentViewFactory(final boolean multiSelect) {
        this.multiSelect = multiSelect;
    }

    @Override
    public List<ContentViewDefinition<T>> apply(final DropConstraintDefinition dropConstraint, final List<ColumnDefinition<T>> columnDefinitions) {
        return List.of(
                new TreeViewDefinitionBuilder<T>()
                        .columns(columnDefinitions)
                        .dropConstraint(dropConstraint)
                        .multiSelect(multiSelect)
                        .build(),
                new ListViewDefinitionBuilder<T>()
                        .columns(columnDefinitions)
                        .dropConstraint(dropConstraint)
                        .multiSelect(multiSelect)
                        .build()
        );
    }
}
