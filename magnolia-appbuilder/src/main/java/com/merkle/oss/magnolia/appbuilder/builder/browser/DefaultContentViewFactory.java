package com.merkle.oss.magnolia.appbuilder.builder.browser;

import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.contentapp.configuration.ContentViewDefinition;
import info.magnolia.ui.contentapp.configuration.column.ColumnDefinition;

import java.util.List;
import java.util.function.BiFunction;

import javax.jcr.Item;

import com.merkle.oss.magnolia.definition.builder.contentapp.contentview.ListViewDefinitionBuilder;
import com.merkle.oss.magnolia.definition.builder.contentapp.contentview.TreeViewDefinitionBuilder;

public class DefaultContentViewFactory implements BiFunction<DropConstraintDefinition, List<ColumnDefinition<Item>>, List<ContentViewDefinition<Item>>> {
    private final boolean multiSelect;

    public DefaultContentViewFactory(final boolean multiSelect) {
        this.multiSelect = multiSelect;
    }

    @Override
    public List<ContentViewDefinition<Item>> apply(final DropConstraintDefinition dropConstraint, final List<ColumnDefinition<Item>> columnDefinitions) {
        return List.of(
                new TreeViewDefinitionBuilder<Item>()
                        .columns(columnDefinitions)
                        .dropConstraint(dropConstraint)
                        .multiSelect(multiSelect)
                        .build(),
                new ListViewDefinitionBuilder<Item>()
                        .columns(columnDefinitions)
                        .dropConstraint(dropConstraint)
                        .multiSelect(multiSelect)
                        .build()
        );
    }
}
