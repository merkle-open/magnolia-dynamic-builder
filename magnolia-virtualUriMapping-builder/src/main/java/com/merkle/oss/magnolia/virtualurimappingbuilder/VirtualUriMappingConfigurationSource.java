package com.merkle.oss.magnolia.virtualurimappingbuilder;

import info.magnolia.config.registry.DefinitionProvider;
import info.magnolia.virtualuri.VirtualUriMapping;
import info.magnolia.virtualuri.VirtualUriRegistry;

import java.lang.invoke.MethodHandles;
import java.util.Set;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.merkle.oss.magnolia.builder.AbstractDynamicConfigurationSource;
import com.merkle.oss.magnolia.virtualurimappingbuilder.annotation.VirtualUriMappers;

import jakarta.inject.Inject;

public class VirtualUriMappingConfigurationSource extends AbstractDynamicConfigurationSource<VirtualUriMapping> {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final VirtualUriMappingDefinitionProvider.Factory virtualUriMappingDefinitionProviderFactory;

    @Inject
    public VirtualUriMappingConfigurationSource(
            @VirtualUriMappers final Set<Class<? extends VirtualUriMapping>> virtualUriMappers,
            final VirtualUriMappingDefinitionProvider.Factory virtualUriMappingDefinitionProviderFactory,
            final VirtualUriRegistry registry
    ) {
        super(registry.type(), Set.copyOf(virtualUriMappers));
        this.virtualUriMappingDefinitionProviderFactory = virtualUriMappingDefinitionProviderFactory;
    }

    @Override
    protected Stream<DefinitionProvider<VirtualUriMapping>> definitionProviders(final Class<?> factoryClass) {
        LOG.info("Registered virtualUriMapping '{}' from {}", factoryClass.getSimpleName(), factoryClass.getName());
        return Stream.of(virtualUriMappingDefinitionProviderFactory.create((Class<? extends VirtualUriMapping>)factoryClass));
    }
}
