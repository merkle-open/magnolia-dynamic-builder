# Magnolia DialogBuilder

The DialogBuilder module is a builder for Magnolia dialogs in a java.

## Setup

### Add Maven dependency:
```xml
<dependency>
    <groupId>com.merkle.oss.magnolia</groupId>
    <artifactId>magnolia-dialogbuilder</artifactId>
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
    </components>
    ...
</module>
```

```java
import info.magnolia.objectfactory.guice.AbstractGuiceComponentConfigurer;
import info.magnolia.virtualuri.VirtualUriMapping;

import org.apache.commons.lang3.reflect.TypeLiteral;
import org.reflections.Reflections;

import com.google.inject.multibindings.Multibinder;
import com.merkle.oss.magnolia.dialogbuilder.annotation.DialogFactories;
import com.merkle.oss.magnolia.dialogbuilder.annotation.DialogFactory;

public class GuiceComponentConfigurer extends AbstractGuiceComponentConfigurer {
    @Override
    protected void configure() {
        // Here we use Reflections, but you can also use ClassPathScanningCandidateComponentProvider or bind each factory manually 
        final Multibinder<Class<?>> dialogFactoriesMultibinder = Multibinder.newSetBinder(binder(), new TypeLiteral<>() {}, DialogFactories.class);
        new Reflections(getClass()).getTypesAnnotatedWith(DialogFactory.class).forEach(clazz -> dialogFactoriesMultibinder.addBinding().toInstance(clazz));
    }
}
```

## How to use

### DialogFactory
To create a new dialog, add a class with the `@DialogFactory` annotation. To add tabs (can also be a single one) just add methods annotated with `@TabFactory`.
The tab order is defined with the `@TabOrder`annotation on the class.


## Example

```java
import info.magnolia.ui.editor.validator.NodeNameValidatorDefinition;
import info.magnolia.ui.field.EditorPropertyDefinition;

import java.util.List;

import org.springframework.stereotype.Component;

import com.merkle.oss.magnolia.dialogbuilder.annotation.DialogFactory;
import com.merkle.oss.magnolia.formbuilder.annotation.PostCreate;
import com.merkle.oss.magnolia.formbuilder.annotation.TabFactory;
import com.merkle.oss.magnolia.formbuilder.annotation.TabOrder;

import info.magnolia.ui.contentapp.action.CommitActionDefinition;
import info.magnolia.ui.dialog.ConfiguredFormDialogDefinition;

@Component
@DialogFactory(
        value = TextDialog.ID,
        label = TextDialog.NAME + ".title.label"
)
@TabOrder({ "tabMain" })
public class TextDialog {
    public static final String NAME = "TextDialog";
    public static final String ID = "SomeApp:dialogs/" + NAME;

    @TabFactory("tabMain")
    public List<EditorPropertyDefinition> tabMain(/* any parameter defined in com.merkle.oss.magnolia.dialogbuilder.parameter.DefaultDialogParameterResolver can be injected */) {
        return List.of(
                ...
        );
    }

    //Executed after the dialog has been created
    @PostCreate
    public void postCreate(final ConfiguredFormDialogDefinition<Node> dialog) {
        final CommitActionDefinition save = new CommitActionDefinition();
        save.setImplementationClass(SomeCustomCommitAction.class);
        dialog.getActions().put(CommitActionDefinition.COMMIT_ACTION_NAME, save);
    }
}
```

## Customization
### TabComparator
Bind a different TabComparatorFactory to adjust the order. 
```xml
<component>
    <type>com.merkle.oss.magnolia.formbuilder.FormFactory$TabComparatorFactory</type>
    <implementation>com.sampe.CustomTabComparatorFactory</implementation>
</component>
```

### ParameterResolver
Implement and bind different DialogParameterResolverFactory to customize injectable `@TabFactory` method arguments.
```java
import info.magnolia.ui.dialog.ConfiguredFormDialogDefinition;

import com.merkle.oss.magnolia.dialogbuilder.parameter.DefaultDialogParameterResolver;
import com.merkle.oss.magnolia.dialogbuilder.parameter.DialogCreationContext;
import com.merkle.oss.magnolia.dialogbuilder.parameter.DialogParameterResolverFactory;

public class CustomDialogParameterResolverFactory extends ParameterResolver {

    public CustomDialogParameterResolverFactory(
            final ConfiguredFormDialogDefinition<Node> dialog,
            final DialogCreationContext context
    ) {
        super(new DefaultDialogParameterResolver(dialog, context));
    }

    @Override
    public Object resolveParameter(final Class<?> parameterType) {
        if (parameterType.equals(SomeCustomParam.class)) {
            return new SomeCustomParam();
        }
        return super.resolveParameter(parameterType);
    }

    public static class Factory implements DialogParameterResolverFactory {
        @Override
        public ParameterResolver create(final DialogCreationContext context, final ConfiguredFormDialogDefinition<Node> dialog) {
            return new CustomDialogParameterResolverFactory(dialog, context);
        }
    }
}
```
```xml
<component>
    <type>com.merkle.oss.magnolia.dialogbuilder.parameter.DialogParameterResolverFactory</type>
    <implementation>com.sampe.CustomDialogParameterResolverFactory</implementation>
</component>
```
