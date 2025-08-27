package com.merkle.oss.magnolia.dialogbuilder;

import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.Registry;
import info.magnolia.config.registry.decoration.DefinitionDecorator;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.dialog.ConfiguredFormDialogDefinition;
import info.magnolia.ui.dialog.DefinitionTypes;
import info.magnolia.ui.dialog.DialogDefinition;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;
import javax.jcr.Item;

import org.apache.commons.lang3.StringUtils;

import com.merkle.oss.magnolia.builder.AbstractDynamicDefinitionProvider;
import com.merkle.oss.magnolia.builder.DynamicDefinitionMetaData;
import com.merkle.oss.magnolia.dialogbuilder.annotation.DialogFactory;
import com.merkle.oss.magnolia.dialogbuilder.parameter.DialogParameterResolverFactory;
import com.merkle.oss.magnolia.dialogbuilder.parameter.SubAppContextBeanStoreNodeProvider;
import com.merkle.oss.magnolia.formbuilder.FormFactory;
import com.merkle.oss.magnolia.formbuilder.FormFactory.TabComparatorFactory;


public class DialogDefinitionProvider extends AbstractDynamicDefinitionProvider<DialogDefinition> {
    private final DialogParameterResolverFactory parameterResolverFactory;
    private final TabComparatorFactory tabComparatorFactory;
    private final Provider<Object> factoryObjectProvider;
    private final DialogFactory annotation;
    private final DefinitionMetadata metadata;

    public DialogDefinitionProvider(
            final List<DefinitionDecorator<DialogDefinition>> decorators,
            final DialogParameterResolverFactory parameterResolverFactory,
            final TabComparatorFactory tabComparatorFactory,
            final Provider<Object> factoryObjectProvider,
            final Class<?> factoryClass
    ) {
        super(decorators);
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
        final ConfiguredFormDialogDefinition<Item> dialogDefinition = new ConfiguredFormDialogDefinition<>();
        dialogDefinition.setId(annotation.value());
        dialogDefinition.setLabel(StringUtils.trimToNull(annotation.label()));
        try {
            final FormFactory formFactory = new FormFactory(
                    (context, formDefinition) -> parameterResolverFactory.create(context, formDefinition, dialogDefinition),
                    tabComparatorFactory,
                    new SubAppContextBeanStoreNodeProvider()
            );
            dialogDefinition.setForm(formFactory.create(factoryObject));
        } catch (Exception e) {
            addProblem(e);
        }
        return dialogDefinition;
    }

    public static class Factory {
        private final DialogParameterResolverFactory parameterResolverFactory;
        private final TabComparatorFactory tabComparatorFactory;

        @Inject
        public Factory(
                final DialogParameterResolverFactory parameterResolverFactory,
                final TabComparatorFactory tabComparatorFactory
        ) {
            this.parameterResolverFactory = parameterResolverFactory;
            this.tabComparatorFactory = tabComparatorFactory;
        }

        public DialogDefinitionProvider create(final Class<?> factoryClass) {
            return new DialogDefinitionProvider(
                    Collections.emptyList(),
                    parameterResolverFactory,
                    tabComparatorFactory,
                    () -> Components.newInstance(factoryClass),
                    factoryClass
            );
        }
    }
}
