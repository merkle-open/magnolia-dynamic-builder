# Magnolia Appbuilder

The AppBuilder module is a builder for Magnolia apps in a java.

## Setup

### Add Maven dependency:
```xml
<dependency>
    <groupId>com.merkle.oss.magnolia</groupId>
    <artifactId>magnolia-appbuilder</artifactId>
    <version>1.4.0</version>
</dependency>
```

### DI-Bindings
```xml
<module>
    <name>SomeModule</name>
    ...
    <components>
        <id>main</id>
        <configurer>
            <class>GuiceComponentConfigurer</class>
        </configurer>
        <component>
            <type>com.namics.oss.magnolia.appbuilder.builder.DefaultColumnDefinitionsFactory</type>
            <implementation>...</implementation>
        </component>
    </components>
    ...
</module>
```

```java
import info.magnolia.objectfactory.guice.AbstractGuiceComponentConfigurer;

import org.apache.commons.lang3.reflect.TypeLiteral;
import org.reflections.Reflections;

import com.google.inject.multibindings.Multibinder;
import com.namics.oss.magnolia.appbuilder.annotations.AppFactories;
import com.namics.oss.magnolia.appbuilder.annotations.AppFactory;
import com.namics.oss.magnolia.appbuilder.annotations.ChooserDialogFactories;
import com.namics.oss.magnolia.appbuilder.annotations.ChooserDialogFactory;

public class GuiceComponentConfigurer extends AbstractGuiceComponentConfigurer {
    @Override
    protected void configure() {
        // Here we use Reflections, but you can also use ClassPathScanningCandidateComponentProvider or bind each factory manually 
        final Multibinder<Class<?>> appFactoryMultibinder = Multibinder.newSetBinder(binder(), new TypeLiteral<>() {}, AppFactories.class);
        new Reflections(getClass()).getTypesAnnotatedWith(AppFactory.class).forEach(clazz -> appFactoryMultibinder.addBinding().toInstance(clazz));

        final Multibinder<Class<?>> chooserDialogFactoryMultibinder = Multibinder.newSetBinder(binder(), new TypeLiteral<>() {}, ChooserDialogFactories.class);
        new Reflections(getClass()).getTypesAnnotatedWith(ChooserDialogFactory.class).forEach(clazz -> chooserDialogFactoryMultibinder.addBinding().toInstance(clazz));
    }
}
```

## How to use

### AppFactory
To create a new app, add a class with the `@AppFactory` annotation and at least one 
method annotated with `@SubApp` returning a `info.magnolia.ui.api.app.SubAppDescriptor`. Make sure the the class
in in a package which is scanned for `@AppFactory`s.

### ChooserDialog
By default Magnolia generates chooser dialogs with the workbench of the default supapp (See [AppAwareWorkbenchChooserDefinition](info.magnolia.ui.chooser.definition.AppAwareWorkbenchChooserDefinition)).


## Examples
The following class is a demo app, made with the AppBuilder:
### AppFactory

```java
import com.merkle.oss.magnolia.appbuilder.annotations.SubApp;
import com.merkle.oss.magnolia.appbuilder.builder.detail.DetailAppBuilder;
import com.merkle.oss.magnolia.definition.builder.contentapp.column.JcrStatusColumnDefinitionBuilder;
import com.merkle.oss.magnolia.definition.builder.contentapp.column.JcrTitleColumnDefinitionBuilder;
import com.merkle.oss.magnolia.definition.custom.contentapp.column.modificationdate.ModificationDateColumnDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.action.AppActionDefinitions;
import com.namics.oss.magnolia.appbuilder.action.AppActionGroupDefinition;
import com.namics.oss.magnolia.appbuilder.action.add.AddAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.action.edit.EditAppActionDefinition;
import com.namics.oss.magnolia.appbuilder.annotations.AppFactory;
import com.namics.oss.magnolia.appbuilder.annotations.Icon;
import com.namics.oss.magnolia.appbuilder.annotations.SubApp;
import com.namics.oss.magnolia.appbuilder.builder.BrowserAppBuilder;

import info.magnolia.icons.MagnoliaIcons;
import info.magnolia.jcr.util.NodeTypes;
import info.magnolia.ui.api.app.SubAppDescriptor;
import info.magnolia.ui.contentapp.configuration.column.ColumnDefinition;
import info.magnolia.ui.datasource.jcr.JcrDatasourceDefinition;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.jcr.Item;

@AppFactory(
        id = SampleApp.ID,
        name = SampleApp.NAME,
        label = SampleApp.NAME
)
@Icon(MagnoliaIcons.TAG_2_APP)
public class SampleApp {
    public static final String NAME = "SampleApp";
    public static final String ID = "module:apps/" + NAME;

    private static final Map<String, String> NODE_TYPES_ICONS = Map.of(
            NodeTypes.Folder.NAME, MagnoliaIcons.FOLDER.getCssClass()
    );

    private final List<ColumnDefinition<Item>> columns = List.of(
            new JcrTitleColumnDefinitionBuilder().nodeTypeToIcon(NODE_TYPES_ICONS).buildTyped(),
            new JcrStatusColumnDefinitionBuilder().build(),
            new ModificationDateColumnDefinitionBuilder().build()
    );

    private final Provider<DetailAppBuilder.Factory> detailAppBuilderFactory;

    @Inject
    public SampleApp(final Provider<DetailAppBuilder.Factory> detailAppBuilderFactory) {
        this.detailAppBuilderFactory = detailAppBuilderFactory;
    }

    @SubApp
    public SubAppDescriptor getBrowser() {
        return new BrowserAppBuilder<Item, JcrDatasourceDefinition>()
                .icon(MagnoliaIcons.TAG_2_APP)
                .columns(columns)
                .rootActions(
                        new AppActionGroupDefinition("addingActions", AddAppActionDefinition.FOLDER),
                        new AppActionGroupDefinition("activationActions", AppActionDefinitions.ACTIVATION),
                        new AppActionGroupDefinition("importExportActions", AppActionDefinitions.IMPORT_EXPORT)
                )
                .nodeActions(
                        NodeTypes.Folder.NAME,
                        EditAppActionDefinition.FOLDER,
                        new AppActionGroupDefinition("editActions", AppActionDefinitions.editActions(EditAppActionDefinition.FOLDER)),
                        new AppActionGroupDefinition("activationActions", AppActionDefinitions.ACTIVATION),
                        new AppActionGroupDefinition("importExportActions", AppActionDefinitions.IMPORT_EXPORT)
                )
                .build("<WORKSPACE>", Set.of(NodeTypes.Folder.NAME));
    }

    @SubApp
    public SubAppDescriptor getDetail() {
        return detailAppBuilderFactory.get().create().build(SomeDetailApp.class, SomeDetailApp.NAME, "<WORKSPACE>");
    }
}
```

```java
import info.magnolia.ui.field.EditorPropertyDefinition;

import java.util.List;

import com.merkle.oss.magnolia.appbuilder.builder.detail.DetailSubApp;
import com.merkle.oss.magnolia.formbuilder.annotation.PostCreate;
import com.merkle.oss.magnolia.formbuilder.annotation.TabFactory;
import com.merkle.oss.magnolia.formbuilder.annotation.TabOrder;

import info.magnolia.ui.contentapp.action.CommitActionDefinition;
import info.magnolia.ui.dialog.ConfiguredFormDialogDefinition;

@TabOrder({ "tabMain" })
public class SomeDetailApp {
    public static final String NAME = "someDetail"; 

    @TabFactory("tabMain")
    public List<EditorPropertyDefinition> tabMain(/* any parameter defined in com.merkle.oss.magnolia.appbuilder.builder.detail.parameter.DefaultDetailAppParameterResolver can be injected */) {
        return List.of(
                ...
        );
    }

    //Executed after the detailSubApp definition has been created
    @PostCreate
    public void postCreate(final DetailSubApp.Definition detailSubAppDefinition) {
        final CommitActionDefinition save = new CommitActionDefinition();
        save.setImplementationClass(SomeCustomCommitAction.class);
        detailSubAppDefinition.getActions().put(CommitActionDefinition.COMMIT_ACTION_NAME, save);
    }
}
```

### ChooserDialog sample:

```java
import com.merkle.oss.magnolia.definition.builder.contentapp.column.ColumnDefinitionBuilder;
import com.namics.oss.magnolia.appbuilder.annotations.ChooserDialogFactory;

import info.magnolia.ui.contentapp.configuration.ContentViewDefinition;
import info.magnolia.ui.contentapp.configuration.ListViewDefinition;
import info.magnolia.ui.contentapp.configuration.TreeViewDefinition;
import info.magnolia.ui.contentapp.configuration.column.ColumnDefinition;

import javax.jcr.Item;

import java.util.List;

@ChooserDialogFactory(
        id = SampleChooserDialog.ID,
        label = SampleChooserDialog.NAME + ".title. label"
)
public class SampleChooserDialog {
    public static final String NAME = "SampleChooserDialog";
    public static final String ID = "SomeApp:dialogs/" + NAME;

    private final List<ColumnDefinition<Item>> columns = List.of(
            new ColumnDefinitionBuilder<Item>().build("someField")
    );

    public ContentViewDefinition<Item> tree() {
        final TreeViewDefinition<Item> tree = new TreeViewDefinition<>();
        tree.setName("tree");
        tree.setColumns(columns);
        return tree;
    }

    public ContentViewDefinition<Item> list() {
        final ListViewDefinition<Item> list = new ListViewDefinition<>();
        list.setName("list");
        list.setColumns(columns);
        return list;
    }
}
```
```java
final LinkFieldDefinition<Node> definition = new LinkFieldDefinition<>();
definition.setChooserId(SampleChooserDialog.ID);
...
```

### ValueProvider sample:
```java
import com.namics.oss.magnolia.appbuilder.formatter.AbstractValueProvider;
import com.namics.oss.magnolia.powernode.PowerNodeService;

import javax.jcr.Node;
import javax.inject.Inject;
import java.util.Optional;

public class SampleValueProvider extends AbstractValueProvider {

	@Inject
	public SampleValueProvider(
			final PowerNodeService powerNodeService
    ) {
		super(powerNodeService, definition);
	}

	@Override
	protected Optional<String> getValue(final Node item) {
		final PowerNode powerNode = powerNodeService.convertToPowerNode(item);
		if (powerNode.isNodeType("<SOME_NODE_TYPE>")) {
			return powerNode.getPropertyValue("<SOME_FIELD>", String.class);
		}
		return Optional.empty();
	}
}
```
