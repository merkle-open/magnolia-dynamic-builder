package com.namics.oss.magnolia.appbuilder;

import info.magnolia.config.registry.DefinitionMetadataBuilder;
import info.magnolia.config.source.ConfigurationSourceTypes;

import org.apache.commons.lang3.StringUtils;

public class ExplicitIdDefinitionMetaDataBuilder extends DefinitionMetadataBuilder {
	private final String id;

	public ExplicitIdDefinitionMetaDataBuilder(final Object objectFactory, final String id) {
		this.id = id;
		location(generateLocation(objectFactory));
		module(generateModule(id));
		relativeLocation(generateRelativeLocation(id));
		configurationSourceType(ConfigurationSourceTypes.code);
		name(generateName(id));
	}

	@Override
	protected String buildReferenceId() {
		return id;
	}

	protected String generateLocation(final Object appFactory) {
		return appFactory.getClass().getName();
	}

	protected String generateModule(final String id) {
		return id.contains(":") ? StringUtils.substringBefore(id, ":") : null;
	}

	protected String generateRelativeLocation(final String id) {
		return id.contains(":") ? StringUtils.substringAfter(id, ":") : id;
	}

	protected String generateName(final String id) {
		final String relativeLocation = generateRelativeLocation(id);
		return relativeLocation.indexOf('/') != -1 ? StringUtils.substringAfterLast(relativeLocation, "/") : relativeLocation;
	}
}
