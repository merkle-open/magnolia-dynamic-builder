# Magnolia Appbuilder

The AppBuilder module is a builder for Magnolia apps in a Blossom 
context comparable to the Blossom DialogBuilder.

Instead of using YAML, it allows to define apps in Java which is less 
error prone than using YAML, especially for big apps.

## Requirements
* Java 11
* Spring >=5
* Magnolia >= 6.0
* Blossom >= 3.2

## Installation

* Add Maven dependency:
```xml
<dependency>
    <groupId>com.namics.oss.magnolia</groupId>
    <artifactId>magnolia-appbuilder</artifactId>
    <version>1.0.2</version>
</dependency>
```

* Import Spring configuration:
```java
[...]
@Configuration
@Import({AppBuilderConfiguration.class})
public class BlossomServletConfiguration {
	[...]
}
```

* Extend the component scan of the Spring configuration:
```java
@ComponentScan.Filter(AppFactory.class),
@ComponentScan.Filter(AppLauncherGroup.class)
```

## How to use

To create a new app, add a class with the `@AppFactory` annotation and at least one 
method annotated with `@SubApp` returning a `info.magnolia.ui.api.app.SubAppDescriptor`. Make sure the the class
in in a package which is scanned for `@AppFactory`s.

For a quick overview check the [examples](#examples) bellow.

### Annotations

#### AppFactory (Target: Class)
Marks a class as AppFactory. The annotation properties define the basic app properties
like 'name', 'label' and 'icon'.

This annotation also allows to place the app in a Launcher Group. It is possible
to create a new group, see ['Creating an App Launcher Group'](#creating-an-app-launcher-group),
and add the app to the newly created group, or to use an existing Launcher Group. 
The position of the app in the group can be configured with the 'order' annotation property.

#### SubApp (Target: Method)
The AppFactory requires at least one method marked with the `@SubApp` annotation
this method must return a `info.magnolia.ui.api.app.SubAppDescriptor`.

#### ChooseDialog, optional (Target: Method)
A method marked with `@ChooseDialog` must return a `info.magnolia.ui.dialog.definition.ChooseDialogDefinition`.

#### AppPermissions, optional (Target: Method)
A method marked with `@AppPermissions` must return a `info.magnolia.cms.security.operations.AccessDefinition`.

### Creating an App Launcher Group
Annotate a class with `@AppLauncherGroup`, and add the name as annotation property.
Optionally the class can be added a method with a `@GroupDefinition` annotation,
returning a `SimpleGroupDefinition`

### Multiple 'defaultActions' (double click actions)
The `NodeTypeToActionDelegatingAction` action wrapper allows to define 
'doubleclick-actions' per node type. Define the Action as follows:
* Define an action per node type
* Define a fallback action for not specified node types
* Set this action as defaultAction in the ActionBarDefinition
```java
[...]
"defaultAction", new NodeTypeToActionDelegatingActionBuilder()
		.fallbackAction("rename")
		.nodeTypeActionMapping(
				NodeTypes.Content.NAME, "rename",
				NodeTypes.ContentNode.NAME, "rename"
		)
[...]	
```
This action wrapper can be used in YAML files as well.

## How it works
The AppBuilder allows to create a app definition using the builder pattern.
The builder classes are automatically generated and extend the respective definition class.
This way, the whole AppBuilder is fully compatible to the definition classes
provided by Magnolia. 

## Examples
The following class is a demo app, made with the AppBuilder:
```java
import com.namics.oss.magnolia.appbuilder.MgnlIcon;
import com.namics.oss.magnolia.appbuilder.builder.generated.action.*;
import com.namics.oss.magnolia.appbuilder.annotations.AppFactory;
import com.namics.oss.magnolia.appbuilder.annotations.AppPermissions;
import com.namics.oss.magnolia.appbuilder.annotations.ChooseDialog;
import com.namics.oss.magnolia.appbuilder.annotations.SubApp;
import com.namics.oss.magnolia.appbuilder.builder.generated.actionbar.ActionbarBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.actionbar.ActionbarGroupBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.actionbar.ActionbarSectionBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.availability.AvailabilityRuleBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.choosedialog.ChooseDialogBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.choosedialog.WorkbenchFieldBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.column.MetaDataColumnBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.column.PropertyColumnBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.column.StatusColumnBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.contentconnector.JcrContentConnectorBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.contentconnector.NodeTypeBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.custom.NodeTypeToActionDelegatingActionBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.permission.AccessBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.presenter.ListPresenterBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.presenter.SearchPresenterBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.presenter.TreePresenterBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.subapp.BrowserSubAppBuilder;
import com.namics.oss.magnolia.appbuilder.builder.generated.workbench.WorkbenchBuilder;
import com.namics.oss.magnolia.appbuilder.builder.helper.actionbar.SimpleActionBarItem;
import info.magnolia.cms.security.operations.AccessDefinition;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.repository.RepositoryConstants;
import info.magnolia.ui.admincentral.availability.ConfigProtectedNodeRule;
import info.magnolia.ui.api.app.SubAppDescriptor;
import info.magnolia.ui.contentapp.browser.BrowserSubApp;
import info.magnolia.ui.dialog.definition.ChooseDialogDefinition;
import info.magnolia.ui.framework.availability.AcceptsClipboardContent;
import info.magnolia.ui.framework.availability.IsDefinitionRule;
import info.magnolia.ui.framework.availability.IsPublishableRule;
import info.magnolia.ui.vaadin.integration.jcr.ModelConstants;
import info.magnolia.ui.workbench.column.DateColumnFormatter;
import info.magnolia.ui.workbench.column.PathColumnFormatter;
import info.magnolia.ui.workbench.column.StatusColumnFormatter;
import info.magnolia.ui.workbench.column.definition.ColumnDefinition;
import info.magnolia.ui.workbench.tree.drop.NodesAndPropsDropConstraint;

@AppFactory(
		id = AppBuilderDemo.ID,
		name = AppBuilderDemo.NAME,
		label = AppBuilderDemo.NAME,
		icon = MgnlIcon.CONFIGURATION_FILES_APP,
		launcherGroup = BlitzGroup.NAME
)
public class AppBuilderDemo {

	public static final String NAME = "AppBuilderDemo";
	public static final String ID = BlitzdingsCoreTemplatingConstants.APPS_PREFIX + NAME;

	@AppPermissions
	public AccessDefinition getAppPremissions() {
		return new AccessBuilder()
				.role("superuser");
	}

	@ChooseDialog
	public ChooseDialogDefinition getChooseDialog() {
		return new ChooseDialogBuilder()
				.field(new WorkbenchFieldBuilder()
						.workbench(new WorkbenchBuilder()
								.contentViews(new ListPresenterBuilder()
										.columns(new PropertyColumnBuilder()
												.name(ModelConstants.JCR_NAME)
												.sortable(false)
										)
								)
						)
				)
				.contentConnector(getJcrContentConnector());
	}

	@SubApp
	public SubAppDescriptor getBrowser() {
		return new BrowserSubAppBuilder()
				.subAppClass(BrowserSubApp.class)
				.name("browser")
				.actions(
						"addFolder", new AddNodeActionBuilder()
								.name("addFolder")
								.icon(MgnlIcon.ADD_FOLDER)
								.nodeType(NodeTypes.Content.NAME)
								.availability(new AvailabilityBuilder()
										.root(true)
										.nodeTypes(NodeTypes.Content.NAME)),
						"addNode", new AddNodeActionBuilder()
								.name("addNode")
								.icon(MgnlIcon.ADD_NODE_CONTENT)
								.availability(new AvailabilityBuilder()
										.root(true)),
						"addProperty", new AddPropertyActionBuilder()
								.name("addProperty")
								.icon(MgnlIcon.ADD_NODE_DATA),
						"confirmDeletion", new DeleteConfirmationActionBuilder()
								.name("confirmDeletion")
								.icon(MgnlIcon.DELETE)
								.successActionName("delete")
								.availability(new AvailabilityBuilder()
										.multiple(true)
										.properties(true)),
						"duplicate", new DuplicateNodeActionBuilder()
								.name("duplicate")
								.icon(MgnlIcon.DUPLICATE),
						"rename", new OpenEditDialogActionBuilder()
								.name("rename")
								.icon(MgnlIcon.EDIT)
								.dialogName("ui-admincentral:editProperty")
								.availability(new AvailabilityBuilder()
										.properties(false)),
						"editProperty", new OpenEditDialogActionBuilder()
								.name("editProperty")
								.icon(MgnlIcon.EDIT)
								.dialogName("ui-admincentral:editProperty")
								.availability(new AvailabilityBuilder()
										.properties(true)
										.nodes(false)),
						"activate", new ActivationActionBuilder()
								.name("activate")
								.icon(MgnlIcon.PUBLISH),
						"activateRecursive", new ActivationActionBuilder()
								.name("activateRecursive")
								.icon(MgnlIcon.PUBLISH_INCL_SUB)
								.command("activate")
								.recursive(true)
								.asynchronous(true),
						"deactivate", new DeactivationActionBuilder()
								.name("deactivate")
								.icon(MgnlIcon.UNPUBLISH)
								.availability(new AvailabilityBuilder()
										.rules(new AvailabilityRuleBuilder()
														.implementationClass(IsPublishableRule.class),
												new AvailabilityRuleBuilder()
														.implementationClass(ConfigProtectedNodeRule.class)
										)),
						"export", new OpenExportDialogActionBuilder()
								.name("export")
								.icon(MgnlIcon.EXPORT)
								.dialogName("ui-admincentral:export")
								.availability(new AvailabilityBuilder()
										.root(true)),
						"import", new OpenCreateDialogActionBuilder()
								.name("import")
								.icon(MgnlIcon.IMPORT)
								.dialogName("ui-admincentral:import")
								.availability(new AvailabilityBuilder()
										.root(true)),
						"delete", new DeleteActionBuilder()
								.name("delete")
								.asynchronous(true),
						"move", new OpenMoveDialogActionBuilder()
								.name("move")
								.icon(MgnlIcon.MOVE)
								.availability(new AvailabilityBuilder()
										.multiple(true)
										.properties(true)),
						"saveItemProperty", new SaveItemPropertyActionBuilder()
								.name("saveItemProperty")
								.availability(new AvailabilityBuilder()
										.properties(true)),
						"downloadYaml", new ExportYamlActionBuilder()
								.name("downloadYaml")
								.icon(MgnlIcon.DOWNLOAD)
								.command("exportYaml")
								.availability(new AvailabilityBuilder()
										.rules(new AvailabilityRuleBuilder()
												.implementationClass(IsDefinitionRule.class))),
						"copy", new CopyContentActionBuilder()
								.name("copy")
								.icon(MgnlIcon.COPY)
								.availability(new AvailabilityBuilder()
										.multiple(true)
										.properties(true)),
						"paste", new PasteContentActionBuilder()
								.name("paste")
								.icon(MgnlIcon.PASTE)
								.availability(new AvailabilityBuilder()
										.root(true)
										.rules(new AvailabilityRuleBuilder()
												.implementationClass(AcceptsClipboardContent.class))),
						// DEMO: allow multiple default (double click) actions.
						// - Define an action per node type
						// - Define a fallback action for not specified node types
						// - Set this action as defaultAction in the ActionBarDefinition
						"defaultAction", new NodeTypeToActionDelegatingActionBuilder()
								.fallbackAction("rename")
								.nodeTypeActionMapping(
										NodeTypes.Content.NAME, "rename",
										NodeTypes.ContentNode.NAME, "rename"
								)
				).actionbar(new ActionbarBuilder()
						.defaultAction("defaultAction")
						.sections(
								new ActionbarSectionBuilder()
										.name("root")
										.groups(new ActionbarGroupBuilder()
														.name("addingActions")
														.items(SimpleActionBarItem.names(
																"addFolder",
																"addNode",
																"addProperty",
																"confirmDeletion")),
												new ActionbarGroupBuilder()
														.name("editActions")
														.items(SimpleActionBarItem.names(
																"rename",
																"duplicate",
																"move",
																"copy",
																"paste")),
												new ActionbarGroupBuilder()
														.name("activationActions")
														.items(SimpleActionBarItem.names(
																"activate",
																"activateRecursive",
																"deactivate")),
												new ActionbarGroupBuilder()
														.name("downloadActions")
														.items(SimpleActionBarItem.names(
																"downloadYaml")),
												new ActionbarGroupBuilder()
														.name("importExportActions")
														.items(SimpleActionBarItem.names(
																"export",
																"import")))
										.availability(new AvailabilityBuilder()
												.nodes(false)
												.root(true)),
								new ActionbarSectionBuilder()
										.name("properties")
										.groups(
												new ActionbarGroupBuilder()
														.name("addingActions")
														.items(SimpleActionBarItem.names(
																"addFolder",
																"addNode",
																"addProperty",
																"confirmDeletion")),
												new ActionbarGroupBuilder()
														.name("editActions")
														.items(SimpleActionBarItem.names(
																"editProperty",
																"rename",
																"duplicate",
																"move",
																"copy",
																"paste")),
												new ActionbarGroupBuilder()
														.name("activationActions")
														.items(SimpleActionBarItem.names(
																"activate",
																"activateRecursive",
																"deactivate")),
												new ActionbarGroupBuilder()
														.name("downloadActions")
														.items(SimpleActionBarItem.names(
																"downloadYaml")),
												new ActionbarGroupBuilder()
														.name("importExportActions")
														.items(SimpleActionBarItem.names(
																"export",
																"import")))
										.availability(new AvailabilityBuilder()
												.nodes(false)
												.properties(true)),
								new ActionbarSectionBuilder()
										.name("nodes")
										.groups(
												new ActionbarGroupBuilder()
														.name("addingActions")
														.items(SimpleActionBarItem.names(
																"addFolder",
																"addNode",
																"addProperty",
																"confirmDeletion")),
												new ActionbarGroupBuilder()
														.name("editActions")
														.items(SimpleActionBarItem.names(
																"editProperty",
																"rename",
																"duplicate",
																"move",
																"copy",
																"paste")),
												new ActionbarGroupBuilder()
														.name("activationActions")
														.items(SimpleActionBarItem.names(
																"activate",
																"activateRecursive",
																"deactivate")),
												new ActionbarGroupBuilder()
														.name("downloadActions")
														.items(SimpleActionBarItem.names(
																"downloadYaml")),
												new ActionbarGroupBuilder()
														.name("importExportActions")
														.items(SimpleActionBarItem.names(
																"export",
																"import")))
										.availability(new AvailabilityBuilder()
												.nodeTypes(NodeTypes.ContentNode.NAME)),
								new ActionbarSectionBuilder()
										.name("folders")
										.groups(
												new ActionbarGroupBuilder()
														.name("addingActions")
														.items(SimpleActionBarItem.names(
																"addFolder",
																"addNode",
																"addProperty",
																"confirmDeletion")),
												new ActionbarGroupBuilder()
														.name("editActions")
														.items(SimpleActionBarItem.names(
																"editProperty",
																"rename",
																"duplicate",
																"move",
																"copy",
																"paste")),
												new ActionbarGroupBuilder()
														.name("activationActions")
														.items(SimpleActionBarItem.names(
																"activate",
																"activateRecursive",
																"deactivate")),
												new ActionbarGroupBuilder()
														.name("downloadActions")
														.items(SimpleActionBarItem.names(
																"downloadYaml")),
												new ActionbarGroupBuilder()
														.name("importExportActions")
														.items(SimpleActionBarItem.names(
																"export",
																"import")))
										.availability(new AvailabilityBuilder()
												.nodeTypes(NodeTypes.Content.NAME)),
								new ActionbarSectionBuilder()
										.name("multiple")
										.groups(
												new ActionbarGroupBuilder()
														.name("addingActions")
														.items(SimpleActionBarItem.names(
																"addFolder",
																"addNode",
																"addProperty",
																"confirmDeletion")),
												new ActionbarGroupBuilder()
														.name("editActions")
														.items(SimpleActionBarItem.names(
																"editProperty",
																"rename",
																"duplicate",
																"move",
																"copy",
																"paste")),
												new ActionbarGroupBuilder()
														.name("activationActions")
														.items(SimpleActionBarItem.names(
																"activate",
																"activateRecursive",
																"deactivate")),
												new ActionbarGroupBuilder()
														.name("downloadActions")
														.items(SimpleActionBarItem.names(
																"downloadYaml")),
												new ActionbarGroupBuilder()
														.name("importExportActions")
														.items(SimpleActionBarItem.names(
																"export",
																"import")))
										.availability(new AvailabilityBuilder()
												.properties(true)))
				).workbench(new WorkbenchBuilder()
						.dropConstraintClass(NodesAndPropsDropConstraint.class)
						.editable(true)
						.contentViews(
								new TreePresenterBuilder()
										.columns(getColumnDefinitions()),
								new ListPresenterBuilder()
										.columns(getColumnDefinitions()),
								new SearchPresenterBuilder()
										.columns(getColumnDefinitions())
						)
				).contentConnector(getJcrContentConnector());
	}

	private ColumnDefinition[] getColumnDefinitions() {
		return new ColumnDefinition[]{
				new PropertyColumnBuilder()
						.name("name")
						.editable(true)
						.expandRatio(4)
						.propertyName(ModelConstants.JCR_NAME)
						.sortable(true),
				new PropertyColumnBuilder()
						.name("version")
						.editable(true)
						.expandRatio(0)
						.propertyName("version")
						.sortable(true),
				new MetaDataColumnBuilder()
						.name("path")
						.enabled(true)
						.expandRatio(4)
						.formatterClass(PathColumnFormatter.class),
				new PropertyColumnBuilder()
						.name("value")
						.editable(true)
						.expandRatio(3)
						.propertyName("value"),
				new PropertyColumnBuilder()
						.name("type")
						.editable(true)
						.expandRatio(0)
						.propertyName("type"),
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
	}

	private JcrContentConnectorBuilder getJcrContentConnector() {
		return new JcrContentConnectorBuilder()
				.workspace(RepositoryConstants.CONFIG)
				.defaultOrder(ModelConstants.JCR_NAME)
				.rootPath("/")
				.includeProperties(true)
				.nodeTypes(
						new NodeTypeBuilder()
								.name(NodeTypes.ContentNode.NAME)
								.icon(MgnlIcon.NODE_CONTENT)
								.strict(true),
						new NodeTypeBuilder()
								.name(NodeTypes.Content.NAME)
								.icon(MgnlIcon.FOLDER)
								.strict(true)
				);
	}
}
```

The following class creates an 'App Launcher Group':
```java
import com.namics.oss.magnolia.appbuilder.annotations.AppLauncherGroup;
import com.namics.oss.magnolia.appbuilder.annotations.GroupDefinition;
import com.namics.oss.magnolia.appbuilder.launcher.group.SimpleGroupDefinition;


@AppLauncherGroup(
		name = BlitzGroup.NAME
)
public class BlitzGroup {

	public static final String NAME = "blitz";

	@GroupDefinition
	public SimpleGroupDefinition getDefinition() {
		return new SimpleGroupDefinition(NAME)
				.color("#e05343");
	}

}
```