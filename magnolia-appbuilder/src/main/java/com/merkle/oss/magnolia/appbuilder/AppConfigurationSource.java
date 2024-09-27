package com.merkle.oss.magnolia.appbuilder;

import info.magnolia.config.registry.DefinitionProvider;
import info.magnolia.ui.api.app.AppDescriptor;
import info.magnolia.ui.api.app.registry.DefinitionTypes;

import java.lang.invoke.MethodHandles;
import java.util.Set;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.merkle.oss.magnolia.appbuilder.annotations.AppFactories;
import com.merkle.oss.magnolia.builder.AbstractDynamicConfigurationSource;

public class AppConfigurationSource extends AbstractDynamicConfigurationSource<AppDescriptor> {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final AppDescriptorProvider.Factory appDescriptorProviderFactory;
    private final LegacyAppDescriptorProvider.Factory legacyAppDescriptorProviderFactory;

    @Inject
    public AppConfigurationSource(
            @AppFactories final Set<Class<?>> appFactories,
            final AppDescriptorProvider.Factory appDescriptorProviderFactory,
            final LegacyAppDescriptorProvider.Factory legacyAppDescriptorProviderFactory
    ) {
        super(DefinitionTypes.APP, appFactories);
        this.appDescriptorProviderFactory = appDescriptorProviderFactory;
        this.legacyAppDescriptorProviderFactory = legacyAppDescriptorProviderFactory;
    }

    @Override
    protected Stream<DefinitionProvider<AppDescriptor>> definitionProviders(final Class<?> factoryClass) {
        LOG.info("Registered app '{}' from {}", factoryClass.getSimpleName(), factoryClass.getName());
        return Stream.concat(
                Stream.of(appDescriptorProviderFactory.create(factoryClass)),
                Stream.of(legacyAppDescriptorProviderFactory.create(factoryClass)).filter(LegacyAppDescriptorProvider::shouldRegister)
        );
    }
}
