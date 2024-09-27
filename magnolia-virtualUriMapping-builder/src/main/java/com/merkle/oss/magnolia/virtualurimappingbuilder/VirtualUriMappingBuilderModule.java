package com.merkle.oss.magnolia.virtualurimappingbuilder;

import info.magnolia.module.ModuleLifecycle;
import info.magnolia.module.ModuleLifecycleContext;
import info.magnolia.virtualuri.VirtualUriRegistry;

import java.lang.invoke.MethodHandles;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class VirtualUriMappingBuilderModule implements ModuleLifecycle {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
    private final VirtualUriRegistry virtualUriRegistry;
    private final VirtualUriMappingConfigurationSource virtualUriMappingConfigurationSource;

    @Inject
	public VirtualUriMappingBuilderModule(
			final VirtualUriRegistry virtualUriRegistry,
			final VirtualUriMappingConfigurationSource virtualUriMappingConfigurationSource
	) {
        this.virtualUriRegistry = virtualUriRegistry;
        this.virtualUriMappingConfigurationSource = virtualUriMappingConfigurationSource;
    }

	@Override
	public void start(ModuleLifecycleContext moduleLifecycleContext) {
		LOG.debug("Starting VirtualUriMappingBuilder Module");
		virtualUriRegistry.bindTo(virtualUriMappingConfigurationSource);
		virtualUriMappingConfigurationSource.start();
	}

	@Override
	public void stop(ModuleLifecycleContext moduleLifecycleContext) {
		LOG.debug("Stopping VirtualUriMappingBuilder Module");
	}
}
