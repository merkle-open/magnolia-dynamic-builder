package com.merkle.oss.magnolia.sitebuilder;

import info.magnolia.module.ModuleLifecycle;
import info.magnolia.module.ModuleLifecycleContext;
import info.magnolia.module.site.registry.SiteRegistry;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import jakarta.inject.Inject;

public class SiteBuilderModule implements ModuleLifecycle {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final SiteRegistry siteRegistry;
    private final SiteConfigurationSource siteConfigurationSource;

    @Inject
	public SiteBuilderModule(
			final SiteRegistry siteRegistry,
			final SiteConfigurationSource siteConfigurationSource
	) {
        this.siteRegistry = siteRegistry;
        this.siteConfigurationSource = siteConfigurationSource;
    }

	@Override
	public void start(final ModuleLifecycleContext moduleLifecycleContext) {
		LOG.debug("Starting SiteBuilder Module");
		siteRegistry.bindTo(siteConfigurationSource);
		siteConfigurationSource.start();
	}

	@Override
	public void stop(final ModuleLifecycleContext moduleLifecycleContext) {
		LOG.debug("Stopping SiteBuilder Module");
	}
}
