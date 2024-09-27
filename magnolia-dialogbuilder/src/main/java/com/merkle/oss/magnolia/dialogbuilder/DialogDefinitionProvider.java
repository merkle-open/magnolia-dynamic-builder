package com.merkle.oss.magnolia.dialogbuilder;

import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.Registry;
import info.magnolia.config.registry.decoration.DefinitionDecorator;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.dialog.ConfiguredFormDialogDefinition;
import info.magnolia.ui.dialog.DefinitionTypes;
import info.magnolia.ui.dialog.DialogDefinition;
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
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.annotation.Nullable;
import javax.inject.Inject;
import javax.inject.Provider;
import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;

import com.merkle.oss.magnolia.builder.AbstractDynamicDefinitionProvider;
import com.merkle.oss.magnolia.builder.DynamicDefinitionMetaData;
import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;
import com.merkle.oss.magnolia.dialogbuilder.annotation.DialogFactory;
import com.merkle.oss.magnolia.dialogbuilder.annotation.PostCreate;
import com.merkle.oss.magnolia.dialogbuilder.annotation.TabFactory;
import com.merkle.oss.magnolia.dialogbuilder.annotation.TabOrder;
import com.merkle.oss.magnolia.dialogbuilder.parameter.DialogCreationContext;
import com.merkle.oss.magnolia.dialogbuilder.parameter.DialogParameterResolverFactory;


public class DialogDefinitionProvider extends AbstractDynamicDefinitionProvider<DialogDefinition> {
    private final DialogCreationContext.Factory dialogCreationContextFactory;
    private final DialogParameterResolverFactory parameterResolverFactory;
    private final TabComparatorFactory tabComparatorFactory;
    private final Provider<Object> factoryObjectProvider;
    private final DialogFactory annotation;
    private final DefinitionMetadata metadata;

    public DialogDefinitionProvider(
            final List<DefinitionDecorator<DialogDefinition>> decorators,
            final DialogCreationContext.Factory dialogCreationContextFactory,
            final DialogParameterResolverFactory parameterResolverFactory,
            final TabComparatorFactory tabComparatorFactory,
            final Provider<Object> factoryObjectProvider,
            final Class<?> factoryClass
    ) {
        super(decorators);
        this.dialogCreationContextFactory = dialogCreationContextFactory;
        this.parameterResolverFactory = parameterResolverFactory;
        this.tabComparatorFactory = tabComparatorFactory;
        this.factoryObjectProvider = factoryObjectProvider;
        this.annotation = factoryClass.getAnnotation(DialogFactory.class);
        this.metadata = new DynamicDefinitionMetaData.Builder(factoryClass, annotation.value())
                .type(DefinitionTypes.DIALOG)
                .build();
    }

    @Override
    public DefinitionMetadata getMetadata() {
        return metadata;
    }

    @Override
    public DialogDefinition getInternal() throws Registry.InvalidDefinitionException {
        final Object factoryObject = factoryObjectProvider.get();
        final DialogCreationContext context = dialogCreationContextFactory.create();
        final ConfiguredFormDialogDefinition<Node> dialogDefinition = new ConfiguredFormDialogDefinition<>();
        final ParameterResolver parameterResolver = parameterResolverFactory.create(context, dialogDefinition);
        dialogDefinition.setId(annotation.value());
        dialogDefinition.setLabel(StringUtils.trimToNull(annotation.label()));
        try {
            final ConfiguredFormDefinition<Node> formDefinition = new ConfiguredFormDefinition<>();
            dialogDefinition.setForm(formDefinition);

            @Nullable
            final TabOrder tabOrder = factoryObject.getClass().getAnnotation(TabOrder.class);
            final Map<TabFactory, List<EditorPropertyDefinition>> tabs = getTabs(factoryObject, parameterResolver);
            formDefinition.setProperties(tabs.values().stream().flatMap(Collection::stream).collect(Collectors.toList()));
            formDefinition.setLayout(getLayout(tabOrder, tabs));

            invokePostCreate(factoryObject, parameterResolver);
        } catch (Exception e) {
            addProblem(e);
        }
        return dialogDefinition;
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

    public static class Factory {
        private final DialogCreationContext.Factory dialogCreationContextFactory;
        private final DialogParameterResolverFactory parameterResolverFactory;
        private final TabComparatorFactory tabComparatorFactory;

        @Inject
        public Factory(
                final DialogCreationContext.Factory dialogCreationContextFactory,
                final DialogParameterResolverFactory parameterResolverFactory,
                final TabComparatorFactory tabComparatorFactory
        ) {
            this.dialogCreationContextFactory = dialogCreationContextFactory;
            this.parameterResolverFactory = parameterResolverFactory;
            this.tabComparatorFactory = tabComparatorFactory;
        }

        public DialogDefinitionProvider create(final Class<?> factoryClass) {
            return new DialogDefinitionProvider(
                    Collections.emptyList(),
                    dialogCreationContextFactory,
                    parameterResolverFactory,
                    tabComparatorFactory,
                    () -> Components.newInstance(factoryClass),
                    factoryClass
            );
        }
    }
}
