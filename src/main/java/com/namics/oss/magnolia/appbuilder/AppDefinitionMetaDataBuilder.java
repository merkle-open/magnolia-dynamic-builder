package com.namics.oss.magnolia.appbuilder;

import info.magnolia.config.registry.DefinitionMetadataBuilder;
import org.apache.commons.lang3.StringUtils;

public class AppDefinitionMetaDataBuilder extends DefinitionMetadataBuilder {
	private String referenceId;

	public AppDefinitionMetaDataBuilder(String id) {
		String module = id.contains(":") ? StringUtils.substringBefore(id, ":") : null;
		String relativeLocation = id.contains(":") ? StringUtils.substringAfter(id, ":") : id;
		String name = relativeLocation.indexOf('/') != -1 ? StringUtils.substringAfterLast(relativeLocation, "/") : relativeLocation;
		module(module).relativeLocation(relativeLocation).name(name);
		this.referenceId = name;
	}

	@Override
	protected String buildReferenceId() {
		return referenceId;
	}

}
