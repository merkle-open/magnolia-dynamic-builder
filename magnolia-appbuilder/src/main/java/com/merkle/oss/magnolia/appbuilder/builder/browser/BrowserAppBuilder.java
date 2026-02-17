package com.merkle.oss.magnolia.appbuilder.builder.browser;

import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.contentapp.ContentBrowserSubApp;
import info.magnolia.ui.contentapp.browser.drop.DropConstraintDefinition;
import info.magnolia.ui.contentapp.configuration.BrowserDescriptor;
import info.magnolia.ui.contentapp.configuration.ContentViewDefinition;
import info.magnolia.ui.contentapp.configuration.WorkbenchDefinition;
import info.magnolia.ui.contentapp.configuration.column.ColumnDefinition;
import info.magnolia.ui.datasource.DatasourceDefinition;
import info.magnolia.ui.datasource.jcr.JcrDatasourceDefinition;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.jcr.Item;

import com.merkle.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.merkle.oss.magnolia.appbuilder.action.AppActionGroupDefinition;
import com.merkle.oss.magnolia.appbuilder.builder.ActionFactory;
import com.merkle.oss.magnolia.appbuilder.builder.ActionbarFactory;
import com.merkle.oss.magnolia.appbuilder.contextmenu.AppContextMenuDefinition;
import com.merkle.oss.magnolia.appbuilder.contextmenu.ContentAppContextMenuDefinition;
import com.merkle.oss.magnolia.appbuilder.contextmenu.RootAppContextMenuDefinition;
import com.merkle.oss.magnolia.appbuilder.dropconstraint.NodeTypeConstraintAwareDropConstraintDefinition;
import com.merkle.oss.magnolia.definition.builder.datasource.JcrDatasourceDefinitionBuilder;
import com.vaadin.shared.data.sort.SortDirection;

import jakarta.annotation.Nullable;

public class BrowserAppBuilder {
	public static final String NAME = "browser";
	private final List<AppContextMenuDefinition> contextMenuDefinitions = new ArrayList<>();
	private List<AppActionGroupDefinition> rootActions = Collections.emptyList();
	@Nullable
	private String icon;
	@Nullable
	private DropConstraintDefinition dropConstraint;
	@Nullable
	private List<ColumnDefinition<Item>> columnDefinitions;
	@Nullable
	private Map<String, SortDirection> sortBy;
	@Nullable
	private BiFunction<DropConstraintDefinition, List<ColumnDefinition<Item>>, List<ContentViewDefinition<Item>>> contentViewFactory;
	@Nullable
	private Boolean hasSearchBar;

    private final ActionbarFactory actionbarFactory;
    private final ActionFactory actionFactory;

    public BrowserAppBuilder() {
		this(new ActionbarFactory(), new ActionFactory());
	}
	public BrowserAppBuilder(final ActionbarFactory actionbarFactory, final ActionFactory actionFactory) {
        this.actionbarFactory = actionbarFactory;
        this.actionFactory = actionFactory;
    }

	public BrowserAppBuilder contentViews(final BiFunction<DropConstraintDefinition, List<ColumnDefinition<Item>>, List<ContentViewDefinition<Item>>> contentViewFactory) {
		this.contentViewFactory = contentViewFactory;
		return this;
	}

	public BrowserAppBuilder icon(final MagnoliaIcons icon) {
		return icon(icon.getCssClass());
	}
	public BrowserAppBuilder icon(final String icon) {
		this.icon = icon;
		return this;
	}

	public BrowserAppBuilder rootActions(final AppActionGroupDefinition... rootActions) {
		return rootActions(List.of(rootActions));
	}
	public BrowserAppBuilder rootActions(final List<AppActionGroupDefinition> rootActions) {
		this.rootActions = rootActions;
		return this;
	}

	public BrowserAppBuilder nodeActions(final String nodeType, final List<AppActionGroupDefinition> actionGroups) {
		return nodeActions(nodeType, null, actionGroups);
	}
	public BrowserAppBuilder nodeActions(final String nodeType, final AppActionGroupDefinition... actionGroups) {
		return nodeActions(nodeType, null, actionGroups);
	}
	public BrowserAppBuilder nodeActions(
			final String nodeType,
			@Nullable final AppActionDefinition doubleClickAction,
			final AppActionGroupDefinition... actionGroups
	) {
		return nodeActions(nodeType, doubleClickAction, List.of(actionGroups));
	}
	public BrowserAppBuilder nodeActions(
			final String nodeType,
			@Nullable final AppActionDefinition doubleClickAction,
			final List<AppActionGroupDefinition> actionGroups
	) {
		return nodeActions(new ContentAppContextMenuDefinition(nodeType, doubleClickAction, actionGroups));
	}
	public BrowserAppBuilder nodeActions(final List<AppContextMenuDefinition> contextMenus) {
		contextMenus.forEach(this::nodeActions);
		return this;
	}
	public BrowserAppBuilder nodeActions(final AppContextMenuDefinition contextMenu) {
		contextMenuDefinitions.add(contextMenu);
		return this;
	}

	public BrowserAppBuilder columns(final List<ColumnDefinition<Item>> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
		return this;
	}

	public BrowserAppBuilder sortBy(final String propertyName, final SortDirection direction) {
		return sortBy(Stream.concat(
				Stream.ofNullable(sortBy).map(Map::entrySet).flatMap(Collection::stream),
				Stream.of(Map.entry(propertyName, direction))
		).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
	}
	public BrowserAppBuilder sortBy(final Map<String, SortDirection> sortBy) {
		this.sortBy = sortBy;
		return this;
	}

	public BrowserAppBuilder dropConstraint(final DropConstraintDefinition dropConstraint) {
		this.dropConstraint = dropConstraint;
		return this;
	}

	public BrowserAppBuilder hasSearchBar(final boolean hasSearchBar) {
		this.hasSearchBar = hasSearchBar;
		return this;
	}

	public BrowserDescriptor<Item, DatasourceDefinition> build(final String workspace, final Set<String> allowedNodeTypes) {
		final JcrDatasourceDefinition datasourceDefinition = getJcrDatasource(workspace, allowedNodeTypes);
		return build(datasourceDefinition);
	}

	public BrowserDescriptor<Item, DatasourceDefinition> build(final DatasourceDefinition datasourceDefinition) {
		final List<AppContextMenuDefinition> contextMenuDefinitions = Stream.concat(
				Stream.of(new RootAppContextMenuDefinition(rootActions)),
				this.contextMenuDefinitions.stream()
		).collect(Collectors.toList());
		if (dropConstraint == null) {
			dropConstraint = new NodeTypeConstraintAwareDropConstraintDefinition();
		}

		final BrowserDescriptor<Item, DatasourceDefinition> definition = new BrowserDescriptor<>();
		definition.setSubAppClass(ContentBrowserSubApp.class);
		definition.setName(NAME);
		definition.setIcon(icon != null ? icon : MagnoliaIcons.PACKAGER_APP.getCssClass());
		definition.setActions(actionFactory.create(contextMenuDefinitions, dropConstraint));
		definition.setActionbar(actionbarFactory.create(contextMenuDefinitions, dropConstraint));
		definition.setWorkbench(workbench(
				Optional.ofNullable(contentViewFactory).orElseGet(() -> new DefaultContentViewFactory(false)),
				Optional.ofNullable(columnDefinitions).orElseGet(Collections::emptyList),
				dropConstraint
		));
		definition.setDatasource(datasourceDefinition);
		return definition;
	}

	private WorkbenchDefinition<Item> workbench(
			final BiFunction<DropConstraintDefinition, List<ColumnDefinition<Item>>, List<ContentViewDefinition<Item>>> contentViewFactory,
			final List<ColumnDefinition<Item>> columnDefinitions,
			final DropConstraintDefinition dropConstraint
	) {
		final WorkbenchDefinition<Item> definition = new WorkbenchDefinition<>();
		definition.setContentViews(contentViewFactory.apply(dropConstraint, columnDefinitions));
		Optional.ofNullable(hasSearchBar).ifPresent(definition::setSearchEnabled);
		return definition;
	}

	private JcrDatasourceDefinition getJcrDatasource(
			final String workspace,
			final Set<String> allowedNodeTypes
	) {
		final JcrDatasourceDefinitionBuilder builder = new JcrDatasourceDefinitionBuilder()
				.workspace(workspace)
				.rootPath("/")
				.allowedNodeTypes(allowedNodeTypes);
		Optional.ofNullable(sortBy).ifPresent(builder::sortBy);
		Optional.ofNullable(sortBy).map(m -> m.get("jcrName")).ifPresent(nodeNameSort -> // workaround for https://jira.magnolia-cms.com/browse/MGNLUI-8725
				builder.sortBy("jcrPath", nodeNameSort)
		);
		return builder.build();
	}
}
