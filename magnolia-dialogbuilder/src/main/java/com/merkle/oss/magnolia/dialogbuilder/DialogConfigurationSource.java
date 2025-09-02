package com.merkle.oss.magnolia.dialogbuilder;

import info.magnolia.config.registry.DefinitionProvider;
import info.magnolia.ui.dialog.DefinitionTypes;
import info.magnolia.ui.dialog.DialogDefinition;

import java.lang.invoke.MethodHandles;
import java.util.Set;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.merkle.oss.magnolia.builder.AbstractDynamicConfigurationSource;
import com.merkle.oss.magnolia.dialogbuilder.annotation.DialogFactories;

import jakarta.inject.Inject;

public class DialogConfigurationSource extends AbstractDynamicConfigurationSource<DialogDefinition> {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final DialogDefinitionProvider.Factory dialogDefinitionProviderFactory;

    @Inject
    public DialogConfigurationSource(
            @DialogFactories final Set<Class<?>> dialogFactories,
            final DialogDefinitionProvider.Factory dialogDefinitionProviderFactory
    ) {
        super(DefinitionTypes.DIALOG, dialogFactories);
        this.dialogDefinitionProviderFactory = dialogDefinitionProviderFactory;
    }

    @Override
    protected Stream<DefinitionProvider<DialogDefinition>> definitionProviders(final Class<?> factoryClass) {
        LOG.info("Registered dialog '{}' from {}", factoryClass.getSimpleName(), factoryClass.getName());
        return Stream.of(dialogDefinitionProviderFactory.create(factoryClass));
    }
}
