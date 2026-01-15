package com.merkle.oss.magnolia.formbuilder;

import info.magnolia.ui.editor.ConfiguredFormDefinition;
import info.magnolia.ui.field.ConfiguredNamedFieldDefinition;
import info.magnolia.ui.field.EditorPropertyDefinition;
import info.magnolia.ui.framework.layout.ConfiguredTabDefinition;
import info.magnolia.ui.framework.layout.FieldLayoutDefinition;
import info.magnolia.ui.framework.layout.SingleTabLayoutProducer;
import info.magnolia.ui.framework.layout.TabbedLayoutDefinition;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collection;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.jcr.Item;
import javax.jcr.Node;

import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;
import com.merkle.oss.magnolia.formbuilder.annotation.PostCreate;
import com.merkle.oss.magnolia.formbuilder.annotation.TabFactory;
import com.merkle.oss.magnolia.formbuilder.annotation.TabOrder;
import com.merkle.oss.magnolia.formbuilder.parameter.FormParameterResolverFactory;

import jakarta.annotation.Nullable;
import jakarta.inject.Provider;

public class FormFactory {
    private final FormParameterResolverFactory parameterResolverFactory;
    private final TabComparatorFactory tabComparatorFactory;
    private final Provider<Optional<Node>> nodeProvider;

    public FormFactory(
            final FormParameterResolverFactory parameterResolverFactory,
            final TabComparatorFactory tabComparatorFactory,
            final Provider<Optional<Node>> nodeProvider
    ) {
        this.nodeProvider = nodeProvider;
        this.parameterResolverFactory = parameterResolverFactory;
        this.tabComparatorFactory = tabComparatorFactory;
    }

    public ConfiguredFormDefinition<Item> create(final Object factoryObject) {
        final RootFormView.Definition<Item> formDefinition = new RootFormView.Definition<>();
        final ParameterResolver parameterResolver = parameterResolverFactory.create(new FormParameterResolverFactory.FormCreationContext(nodeProvider), formDefinition);

        @Nullable
        final TabOrder tabOrder = factoryObject.getClass().getAnnotation(TabOrder.class);
        final Map<TabFactory, List<EditorPropertyDefinition>> tabs = getTabs(factoryObject, parameterResolver);
        formDefinition.setProperties(tabs.values().stream().flatMap(Collection::stream).collect(Collectors.toList()));
        formDefinition.setLayout(getLayout(tabOrder, tabs));

        invokePostCreate(factoryObject, parameterResolver);
        return formDefinition;
    }

    private FieldLayoutDefinition<?> getLayout(@Nullable final TabOrder tabOrder, final Map<TabFactory, List<EditorPropertyDefinition>> tabs) {
        if (tabs.isEmpty()) {
            throw new IllegalArgumentException("Dialog has no @TabFactory methods!");
        }
        if (tabs.size() == 1) {
            return new SingleTabLayoutProducer.Definition();
        }
        final TabbedLayoutDefinition tabbedLayoutDefinition = new TabbedLayoutDefinition();
        tabbedLayoutDefinition.setTabs(tabs.entrySet().stream().map(entry ->
                getTab(entry.getKey(), entry.getValue())
        ).sorted(tabComparatorFactory.create(tabOrder)).collect(Collectors.toList()));
        return tabbedLayoutDefinition;
    }

    private ConfiguredTabDefinition getTab(final TabFactory annotation, final List<EditorPropertyDefinition> properties) {
        final ConfiguredTabDefinition tabDefinition = new ConfiguredTabDefinition();
        tabDefinition.setName(annotation.value());
        tabDefinition.setFields(properties.stream().map(property -> {
            final ConfiguredNamedFieldDefinition namedFieldDefinition = new ConfiguredNamedFieldDefinition();
            namedFieldDefinition.setName(property.getName());
            return namedFieldDefinition;
        }).collect(Collectors.toList()));
        return tabDefinition;
    }

    private Map<TabFactory, List<EditorPropertyDefinition>> getTabs(final Object factoryObject, final ParameterResolver parameterResolver) {
        return streamMethods(factoryObject, TabFactory.class)
                .filter(method -> method.getReturnType().isAssignableFrom(List.class))
                .collect(Collectors.toMap(
                        method -> method.getAnnotation(TabFactory.class),
                        method -> (List<EditorPropertyDefinition>) invoke(factoryObject, method, parameterResolver)
                ));
    }

    private void invokePostCreate(final Object factoryObject, final ParameterResolver parameterResolver) {
        streamMethods(factoryObject, PostCreate.class).forEach(method ->
                invoke(factoryObject, method, parameterResolver)
        );
    }

    private Object invoke(final Object factoryObject, final Method method, final ParameterResolver resolver) {
        final Object[] parameters = Arrays
                .stream(method.getParameterTypes())
                .map(resolver::resolveParameter)
                .toArray();
        try {
            return method.invoke(factoryObject, parameters);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new RuntimeException("Could not invoke " + method.getName() + ", for dialog " + factoryObject.getClass(), e);
        }
    }

    private Stream<Method> streamMethods(final Object factoryObject, final Class<? extends Annotation> annotationClass) {
        return Arrays
                .stream(factoryObject.getClass().getMethods())
                .filter(method -> method.isAnnotationPresent(annotationClass) && !Modifier.isStatic(method.getModifiers()));
    }

    public interface TabComparatorFactory {
        Comparator<ConfiguredTabDefinition> create(@Nullable TabOrder tabOrder);
    }

    public interface Factory {
        FormFactory create(FormParameterResolverFactory parameterResolverFactory, TabComparatorFactory tabComparatorFactory, Provider<Optional<Node>> nodeProvider);

        class DefaultFactory implements Factory {
            @Override
            public FormFactory create(final FormParameterResolverFactory parameterResolverFactory, final TabComparatorFactory tabComparatorFactory, final Provider<Optional<Node>> nodeProvider) {
                return new FormFactory(parameterResolverFactory, tabComparatorFactory, nodeProvider);
            }
        }
    }
}
