package com.merkle.oss.magnolia.builder;

import static com.google.common.base.CaseFormat.*;

import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.DefinitionProvider;
import info.magnolia.config.registry.DefinitionType;
import info.magnolia.config.registry.RegistryMap;
import info.magnolia.config.registry.decoration.DefinitionDecorator;
import info.magnolia.config.source.ConfigurationSource;
import info.magnolia.config.source.ConfigurationSourceType;
import info.magnolia.config.source.ConfigurationSourceTypes;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractDynamicConfigurationSource<T> implements ConfigurationSource<T> {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final RegistryMap<T> registryMap = new RegistryMap<>();
    private final DefinitionType type;
    protected final Set<Class<?>> factories;

    public AbstractDynamicConfigurationSource(
            final DefinitionType type,
            final Set<Class<?>> factories
    ) {
        this.type = type;
        this.factories = factories;
    }

    @Override
    public ConfigurationSourceType type() {
        return ConfigurationSourceTypes.code;
    }

    @Override
    public DefinitionType getDefinitionType() {
        return type;
    }

    @Override
    public void start() {
        LOG.info("Setting up {} for {} definitions", getClass().getSimpleName(), LOWER_CAMEL.to(LOWER_HYPHEN, getDefinitionType().getName()));
        registryMap.removeAndPutAll(
                registryMap.keySet(),
                factories.stream()
                        .flatMap(this::definitionProviders)
                        .map(this::decorate)
                        .collect(Collectors.toSet())
        );
    }

    private DefinitionProvider<T> decorate(final DefinitionProvider<T> definitionProvider) {
        DefinitionProvider<T> result = definitionProvider;
        for (DefinitionDecorator<T> decorator : definitionProvider.getDecorators()) {
            if(decorator.appliesTo(result)) {
                result = decorator.decorate(result);
            }
        }
        return result;
    }

    protected abstract Stream<DefinitionProvider<T>> definitionProviders(Class<?> factoryClass);

    @Override
    public DefinitionProvider<T> getProvider(final DefinitionMetadata id) {
        return registryMap.get(id);
    }

    @Override
    public DefinitionProvider<T> getProvider(final String id) {
        return registryMap.getByStringKey(id);
    }

    @Override
    public Collection<DefinitionMetadata> getAllMetadata() {
        return registryMap.keySet();
    }

    @Override
    public Collection<DefinitionProvider<T>> getAllProviders() {
        return registryMap.values();
    }
}
