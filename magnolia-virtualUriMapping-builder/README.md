# Magnolia VirtualUriMapping-Builder

The VirtualUriMapping-Builder module is a builder for Magnolia virtualUriMappings in a java.

## Setup

### Add Maven dependency:
```xml
<dependency>
    <groupId>com.merkle.oss.magnolia</groupId>
    <artifactId>magnolia-virtualUriMapping-builder</artifactId>
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
import com.merkle.oss.magnolia.virtualurimappingbuilder.annotation.VirtualUriMapper;
import com.merkle.oss.magnolia.virtualurimappingbuilder.annotation.VirtualUriMappers;

public class GuiceComponentConfigurer extends AbstractGuiceComponentConfigurer {
    @Override
    protected void configure() {
        // Here we use Reflections, but you can also use ClassPathScanningCandidateComponentProvider or bind each factory manually 
        final Multibinder<Class<? extends VirtualUriMapping>> virtualUriMappersMultibinder = Multibinder.newSetBinder(binder(), new TypeLiteral<>() {}, VirtualUriMappers.class);
        new Reflections(getClass()).getTypesAnnotatedWith(VirtualUriMapper.class).forEach(clazz -> virtualUriMappersMultibinder.addBinding().toInstance(clazz));
    }
}
```

## How to use

### VirtualUriMapper
To create a new virtualUriMapper, add a class with the `@VirtualUriMapper` annotation and implement ```info.magnolia.virtualuri.VirtualUriMapping```.


## Example

```java
import info.magnolia.virtualuri.VirtualUriMapping;

import java.net.URI;
import java.util.Optional;

import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import com.merkle.oss.magnolia.virtualurimappingbuilder.annotation.VirtualUriMapper;

@VirtualUriMapper("SomeApp:virtualUriMappings/robots")
public class RobotsTxtMapping implements VirtualUriMapping {
    private static final String ROBOTS_URI = "/robots.txt";
    private static final String ROBOTS_ENDPOINT_URI = "/api/robots.txt";
    private final VirtualUriMappingPredicate virtualUriMappingPredicate;

    @Inject
    public RobotsTxtMapping(final VirtualUriMappingPredicate virtualUriMappingPredicate) {
        this.virtualUriMappingPredicate = virtualUriMappingPredicate;
    }

    @Override
    public Optional<Result> mapUri(final URI uri) {
        if (virtualUriMappingPredicate.test(uri) && StringUtils.equalsIgnoreCase(uri.toString(), ROBOTS_URI)) {
            return Optional.of(new Result(ROBOTS_ENDPOINT_URI, 1, this));
        }
        return Optional.empty();
    }

    @Override
    public boolean isValid() {
        return true;
    }
}
```