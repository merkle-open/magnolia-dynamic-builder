package com.merkle.oss.magnolia.appbuilder;

import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.Registry;
import info.magnolia.config.registry.decoration.DefinitionDecorator;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.chooser.definition.AppAwareWorkbenchChooserDefinition;
import info.magnolia.ui.chooser.definition.SingleItemWorkbenchChooserDefinition;
import info.magnolia.ui.contentapp.configuration.ContentViewDefinition;
import info.magnolia.ui.contentapp.configuration.WorkbenchDefinition;
import info.magnolia.ui.dialog.DefinitionTypes;
import info.magnolia.ui.dialog.DialogDefinition;
import info.magnolia.ui.filteringapp.filter.FilterView;
import info.magnolia.ui.filteringapp.filter.FilterViewDefinition;

import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.merkle.oss.magnolia.appbuilder.annotations.ChooserDialogFactory;
import com.merkle.oss.magnolia.builder.AbstractDynamicDefinitionProvider;
import com.merkle.oss.magnolia.builder.DynamicDefinitionMetaData;
import com.merkle.oss.magnolia.builder.annotation.TernaryBoolean;
import com.merkle.oss.magnolia.definition.builder.contentapp.contentview.WorkbenchDefinitionBuilder;

import jakarta.inject.Provider;

public class ChooserDialogDefinitionProvider<T> extends AbstractDynamicDefinitionProvider<DialogDefinition> {
    private final DefinitionMetadata metadata;
    private final Provider<Object> factoryObjectProvider;
    private final ChooserDialogFactory annotation;

    public ChooserDialogDefinitionProvider(
            final List<DefinitionDecorator<DialogDefinition>> decorators,
            final Provider<Object> factoryObjectProvider,
            final Class<?> factoryClass
    ) {
        super(decorators);
        this.factoryObjectProvider = factoryObjectProvider;
        this.annotation = factoryClass.getAnnotation(ChooserDialogFactory.class);
        this.metadata = new DynamicDefinitionMetaData.Builder(factoryClass, annotation.id())
                .type(DefinitionTypes.DIALOG)
                .build();
    }

    @Override
    public DefinitionMetadata getMetadata() {
        return metadata;
    }

    @Override
    public DialogDefinition getInternal() throws Registry.InvalidDefinitionException {
        final SingleItemWorkbenchChooserDefinition<T> chooser = new SingleItemWorkbenchChooserDefinition<>();
        chooser.setId(annotation.id());
        chooser.setLabel(annotation.label());
        try {
            final AppAwareWorkbenchChooserDefinition<T> appAwareWorkbenchChooser = new AppAwareWorkbenchChooserDefinition<>();
            final Object factoryObject = factoryObjectProvider.get();
            appAwareWorkbenchChooser.setWorkbench(getWorkbench(factoryObject, annotation));
            chooser.setWorkbenchChooser(appAwareWorkbenchChooser);
        } catch (Exception e) {
            addProblem(e);
        }
        return chooser;
    }

    private WorkbenchDefinition<T> getWorkbench(final Object factoryObject, final ChooserDialogFactory annotation) {
        final WorkbenchDefinitionBuilder<T> workbenchBuilder = new WorkbenchDefinitionBuilder<T>()
                .contentViews(getContentViews(factoryObject))
                .filters(getFilters(factoryObject));

        if(annotation.hasSearchBar() != TernaryBoolean.UNSPECIFIED) {
            workbenchBuilder.searchEnabled(annotation.hasSearchBar() == TernaryBoolean.TRUE);
        }
        return workbenchBuilder.build();
    }

    private List<ContentViewDefinition<T>> getContentViews(final Object factoryObject) {
        return streamFromMethods(factoryObject, ContentViewDefinition.class)
                .map(contentViewDefinition -> (ContentViewDefinition<T>)contentViewDefinition)
                .collect(Collectors.toList());
    }

    private List<FilterViewDefinition<? extends FilterView>> getFilters(final Object factoryObject) {
        return streamFromMethods(factoryObject, FilterViewDefinition.class)
                .map(filterViewDefinition -> (FilterViewDefinition<? extends FilterView>) filterViewDefinition)
                .collect(Collectors.toList());
    }

    private <V> Stream<V> streamFromMethods(final Object factoryObject, final Class<V> type) {
        return Arrays.stream(factoryObject.getClass().getMethods())
                .filter(method -> method.getReturnType().isAssignableFrom(type))
                .sorted(Comparator.comparingInt(method ->
                        Optional
                                .ofNullable(method.getAnnotation(ChooserDialogFactory.Order.class))
                                .map(ChooserDialogFactory.Order::value)
                                .orElse(-1)
                ))
                .map(method -> {
                    try {
                        //noinspection unchecked
                        return (V) method.invoke(factoryObject);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        throw new RuntimeException("Could not build " + type + ", for chooser dialog  " + annotation.id() + " " + method.getName(), e);
                    }
                });
    }

    public static class Factory {
        public ChooserDialogDefinitionProvider<?> create(final Class<?> factoryClass) {
            return new ChooserDialogDefinitionProvider<>(Collections.emptyList(), () -> Components.newInstance(factoryClass), factoryClass);
        }
    }
}
