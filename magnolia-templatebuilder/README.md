# Magnolia Templatebuilder

The TemplateBuilder module is a builder for Magnolia templates in a java.

## Setup

### Add Maven dependency:
```xml
<dependency>
    <groupId>com.merkle.oss.magnolia</groupId>
    <artifactId>magnolia-templatebuilder</artifactId>
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
import com.merkle.oss.magnolia.templatebuilder.annotation.Template;
import com.merkle.oss.magnolia.templatebuilder.annotation.TemplateFactories;

public class GuiceComponentConfigurer extends AbstractGuiceComponentConfigurer {
    @Override
    protected void configure() {
        // Here we use Reflections, but you can also use ClassPathScanningCandidateComponentProvider or bind each factory manually 
        final Multibinder<Class<?>> templateFactoryMultibinder = Multibinder.newSetBinder(binder, new TypeLiteral<>() {}, TemplateFactories.class);
        new Reflections(getClass()).getTypesAnnotatedWith(Template.class).forEach(clazz -> templateFactoryMultibinder.addBinding().toInstance(clazz));
    }
}
```

## How to use

## Example

### Area-component-category
```java
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.merkle.oss.magnolia.templatebuilder.annotation.area.ComponentCategory;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ComponentCategory
public @interface ContentArea {}
```

### Component-template

```java
import com.merkle.oss.magnolia.templatebuilder.annotation.Template;

@ContentArea
@Template(
        id = SomeComponent.ID,
        title = "templates.components." + SomeComponent.NAME + ".title",
        dialog = SomeComponentDialog.ID,
        description = "templates.components." + SomeComponent.NAME + ".description",
        renderer = "freemarker",
        templateScript = "/someModule/templates/components/someComponent.ftl"
)
public class SomeComponent extends BaseComponent {
    public static final String NAME = "SomeComponent";
    public static final String ID = "SomeApp:components/" + NAME;
}
```
### Page-template with area

```java
import java.util.List;
import java.util.Set;

import javax.jcr.Node;

import com.merkle.oss.magnolia.templatebuilder.annotation.Available;
import com.merkle.oss.magnolia.templatebuilder.annotation.Template;
import com.merkle.oss.magnolia.templatebuilder.annotation.area.Area;
import com.merkle.oss.magnolia.templatebuilder.annotation.area.AvailableComponentClasses;
import com.merkle.oss.magnolia.templatebuilder.annotation.area.AvailableComponents;

@Template(
        id = SomePage.ID,
        title = "templates.pages." + SomePage.NAME + ".title",
        dialog = SomePageDialog.ID,
        description = "templates.pages." + SomePage.NAME + ".description",
        renderer = "freemarker",
        templateScript = "/someModule/templates/pages/somePage.ftl"
)
public class SomePage {
    public static final String NAME = "SomePage";
    public static final String ID = "SomeApp:pages/" + NAME;

    @Available
    public boolean isAvailable(final Node node) {
        //TODO implement
        return true;
    }

    @Area(
            id = ContentArea.ID,
            name = ContentArea.NAME, 
            title = "templates.areas." + SomePage.ContentArea.NAME + ".title",
            renderer = "freemarker", //optional, uses templates renderer if not specified
            templateScript = "/someModule/templates/areas/contentArea.ftl"
    )
    @AvailableComponentClasses({ ContentArea.class })
    @AvailableComponents({ "someComponentId" })
    public static class ContentArea {
        public static final String NAME = "ContentArea";
        public static final String ID = SomePage.ID + "/" + NAME;
    }
}
```

## Customization
### Definition decorators
Implement a decorator:
```java
import info.magnolia.config.registry.DefinitionProvider;
import info.magnolia.config.registry.DefinitionProviderWrapper;
import info.magnolia.config.registry.Registry;
import info.magnolia.config.registry.decoration.DefinitionDecorator;
import info.magnolia.config.registry.decoration.DefinitionDecoratorMetadata;
import info.magnolia.rendering.template.TemplateDefinition;


public class CustomTemplateDefinitionDecorator implements DefinitionDecorator<TemplateDefinition> {
    @Override
    public DefinitionDecoratorMetadata metadata() {
        return () -> "someModule";
    }

    @Override
    public boolean appliesTo(final DefinitionProvider<TemplateDefinition> definitionProvider) {
        //TODO implement
        return true;
    }

    @Override
    public DefinitionProvider<TemplateDefinition> decorate(final DefinitionProvider<TemplateDefinition> definitionProvider) {
        return new DefinitionProviderWrapper<>(definitionProvider) {
            @Override
            public TemplateDefinition get() throws Registry.InvalidDefinitionException {
                //TODO deorate definition
                return definitionProvider.get();
            }
        };
    }
}
```

Override the TemplateDefinitionProvider.Factory and pass your custom decorator to TemplateDefinitionProvider:
```java
import info.magnolia.objectfactory.Components;
import info.magnolia.rendering.template.configured.ConfiguredAreaDefinition;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.merkle.oss.magnolia.templatebuilder.TemplateAvailabilityResolver;
import com.merkle.oss.magnolia.templatebuilder.TemplateDefinitionProvider;

public class CustomTemplateDefinitionProviderFactory extends TemplateDefinitionProvider.Factory {
    private final TemplateAvailabilityResolver templateAvailabilityResolver;
    private final CustomTemplateDefinitionDecorator customTemplateDefinitionDecorator;

    @Inject
    public CustomTemplateDefinitionProviderFactory(
            final TemplateAvailabilityResolver templateAvailabilityResolver,
            final CustomTemplateDefinitionDecorator customTemplateDefinitionDecorator
    ) {
        super(templateAvailabilityResolver);
        this.templateAvailabilityResolver = templateAvailabilityResolver;
        this.customTemplateDefinitionDecorator = customTemplateDefinitionDecorator;
    }

    public TemplateDefinitionProvider create(final Set<Class<?>> templateFactories, final Class<?> factoryClass) {
        return new TemplateDefinitionProvider(
                List.of(customTemplateDefinitionDecorator),
                templateAvailabilityResolver,
                () -> Components.newInstance(ConfiguredAreaDefinition.class),
                templateFactories,
                () -> Components.newInstance(factoryClass),
                factoryClass
        );
    }
}
```
bind custom factory:
```xml
<component>
    <type>com.merkle.oss.magnolia.templatebuilder.TemplateDefinitionProvider.Factory</type>
    <implementation>com.somepackage.CustomTemplateDefinitionProviderFactory</implementation>
</component>
```

### ParameterResolver
Implement and bind different AvailabilityParameterResolverFactory to customize injectable `@Availabile` method arguments.

```java
import info.magnolia.rendering.template.TemplateDefinition;

import javax.inject.Inject;
import javax.jcr.Node;

import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;
import com.merkle.oss.magnolia.powernode.PowerNodeService;
import com.merkle.oss.magnolia.templatebuilder.parameter.AvailabilityParameterResolverFactory;
import com.merkle.oss.magnolia.templatebuilder.parameter.DefaultAvailabilityParameterResolver;

public class CustomAvailabilityParamResolver extends ParameterResolver {

    public CustomAvailabilityParamResolver(
            final Node node,
            final TemplateDefinition templateDefinition
    ) {
        super(new DefaultAvailabilityParameterResolver(node, templateDefinition));
    }

    @Override
    public Object resolveParameter(final Class<?> parameterType) {
        if (parameterType.equals(SomeCustomParam.class)) {
            return new SomeCustomParam();
        }
        return super.resolveParameter(parameterType);
    }

    public static class Factory implements AvailabilityParameterResolverFactory {
        @Override
        public ParameterResolver create(final Node node, final TemplateDefinition templateDefinition) {
            return new CustomAvailabilityParamResolver(node, templateDefinition);
        }
    }
}
```

```xml
<component>
    <type>com.merkle.oss.magnolia.templatebuilder.parameter.AvailabilityParameterResolverFactory</type>
    <implementation>com.somepackage.CustomAvailabilityParamResolver$Factory</implementation>
</component>
```

## Blossom migration notes
Search and replace.

### Template annotations
```java
import info.magnolia.module.blossom.annotation.([Template|Available]+);
```
```java
import com.merkle.oss.magnolia.templatebuilder.annotation.$1;
```

### Area annotations
```java
import info.magnolia.module.blossom.annotation.([ComponentCategory|AutoGenerator|AvailableComponentClasses|AvailableComponents|Area|Inherits]+);
```
```java
import com.merkle.oss.magnolia.templatebuilder.annotation.area.$1;
```

### Template

1. structural search and replace
    ```java
    @Template(
            id = $id$,
            title = $title$,
            dialog = $dialog$
    )
    ```
    ```java
    @Template(
            id = $id$,
            title = $title$,
            dialog = $dialog$,
            renderer = "blossom"
    )
    ```
2. search and replace
    ```java
    @TemplateDescription\((.*)\)
    ```
    ```java
    description = $1,
    ```
3. move description line manually into template annotation


### Area
```java
@Area\(value = ([^,]+)\.NAME,
```
```java
@Area\(id = $1.ID, name = $1.NAME,
```
