package com.merkle.oss.magnolia.templatebuilder;

import info.magnolia.config.registry.DefinitionProvider;
import info.magnolia.rendering.DefinitionTypes;
import info.magnolia.rendering.template.TemplateDefinition;

import java.lang.invoke.MethodHandles;
import java.util.Set;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.merkle.oss.magnolia.builder.AbstractDynamicConfigurationSource;
import com.merkle.oss.magnolia.templatebuilder.annotation.TemplateFactories;

import jakarta.inject.Inject;

public class TemplateConfigurationSource extends AbstractDynamicConfigurationSource<TemplateDefinition> {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final TemplateDefinitionProvider.Factory templateDefinitionProviderFactory;

    @Inject
    public TemplateConfigurationSource(
            @TemplateFactories final Set<Class<?>> templateFactories,
            final TemplateDefinitionProvider.Factory templateDefinitionProviderFactory
    ) {
        super(DefinitionTypes.TEMPLATE, templateFactories);
        this.templateDefinitionProviderFactory = templateDefinitionProviderFactory;
    }

    @Override
    protected Stream<DefinitionProvider<TemplateDefinition>> definitionProviders(final Class<?> factoryClass) {
        LOG.info("Registered template '{}' from {}", factoryClass.getSimpleName(), factoryClass.getName());
        return Stream.of(templateDefinitionProviderFactory.create(factories, factoryClass));
    }
}
