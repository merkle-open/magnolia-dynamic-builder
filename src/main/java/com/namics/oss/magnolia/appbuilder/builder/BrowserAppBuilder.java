package com.namics.oss.magnolia.appbuilder.builder;

import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.AppActionGroupDefinition;
import com.namics.oss.magnolia.appbuilder.action.DoubleClickAction;
import com.namics.oss.magnolia.appbuilder.builder.generated.actionbar.ActionbarBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.column.MetaDataColumnBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.column.PropertyColumnBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.column.StatusColumnBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.contentconnector.JcrContentConnectorBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.custom.NodeTypeToActionDelegatingActionBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.presenter.ListPresenterBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.presenter.SearchPresenterBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.presenter.TreePresenterBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.subapp.BrowserSubAppBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.workbench.WorkbenchBuilder;
import com.namics.oss.magnolia.appbuilder.contextmenu.AppContextMenuDefinition;
import com.namics.oss.magnolia.appbuilder.contextmenu.ContentAppContextMenuDefinition;
import com.namics.oss.magnolia.appbuilder.contextmenu.RootAppContextMenuDefinition;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.ui.actionbar.definition.ActionbarDefinition;
import info.magnolia.ui.actionbar.definition.ActionbarSectionDefinition;
import info.magnolia.ui.api.action.ActionDefinition;
import info.magnolia.ui.contentapp.browser.BrowserSubApp;
import info.magnolia.ui.contentapp.browser.BrowserSubAppDescriptor;
import info.magnolia.ui.vaadin.integration.contentconnector.NodeTypeDefinition;
import info.magnolia.ui.vaadin.integration.jcr.ModelConstants;
import info.magnolia.ui.workbench.column.DateColumnFormatter;
import info.magnolia.ui.workbench.column.StatusColumnFormatter;
import info.magnolia.ui.workbench.column.definition.ColumnDefinition;
import info.magnolia.ui.workbench.definition.WorkbenchDefinition;
import info.magnolia.ui.workbench.tree.drop.DropConstraint;
import info.magnolia.ui.workbench.tree.drop.JcrDropConstraint;

import javax.annotation.Nullable;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class BrowserAppBuilder {
	private final List<ContentAppContextMenuDefinition> contentContextMenuDefinitions = new ArrayList<>();
	private List<AppActionGroupDefinition> rootActions = Collections.emptyList();
	private String icon = MgnlIcon.PACKAGER_APP;
	private Class<? extends DropConstraint> dropConstraint = JcrDropConstraint.class;
	private ColumnDefinition[] columnDefinitions = new ColumnDefinition[]{
			new PropertyColumnBuilder()
					.name("name")
					.editable(false)
					.expandRatio(1)
					.propertyName(ModelConstants.JCR_NAME)
					.sortable(true),
			new StatusColumnBuilder()
					.name("status")
					.width(46)
					.displayInChooseDialog(false)
					.formatterClass(StatusColumnFormatter.class),
			new MetaDataColumnBuilder()
					.name("moddate")
					.displayInChooseDialog(false)
					.formatterClass(DateColumnFormatter.class)
					.propertyName(NodeTypes.LastModified.NAME)
					.sortable(true)
					.width(160)
	};

	public BrowserAppBuilder icon(final String icon) {
		this.icon = icon;
		return this;
	}

	public BrowserAppBuilder rootActions(AppActionGroupDefinition... rootActions) {
		return rootActions(List.of(rootActions));
	}

	public BrowserAppBuilder rootActions(List<AppActionGroupDefinition> rootActions) {
		this.rootActions = rootActions;
		return this;
	}

	public BrowserAppBuilder nodeActions(
			final String nodeType,
			final List<AppActionGroupDefinition> actionGroups) {
		return nodeActions(nodeType, null, actionGroups);
	}

	public BrowserAppBuilder nodeActions(
			final String nodeType,
			final AppActionGroupDefinition... actionGroups) {
		return nodeActions(nodeType, null, actionGroups);
	}

	public BrowserAppBuilder nodeActions(
			final String nodeType,
			@Nullable final AppActionDefinition doubleClickAction,
			final AppActionGroupDefinition... actionGroups) {
		return nodeActions(nodeType, doubleClickAction, List.of(actionGroups));
	}

	public BrowserAppBuilder nodeActions(
			final String nodeType,
			@Nullable final AppActionDefinition doubleClickAction,
			final List<AppActionGroupDefinition> actionGroups) {
		contentContextMenuDefinitions.add(new ContentAppContextMenuDefinition(nodeType, doubleClickAction, actionGroups));
		return this;
	}

	public BrowserAppBuilder columns(final ColumnDefinition[] columnDefinitions) {
		this.columnDefinitions = columnDefinitions;
		return this;
	}

	public BrowserAppBuilder dropConstraint(final Class<? extends DropConstraint> dropConstraint) {
		this.dropConstraint = dropConstraint;
		return this;
	}

	public BrowserSubAppDescriptor build(final String workspace, final NodeTypeDefinition... nodeTypeDefinitions) {
		final List<AppContextMenuDefinition> contextMenuDefinitions = Stream.concat(
				Stream.of(new RootAppContextMenuDefinition(rootActions)),
				contentContextMenuDefinitions.stream()
		).collect(Collectors.toList());

		final JcrContentConnectorBuilder connector = getJcrContentConnector(workspace, nodeTypeDefinitions);
		return new BrowserSubAppBuilder()
				.subAppClass(BrowserSubApp.class)
				.name("browser")
				.icon(icon)
				.actions(actions(contextMenuDefinitions))
				.actionbar(actionbar(contextMenuDefinitions))
				.workbench(workbench(columnDefinitions, dropConstraint))
				.contentConnector(connector);
	}

	private ActionbarDefinition actionbar(final List<AppContextMenuDefinition> contextMenuDefinitions) {
		final List<ActionbarSectionDefinition> sections = contextMenuDefinitions.stream()
				.flatMap(AppContextMenuDefinition::sections)
				.collect(Collectors.toList());
		return new ActionbarBuilder()
				.defaultAction("defaultAction")
				.sections(sections);
	}

	private Map<String, ActionDefinition> actions(final List<AppContextMenuDefinition> contextMenuDefinitions) {
		final Map<String, String> doubleClickNodeTypeActions = contextMenuDefinitions.stream()
				.filter(nodeDefinition -> nodeDefinition instanceof ContentAppContextMenuDefinition)
				.map(nodeDefinition -> (ContentAppContextMenuDefinition) nodeDefinition)
				.map(ContentAppContextMenuDefinition::doubleClickAction)
				.filter(Optional::isPresent)
				.map(Optional::get)
				.collect(Collectors.toMap(DoubleClickAction::nodeType, DoubleClickAction::action));

		final ActionDefinition defaultAction = new NodeTypeToActionDelegatingActionBuilder()
				.name("defaultAction")
				.nodeTypeActionMapping(doubleClickNodeTypeActions);

		return Stream
				.concat(
						Stream.of(defaultAction),
						contextMenuDefinitions.stream().flatMap(AppContextMenuDefinition::actions)
				)
				.collect(Collectors.toMap(ActionDefinition::getName, action -> action, (t, t2) -> t)); //overwrite duplicate keys (==action name)
	}

	private WorkbenchDefinition workbench(
			final ColumnDefinition[] columnDefinitions,
			final Class<? extends DropConstraint> dropConstraint) {
		return new WorkbenchBuilder()
				.dropConstraintClass(dropConstraint)
				.editable(true)
				.contentViews(
						new TreePresenterBuilder().columns(columnDefinitions),
						new ListPresenterBuilder().columns(columnDefinitions),
						new SearchPresenterBuilder().columns(columnDefinitions)
				);
	}

	private JcrContentConnectorBuilder getJcrContentConnector(
			final String workspace,
			final NodeTypeDefinition... nodeTypeDefinitions) {
		return new JcrContentConnectorBuilder()
				.workspace(workspace)
				.defaultOrder("jcrName")
				.rootPath("/")
				.nodeTypes(nodeTypeDefinitions);
	}
}
