package com.merkle.oss.magnolia.sitebuilder;

import info.magnolia.config.registry.DefinitionProvider;
import info.magnolia.module.site.Site;
import info.magnolia.module.site.registry.DefinitionTypes;

import java.lang.invoke.MethodHandles;
import java.util.Set;
import java.util.stream.Stream;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.merkle.oss.magnolia.builder.AbstractDynamicConfigurationSource;
import com.merkle.oss.magnolia.sitebuilder.annotation.SiteFactories;

public class SiteConfigurationSource extends AbstractDynamicConfigurationSource<Site> {
    private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final SiteDefinitionProvider.Factory siteDefinitionProviderFactory;

    @Inject
    public SiteConfigurationSource(
            @SiteFactories final Set<Class<?>> siteFactories,
            final SiteDefinitionProvider.Factory siteDefinitionProviderFactory
    ) {
        super(DefinitionTypes.SITE, siteFactories);
        this.siteDefinitionProviderFactory = siteDefinitionProviderFactory;
    }

    @Override
    protected Stream<DefinitionProvider<Site>> definitionProviders(final Class<?> factoryClass) {
        LOG.info("Registered site '{}' from {}", factoryClass.getSimpleName(), factoryClass.getName());
        return Stream.of(siteDefinitionProviderFactory.create(factoryClass));
    }
}
