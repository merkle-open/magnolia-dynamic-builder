package com.namics.oss.magnolia.appbuilder.builder;

import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.ui.actionbar.definition.ActionbarDefinition;
import info.magnolia.ui.actionbar.definition.ActionbarSectionDefinition;
import info.magnolia.ui.actionbar.definition.ConfiguredActionbarDefinition;
import info.magnolia.ui.api.action.ActionDefinition;
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

import javax.annotation.Nullable;

import com.merkle.oss.magnolia.definition.builder.availability.AvailabilityDefinitionBuilder;
import com.merkle.oss.magnolia.definition.builder.contentapp.column.JcrStatusColumnDefinitionBuilder;
import com.merkle.oss.magnolia.definition.builder.contentapp.column.JcrTitleColumnDefinitionBuilder;
import com.merkle.oss.magnolia.definition.builder.datasource.JcrDatasourceDefinitionBuilder;
import com.merkle.oss.magnolia.definition.custom.contentapp.column.modificationdate.ModificationDateColumnDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.AppActionGroupDefinition;
import com.namics.oss.magnolia.appbuilder.action.DoubleClickAction;
import com.namics.oss.magnolia.appbuilder.action.rule.JcrIsNotDeletedRuleDefinition;
import com.namics.oss.magnolia.appbuilder.builder.action.NodeTypeToActionDelegatingActionDefinition;
import com.namics.oss.magnolia.appbuilder.contextmenu.AppContextMenuDefinition;
import com.namics.oss.magnolia.appbuilder.contextmenu.ContentAppContextMenuDefinition;
import com.namics.oss.magnolia.appbuilder.contextmenu.RootAppContextMenuDefinition;
import com.namics.oss.magnolia.appbuilder.dropconstraint.NodeTypeConstraintAwareDropConstraintDefinition;
import com.vaadin.shared.data.sort.SortDirection;

public class BrowserAppBuilder<T, DS extends DatasourceDefinition> {
	private final List<ContentAppContextMenuDefinition> contentContextMenuDefinitions = new ArrayList<>();
	private List<AppActionGroupDefinition> rootActions = Collections.emptyList();
	@Nullable
	private String icon;
	@Nullable
	private DropConstraintDefinition dropConstraint;
	@Nullable
	private List<ColumnDefinition<T>> columnDefinitions;
	@Nullable
	private Map<String, String> nodeTypeIcons;
	@Nullable
	private Map<String, SortDirection> sortBy;
	@Nullable
	private BiFunction<DropConstraintDefinition, List<ColumnDefinition<T>>, List<ContentViewDefinition<T>>> contentViewFactory;

	public BrowserAppBuilder<T, DS> contentViews(final BiFunction<DropConstraintDefinition, List<ColumnDefinition<T>>, List<ContentViewDefinition<T>>> contentViewFactory) {
		this.contentViewFactory = contentViewFactory;
		return this;
	}

	public BrowserAppBuilder<T, DS> icon(final MagnoliaIcons icon) {
		return icon(icon.getCssClass());
	}
	public BrowserAppBuilder<T, DS> icon(final String icon) {
		this.icon = icon;
		return this;
	}

	public BrowserAppBuilder<T, DS> rootActions(final AppActionGroupDefinition... rootActions) {
		return rootActions(List.of(rootActions));
	}
	public BrowserAppBuilder<T, DS> rootActions(final List<AppActionGroupDefinition> rootActions) {
		this.rootActions = rootActions;
		return this;
	}

	public BrowserAppBuilder<T, DS> nodeActions(final String nodeType, final List<AppActionGroupDefinition> actionGroups) {
		return nodeActions(nodeType, null, actionGroups);
	}
	public BrowserAppBuilder<T, DS> nodeActions(final String nodeType, final AppActionGroupDefinition... actionGroups) {
		return nodeActions(nodeType, null, actionGroups);
	}
	public BrowserAppBuilder<T, DS> nodeActions(
			final String nodeType,
			@Nullable final AppActionDefinition doubleClickAction,
			final AppActionGroupDefinition... actionGroups
	) {
		return nodeActions(nodeType, doubleClickAction, List.of(actionGroups));
	}
	public BrowserAppBuilder<T, DS> nodeActions(
			final String nodeType,
			@Nullable final AppActionDefinition doubleClickAction,
			final List<AppActionGroupDefinition> actionGroups
	) {
		contentContextMenuDefinitions.add(new ContentAppContextMenuDefinition(nodeType, doubleClickAction, actionGroups));
		return this;
	}

	public BrowserAppBuilder<T, DS> columns(final List<ColumnDefinition<T>> columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
		return this;
	}

	public BrowserAppBuilder<T, DS> nodeTypeIcon(final String nodeType, final MagnoliaIcons icon) {
		return nodeTypeIcon(nodeType, icon.getCssClass());
	}
	public BrowserAppBuilder<T, DS> nodeTypeIcon(final String nodeType, final String icon) {
		return nodeTypeIcons(Stream.concat(
				Stream.ofNullable(nodeTypeIcons).map(Map::entrySet).flatMap(Collection::stream),
				Stream.of(Map.entry(nodeType, icon))
		).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
	}
	public BrowserAppBuilder<T, DS> nodeTypeIconsTyped(final Map<String, MagnoliaIcons> nodeTypeIcons) {
		return nodeTypeIcons(nodeTypeIcons.entrySet().stream()
				.collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getCssClass()))
		);
	}
	public BrowserAppBuilder<T, DS> nodeTypeIcons(final Map<String, String> nodeTypeIcons) {
		this.nodeTypeIcons = nodeTypeIcons;
		return this;
	}

	public BrowserAppBuilder<T, DS> sortBy(final String propertyName, final SortDirection direction) {
		return sortBy(Stream.concat(
				Stream.ofNullable(sortBy).map(Map::entrySet).flatMap(Collection::stream),
				Stream.of(Map.entry(propertyName, direction))
		).collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
	}
	public BrowserAppBuilder<T, DS> sortBy(final Map<String, SortDirection> sortBy) {
		this.sortBy = sortBy;
		return this;
	}

	public BrowserAppBuilder<T, DS> dropConstraint(final DropConstraintDefinition dropConstraint) {
		this.dropConstraint = dropConstraint;
		return this;
	}

	public BrowserDescriptor<T, DS> build(final String workspace, final Set<String> allowedNodeTypes) {
		final JcrDatasourceDefinition datasource = getJcrDatasource(workspace, allowedNodeTypes);
		return build((DS) datasource);
	}

	public BrowserDescriptor<T, DS> build(final DS datasourceDefinition) {
		final List<AppContextMenuDefinition> contextMenuDefinitions = Stream.concat(
				Stream.of(new RootAppContextMenuDefinition(rootActions)),
				contentContextMenuDefinitions.stream()
		).collect(Collectors.toList());

		final BrowserDescriptor<T, DS> definition = new BrowserDescriptor<>();
		definition.setSubAppClass(ContentBrowserSubApp.class);
		definition.setName("browser");
		definition.setIcon(icon != null ? icon : MagnoliaIcons.PACKAGER_APP.getCssClass());
		definition.setActions(actions(contextMenuDefinitions));
		definition.setActionbar(actionbar(contextMenuDefinitions));
		if (dropConstraint == null) {
			dropConstraint = new NodeTypeConstraintAwareDropConstraintDefinition();
		}
		definition.setWorkbench(workbench(
				contentViewFactory != null ? contentViewFactory : new DefaultContentViewFactory<>(false),
				getColumnDefinitions(),
				dropConstraint
		));
		definition.setDatasource(datasourceDefinition);
		return definition;
	}

	private List<ColumnDefinition<T>> getColumnDefinitions() {
		if (columnDefinitions != null) {
			return columnDefinitions;
		}
		return List.of(
				new JcrTitleColumnDefinitionBuilder().nodeTypeToIcon(nodeTypeIcons != null ? nodeTypeIcons : Collections.emptyMap()).build(),
				(ColumnDefinition<T>)new JcrStatusColumnDefinitionBuilder().build(),
				(ColumnDefinition<T>)new ModificationDateColumnDefinitionBuilder().build()
		);
	}

	private ActionbarDefinition actionbar(final List<AppContextMenuDefinition> contextMenuDefinitions) {
		final List<ActionbarSectionDefinition> sections = contextMenuDefinitions.stream()
				.flatMap(AppContextMenuDefinition::sections)
				.collect(Collectors.toList());
		final ConfiguredActionbarDefinition definition = new ConfiguredActionbarDefinition();
		definition.setDefaultAction("defaultAction");
		definition.setSections(sections);
		return definition;
	}

	private Map<String, ActionDefinition> actions(final List<AppContextMenuDefinition> contextMenuDefinitions) {
		final Map<String, String> doubleClickNodeTypeActions = contextMenuDefinitions.stream()
				.filter(ContentAppContextMenuDefinition.class::isInstance)
				.map(ContentAppContextMenuDefinition.class::cast)
				.map(ContentAppContextMenuDefinition::doubleClickAction)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toMap(DoubleClickAction::nodeType, DoubleClickAction::action));

		final NodeTypeToActionDelegatingActionDefinition defaultAction = new NodeTypeToActionDelegatingActionDefinition();
		defaultAction.setName("defaultAction");
		defaultAction.setNodeTypeActionMapping(doubleClickNodeTypeActions);
		defaultAction.setAvailability(new AvailabilityDefinitionBuilder().rule(new JcrIsNotDeletedRuleDefinition()).build());
		return Stream
				.concat(
						Stream.of(defaultAction),
						contextMenuDefinitions.stream().flatMap(AppContextMenuDefinition::actions)
				)
				.collect(Collectors.toMap(ActionDefinition::getName, action -> action, (t, t2) -> t)); //overwrite duplicate keys (==action name)
	}

	private WorkbenchDefinition<T> workbench(
			final BiFunction<DropConstraintDefinition, List<ColumnDefinition<T>>, List<ContentViewDefinition<T>>> contentViewFactory,
			final List<ColumnDefinition<T>> columnDefinitions,
			final DropConstraintDefinition dropConstraint
	) {
		final WorkbenchDefinition<T> definition = new WorkbenchDefinition<>();
		definition.setContentViews(contentViewFactory.apply(dropConstraint, columnDefinitions));
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
