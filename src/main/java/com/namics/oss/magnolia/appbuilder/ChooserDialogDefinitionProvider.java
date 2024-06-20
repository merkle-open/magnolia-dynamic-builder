package com.namics.oss.magnolia.appbuilder;

import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.DefinitionProvider;
import info.magnolia.config.registry.DefinitionRawView;
import info.magnolia.config.registry.Registry;
import info.magnolia.config.registry.decoration.DefinitionDecorator;
import info.magnolia.ui.chooser.definition.AppAwareWorkbenchChooserDefinition;
import info.magnolia.ui.chooser.definition.FullTextSearchExtensionViewDefinition;
import info.magnolia.ui.chooser.definition.SingleItemWorkbenchChooserDefinition;
import info.magnolia.ui.contentapp.configuration.ContentViewDefinition;
import info.magnolia.ui.contentapp.configuration.WorkbenchDefinition;
import info.magnolia.ui.dialog.DefinitionTypes;
import info.magnolia.ui.dialog.DialogDefinition;

import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.namics.oss.magnolia.appbuilder.annotations.ChooserDialogFactory;

public class ChooserDialogDefinitionProvider<T> implements DefinitionProvider<DialogDefinition> {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final DefinitionMetadata metadata;
    private final Object factoryObject;
    private final ChooserDialogFactory annotation;

    public ChooserDialogDefinitionProvider(final Object factoryObject) {
        this.factoryObject = factoryObject;
        this.annotation = factoryObject.getClass().getAnnotation(ChooserDialogFactory.class);
        this.metadata = new ExplicitIdDefinitionMetaDataBuilder(factoryObject, annotation.id())
                .type(DefinitionTypes.DIALOG)
                .build();
    }

    @Override
    public List<DefinitionDecorator<DialogDefinition>> getDecorators() {
        return Collections.emptyList();
    }

    @Override
    public DefinitionMetadata getMetadata() {
        return metadata;
    }

    @Override
    public DialogDefinition get() throws Registry.InvalidDefinitionException {
        final AppAwareWorkbenchChooserDefinition<T> appAwareWorkbenchChooser = new AppAwareWorkbenchChooserDefinition<>();
        appAwareWorkbenchChooser.setWorkbench(getWorkbench(factoryObject, annotation));
        final SingleItemWorkbenchChooserDefinition<T> chooser = new SingleItemWorkbenchChooserDefinition<>();
        chooser.setWorkbenchChooser(appAwareWorkbenchChooser);
        chooser.setId(annotation.id());
        chooser.setLabel(annotation.label());
        return chooser;
    }

    @Override
    public DefinitionRawView getRaw() {
        return DefinitionRawView.EMPTY;
    }

    @Override
    public boolean isValid() {
        return true;
    }

    @Override
    public long getLastModified() {
        return System.currentTimeMillis();
    }

    private WorkbenchDefinition<T> getWorkbench(final Object factoryObject, final ChooserDialogFactory annotation) {
        final WorkbenchDefinition<T> workbench = new WorkbenchDefinition<>();
        workbench.setContentViews(getContentViews(factoryObject, annotation));
        if(annotation.hasFullTextSearch()) {
            workbench.setExtensionViews(List.of(new FullTextSearchExtensionViewDefinition()));
        }
        return workbench;
    }

    private List<ContentViewDefinition<T>> getContentViews(final Object factoryObject, final ChooserDialogFactory annotation) {
        return Arrays.stream(factoryObject.getClass().getDeclaredMethods())
                .filter(method -> method.getReturnType().isAssignableFrom(ContentViewDefinition.class))
                .sorted(Comparator.comparingInt(method ->
                        Optional
                                .ofNullable(method.getAnnotation(ChooserDialogFactory.Order.class))
                                .map(ChooserDialogFactory.Order::value)
                                .orElse(-1)
                ))
                .map(method -> {
                    try {
                        //noinspection unchecked
                        return (ContentViewDefinition<T>) method.invoke(factoryObject);
                    } catch (IllegalAccessException | InvocationTargetException e) {
                        LOG.error("Could not build column, for chooser dialog  " + annotation.id() + " " + method.getName(), e);
                        throw new Registry.InvalidDefinitionException(annotation.id());
                    }
                })
                .collect(Collectors.toList());
    }
}
