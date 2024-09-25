package com.namics.oss.magnolia.appbuilder;

import static com.google.common.base.CaseFormat.*;

import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.DefinitionProvider;
import info.magnolia.config.registry.DefinitionType;
import info.magnolia.config.registry.RegistryMap;
import info.magnolia.config.source.ConfigurationSource;
import info.magnolia.config.source.ConfigurationSourceType;
import info.magnolia.config.source.ConfigurationSourceTypes;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.api.app.AppDescriptor;
import info.magnolia.ui.api.app.registry.DefinitionTypes;

import java.lang.invoke.MethodHandles;
import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.namics.oss.magnolia.appbuilder.annotations.AppFactories;

public class AppConfigurationSource implements ConfigurationSource<AppDescriptor> {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final RegistryMap<AppDescriptor> registryMap = new RegistryMap<>();
    private final Set<Class<?>> appFactories;
    private final LegacyAppDescriptorProvider.ColumnDefinitionFilter legacyAppColumnDefinitionFilter;

    @Inject
    public AppConfigurationSource(
            @AppFactories final Set<Class<?>> appFactories,
            final LegacyAppDescriptorProvider.ColumnDefinitionFilter legacyAppColumnDefinitionFilter
    ) {
        this.appFactories = appFactories;
        this.legacyAppColumnDefinitionFilter = legacyAppColumnDefinitionFilter;
    }

    @Override
    public ConfigurationSourceType type() {
        return ConfigurationSourceTypes.code;
    }

    @Override
    public DefinitionType getDefinitionType() {
        return DefinitionTypes.APP;
    }

    @Override
    public void start() {
        LOG.info("Setting up {} for {} definitions", getClass().getSimpleName(), LOWER_CAMEL.to(LOWER_HYPHEN, getDefinitionType().getName()));
        registryMap.removeAndPutAll(
                registryMap.keySet(),
                appFactories.stream()
                        .flatMap(this::definitionProviders)
                        .collect(Collectors.toSet())
        );
    }

    private Stream<DefinitionProvider<AppDescriptor>> definitionProviders(final Class<?> factoryClass) {
        LOG.info("Registered app '{}' from {}", factoryClass.getSimpleName(), factoryClass.getName());
        final Object factory = Components.newInstance(factoryClass);
        return Stream.concat(
                Stream.of(new AppDescriptorProvider(factory)),
                Stream.of(new LegacyAppDescriptorProvider(factory, legacyAppColumnDefinitionFilter)).filter(LegacyAppDescriptorProvider::shouldRegister)
        );
    }

    @Override
    public DefinitionProvider<AppDescriptor> getProvider(final DefinitionMetadata id) {
        return registryMap.get(id);
    }

    @Override
    public DefinitionProvider<AppDescriptor> getProvider(final String id) {
        return registryMap.getByStringKey(id);
    }

    @Override
    public Collection<DefinitionMetadata> getAllMetadata() {
        return registryMap.keySet();
    }

    @Override
    public Collection<DefinitionProvider<AppDescriptor>> getAllProviders() {
        return registryMap.values();
    }
}
