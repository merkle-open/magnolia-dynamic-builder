package com.merkle.oss.magnolia.virtualurimappingbuilder;

import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.Registry;
import info.magnolia.config.registry.decoration.DefinitionDecorator;
import info.magnolia.objectfactory.Components;
import info.magnolia.virtualuri.VirtualUriMapping;
import info.magnolia.virtualuri.VirtualUriRegistry;

import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Provider;

import com.merkle.oss.magnolia.builder.AbstractDynamicDefinitionProvider;
import com.merkle.oss.magnolia.builder.DynamicDefinitionMetaData;
import com.merkle.oss.magnolia.virtualurimappingbuilder.annotation.VirtualUriMapper;

public class VirtualUriMappingDefinitionProvider extends AbstractDynamicDefinitionProvider<VirtualUriMapping> {
    private final Provider<VirtualUriMapping> virtualUriMappingProvider;
    private final DefinitionMetadata metadata;

    public VirtualUriMappingDefinitionProvider(
            final List<DefinitionDecorator<VirtualUriMapping>> decorators,
            final VirtualUriRegistry registry,
            final Provider<VirtualUriMapping> virtualUriMappingProvider,
            final Class<? extends VirtualUriMapping> virtualUriMappingClass
    ) {
        super(decorators);
        this.virtualUriMappingProvider = virtualUriMappingProvider;
        final VirtualUriMapper annotation = virtualUriMappingClass.getAnnotation(VirtualUriMapper.class);
        this.metadata = new DynamicDefinitionMetaData.Builder(virtualUriMappingClass, annotation.value())
                .type(registry.type())
                .build();
    }

    @Override
    public DefinitionMetadata getMetadata() {
        return metadata;
    }

    @Override
    public VirtualUriMapping getInternal() throws Registry.InvalidDefinitionException {
        return virtualUriMappingProvider.get();
    }

    public static class Factory {
        private final VirtualUriRegistry registry;

        @Inject
        public Factory(final VirtualUriRegistry registry) {
            this.registry = registry;
        }

        public VirtualUriMappingDefinitionProvider create(final Class<? extends VirtualUriMapping> virtualUriMappingClass) {
            return new VirtualUriMappingDefinitionProvider(
                    Collections.emptyList(),
                    registry,
                    () -> Components.newInstance(virtualUriMappingClass),
                    virtualUriMappingClass
            );
        }
    }
}
