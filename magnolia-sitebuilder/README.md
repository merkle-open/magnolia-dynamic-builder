# Magnolia Sitebuilder

The SiteBuilder module is a builder for Magnolia sites in a java.

## Setup

### Add Maven dependency:
```xml
<dependency>
    <groupId>com.merkle.oss.magnolia</groupId>
    <artifactId>magnolia-sitebuilder</artifactId>
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
import com.merkle.oss.magnolia.sitebuilder.annotation.Site;
import com.merkle.oss.magnolia.sitebuilder.annotation.SiteFactories;

public class GuiceComponentConfigurer extends AbstractGuiceComponentConfigurer {
    @Override
    protected void configure() {
        // Here we use Reflections, but you can also use ClassPathScanningCandidateComponentProvider or bind each factory manually 
        final Multibinder<Class<?>> siteFactoryMultibinder = Multibinder.newSetBinder(binder, new TypeLiteral<>() {}, SiteFactories.class);
        new Reflections(getClass()).getTypesAnnotatedWith(Site.class).forEach(clazz -> siteFactoryMultibinder.addBinding().toInstance(clazz));
    }
}
```

## How to use

## Example

### Fallback site
```java
import info.magnolia.cms.i18n.HierarchyBasedI18nContentSupport;
import info.magnolia.multisite.sites.MultiSiteManager;

import com.merkle.oss.magnolia.renderer.spring.SpringRenderer;
import com.merkle.oss.magnolia.sitebuilder.annotation.Site;
import com.merkle.powerboost.web.core.PowerboostCoreTemplatingConstants;

@Site(
        id = "SomeApp:sites/" + FallbackSite.NAME,
        name = FallbackSite.NAME,
        templates = @Site.Templates(),
        i18n = @Site.I18n(
                clazz = HierarchyBasedI18nContentSupport.class,
                fallbackLocale = @Site.I18n.Locale(language = "de"),
                defaultLocale = @Site.I18n.Locale(language = "de"),
                locales = {
                        @Site.I18n.Locale(language = "de"),
                        @Site.I18n.Locale(language = "fr"),
                        @Site.I18n.Locale(language = "it"),
                        @Site.I18n.Locale(language = "en"),
                }
        )
)
public class FallbackSite {
    public static final String NAME = MultiSiteManager.FALLBACK_SITE_NAME;
}
```

### Site

```java
import info.magnolia.module.site.Domain;
import info.magnolia.repository.RepositoryConstants;

import com.merkle.oss.magnolia.sitebuilder.DomainMapper;
import com.merkle.oss.magnolia.sitebuilder.DomainPredicate;
import com.merkle.oss.magnolia.sitebuilder.annotation.Extends;
import com.merkle.oss.magnolia.sitebuilder.annotation.Site;
import com.merkle.powerboost.web.core.PowerboostCoreTemplatingConstants;
import com.merkle.powerboost.web.core.sites.ContextDomainMapper;
import com.merkle.powerboost.web.core.sites.EnvironmentDomainPredicate;
import com.merkle.powerboost.web.core.sites.FallbackSite;

@Extends(FallbackSite.class)
@Site(
        id = "SomeApp:sites/" + SomeSite.NAME,
        name = SomeSite.NAME,
        mappings = {
                @Site.Mapping(workspace = RepositoryConstants.WEBSITE, handlePrefix = SomeSite.HANDLE_PREFIX)
        },
        domains = {
                @Site.Domain(name = "somesite.localdev.me", protocol = "http", port = 8080),
                @Site.Domain(name = "env1.somesite.domain.com", protocol = "https", port = 443, context = "/magnolia"),
                @Site.Domain(name = "env2.somesite.domain.com", protocol = "https", port = 443, context = "/magnolia", predicate = SomePredicate.class),
                @Site.Domain(name = "env3.somesite.domain.com", protocol = "https", port = 443, context = "/magnolia", mapper = SomeMapper.class),
        }
)
public class SomeSite {
    public static final String NAME = "powerboost";
    public static final String HANDLE_PREFIX = "/powerboost";

    public static class SomePredicate implements DomainPredicate {
        @Override
        public boolean test(final Domain domain) {
            //TODO filter
            return true;
        }
    }

    public static class SomeMapper implements DomainMapper {
        @Override
        public Domain apply(final Domain input) {
            //TODO map
            return input;
        }
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
import info.magnolia.module.site.Site;

public class CustomSiteDefinitionDecorator implements DefinitionDecorator<Site> {
    @Override
    public DefinitionDecoratorMetadata metadata() {
        return () -> "someModule";
    }

    @Override
    public boolean appliesTo(final DefinitionProvider<Site> definitionProvider) {
        //TODO implement
        return true;
    }

    @Override
    public DefinitionProvider<Site> decorate(final DefinitionProvider<Site> definitionProvider) {
        return new DefinitionProviderWrapper<>(definitionProvider) {
            @Override
            public Site get() throws Registry.InvalidDefinitionException {
                //TODO deorate definition
                return definitionProvider.get();
            }
        };
    }
}
```

Override the SiteDefinitionProvider.Factory and pass your custom decorator to SiteDefinitionProvider:

```java
import info.magnolia.objectfactory.Components;
import info.magnolia.rendering.template.configured.ConfiguredAreaDefinition;
import info.magnolia.rendering.template.registry.TemplateDefinitionRegistry;

import java.util.List;
import java.util.Set;

import javax.inject.Inject;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.merkle.oss.magnolia.sitebuilder.SiteDefinitionProvider;

public class CustomSiteDefinitionProviderFactory extends SiteDefinitionProvider.Factory {
    private final TemplateDefinitionRegistry templateDefinitionRegistry;
    private final CustomSiteDefinitionDecorator customSiteDefinitionDecorator;

    @Inject
    public CustomTemplateDefinitionProviderFactory(
            final TemplateDefinitionRegistry templateDefinitionRegistry,
            final CustomSiteDefinitionDecorator customSiteDefinitionDecorator
    ) {
        super(templateDefinitionRegistry);
        this.templateDefinitionRegistry = templateDefinitionRegistry;
        this.customSiteDefinitionDecorator = customSiteDefinitionDecorator;
    }

    public SiteDefinitionProvider create(final Class<?> factoryClass) {
        return new SiteDefinitionProvider(
                List.of(customSiteDefinitionDecorator),
                templateDefinitionRegistry,
                Components.getComponentProvider(),
                factoryClass
        );
    }
}
```

bind custom factory:
```xml
<component>
    <type>com.merkle.oss.magnolia.sitebuilder.SiteDefinitionProvider$Factory</type>
    <implementation>com.somepackage.CustomSiteDefinitionProviderFactory</implementation>
</component>
```