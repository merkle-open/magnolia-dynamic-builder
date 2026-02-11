package com.merkle.oss.magnolia.appbuilder.builder.page;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.AppActionGroupDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.AbstractAppBuilder;
import com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.DeleteElementAppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.area.EditAreaAppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.component.*;
import com.merkle.oss.magnolia.appbuilder.builder.page.detail.action.page.EditPageAppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.contextmenu.AppContextMenuDefinition;
import com.merkle.oss.magnolia.appbuilder.contextmenu.ContentAppContextMenuDefinition;
import com.merkle.oss.magnolia.appbuilder.contextmenu.PageAppContextMenuDefinition;
import com.merkle.oss.magnolia.appbuilder.dropconstraint.NodeTypeConstraintAwareDropConstraintDefinition;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.pages.app.detail.PageDetailDescriptor;
import info.magnolia.pages.app.detail.extension.LanguageSelectorViewDefinition;
import info.magnolia.pages.app.detail.extension.PlatformSelectorViewDefinition;
import info.magnolia.pages.app.detail.extension.PublishingStatusViewDefinition;

import info.magnolia.ui.UIComponent;
import info.magnolia.ui.ViewDefinition;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.datasource.jcr.JcrDatasourceDefinition;
import info.magnolia.ui.editor.JcrNodeFromLocationResolutionDefinition;
import jakarta.annotation.Nullable;

import java.util.*;


public class PageDetailAppBuilder extends AbstractAppBuilder {
    public static final String NAME = "detail";
    private final List<AppContextMenuDefinition> contentContextMenuDefinitions = new ArrayList<>();
    private final List<ViewDefinition<? extends UIComponent>> extensionViews = new ArrayList<>();

    @Nullable
    private DropConstraintDefinition dropConstraint;

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
        contentContextMenuDefinitions.add(new PageAppContextMenuDefinition(nodeType, doubleClickAction, actionGroups));
        return this;
    }

    public PageDetailAppBuilder defaultActions() {
        return nodeActions(
                NodeTypes.Page.NAME,
                new AppActionGroupDefinition("edit", new EditPageAppActionDefinition())
        )
                .nodeActions(
                        NodeTypes.Component.NAME,
                        new AppActionGroupDefinition("edit",
                                new EditComponentAppActionDefinition(),
                                new DuplicateComponentAppActionDefinition(),
                                new StartMoveComponentAppActionDefinition(),
                                new StopMoveComponentAppActionDefinition(),
                                new SortComponentAppActionDefinition()
                        ),
                        new AppActionGroupDefinition("delete",
                                new DeleteComponentAppActionDefinition(),
                                new DeleteElementAppActionDefinition()
                        )
                )
                .nodeActions(
                        NodeTypes.Area.NAME,
                        new AppActionGroupDefinition("addingActions", new AddComponentAppActionDefinition()),
                        new AppActionGroupDefinition("edit", new EditAreaAppActionDefinition())
                );
    }

    public PageDetailAppBuilder extensionView(final ViewDefinition<? extends UIComponent> extensionViewDefinition) {
        extensionViews.add(extensionViewDefinition);
        return this;
    }

    public PageDetailAppBuilder defaultExtensionViews() {
        extensionViews.addAll(Set.of(
                new PublishingStatusViewDefinition(),
                new PlatformSelectorViewDefinition(),
                new LanguageSelectorViewDefinition()
        ));
        return this;
    }

    public PageDetailDescriptor build(final JcrDatasourceDefinition datasourceDefinition) {
        if (dropConstraint == null) {
            dropConstraint = new NodeTypeConstraintAwareDropConstraintDefinition();
        }

        final PageDetailDescriptor definition = new PageDetailDescriptor();
        definition.setName(NAME);
        definition.setExtensionViews(extensionViews);
        definition.setItemProvider(new JcrNodeFromLocationResolutionDefinition());
        definition.setActions(actions(contentContextMenuDefinitions, dropConstraint));
        definition.setActionbar(actionbar(contentContextMenuDefinitions, dropConstraint));
        definition.setDatasource(datasourceDefinition);
        return definition;
    }

    @Override
    protected Class<? extends ContentAppContextMenuDefinition> getContextMenuDefinitionType() {
        return PageAppContextMenuDefinition.class;
    }
}
