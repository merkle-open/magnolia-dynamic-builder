package com.merkle.oss.magnolia.appbuilder.builder.page;

import info.magnolia.pages.app.detail.PageDetailDescriptor;
import info.magnolia.ui.UIComponent;
import info.magnolia.ui.ViewDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.datasource.jcr.JcrDatasourceDefinition;
import info.magnolia.ui.editor.JcrNodeFromLocationResolutionDefinition;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.AppActionGroupDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.ActionFactory;
import com.merkle.oss.magnolia.appbuilder.builder.ActionbarFactory;
import com.merkle.oss.magnolia.appbuilder.contextmenu.AppContextMenuDefinition;
import com.merkle.oss.magnolia.appbuilder.contextmenu.ContentAppContextMenuDefinition;
import com.merkle.oss.magnolia.appbuilder.dropconstraint.NodeTypeConstraintAwareDropConstraintDefinition;
import com.merkle.oss.magnolia.definition.builder.datasource.JcrDatasourceDefinitionBuilder;

import jakarta.annotation.Nullable;

public class PageDetailAppBuilder {
    private final List<AppContextMenuDefinition> contentContextMenuDefinitions = new ArrayList<>();
    @Nullable
    private Function<Boolean, List<ViewDefinition<? extends UIComponent>>> extensionViewFactory;

    @Nullable
    private DropConstraintDefinition dropConstraint;

    private boolean multiTree = false;

    private final ActionbarFactory actionbarFactory;
    private final ActionFactory actionFactory;

    public PageDetailAppBuilder() {
        this(new ActionbarFactory(), new ActionFactory());
    }
    public PageDetailAppBuilder(final ActionbarFactory actionbarFactory, final ActionFactory actionFactory) {
        this.actionbarFactory = actionbarFactory;
        this.actionFactory = actionFactory;
    }

    public PageDetailAppBuilder multiTree() {
        return multiTree(true);
    }
    public PageDetailAppBuilder multiTree(final boolean multiTree) {
        this.multiTree = multiTree;
        return this;
    }

    public PageDetailAppBuilder nodeActions(final String nodeType, final List<AppActionGroupDefinition> actionGroups) {
        return nodeActions(nodeType, null, actionGroups);
    }
    public PageDetailAppBuilder nodeActions(final String nodeType, final AppActionGroupDefinition... actionGroups) {
        return nodeActions(nodeType, null, actionGroups);
    }
    public PageDetailAppBuilder nodeActions(
            final String nodeType,
            @Nullable final AppActionDefinition doubleClickAction,
            final AppActionGroupDefinition... actionGroups
    ) {
        return nodeActions(nodeType, doubleClickAction, List.of(actionGroups));
    }
    public PageDetailAppBuilder nodeActions(
            final String nodeType,
            @Nullable final AppActionDefinition doubleClickAction,
            final List<AppActionGroupDefinition> actionGroups
    ) {
        return nodeActions(new ContentAppContextMenuDefinition(nodeType, doubleClickAction, actionGroups));
    }
    public PageDetailAppBuilder nodeActions(final List<AppContextMenuDefinition> contextMenus) {
        contextMenus.forEach(this::nodeActions);
        return this;
    }
    public PageDetailAppBuilder nodeActions(final AppContextMenuDefinition contextMenu) {
        contentContextMenuDefinitions.add(contextMenu);
        return this;
    }

    public PageDetailAppBuilder extensionViews(final Function<Boolean, List<ViewDefinition<? extends UIComponent>>> extensionViewFactory) {
        this.extensionViewFactory = extensionViewFactory;
        return this;
    }

    public PageDetailDescriptor build(final String workspace, final String name) {
        final JcrDatasourceDefinition datasourceDefinition = new JcrDatasourceDefinitionBuilder()
                .workspace(workspace)
                .describeByProperty("jcrName") //see info.magnolia.ui.contentapp.JcrItemDescriber
                .build();
        return build(datasourceDefinition, name);
    }
    public PageDetailDescriptor build(final JcrDatasourceDefinition datasourceDefinition, final String name) {
        if (dropConstraint == null) {
            dropConstraint = new NodeTypeConstraintAwareDropConstraintDefinition();
        }
        final PageDetailDescriptor definition = new PageDetailDescriptor();
        definition.setName(name);
        definition.setExtensionViews(Optional.ofNullable(extensionViewFactory).orElseGet(DefaultExtensionViewFactory::new).apply(multiTree));
        definition.setItemProvider(new JcrNodeFromLocationResolutionDefinition());
        definition.setActions(actionFactory.create(contentContextMenuDefinitions, dropConstraint));
        definition.setActionbar(actionbarFactory.create(contentContextMenuDefinitions, dropConstraint));
        definition.setDatasource(datasourceDefinition);
        return definition;
    }
}
