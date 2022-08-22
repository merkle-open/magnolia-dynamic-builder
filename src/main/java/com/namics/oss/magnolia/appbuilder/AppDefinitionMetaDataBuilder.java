package com.namics.oss.magnolia.appbuilder;

import com.namics.oss.magnolia.appbuilder.annotations.AppFactory;
import info.magnolia.config.registry.DefinitionMetadataBuilder;
import info.magnolia.ui.api.app.registry.DefinitionTypes;
import org.apache.commons.lang3.StringUtils;
import org.springframework.aop.support.AopUtils;

public class AppDefinitionMetaDataBuilder extends DefinitionMetadataBuilder {
	private final Object appFactory;

	public AppDefinitionMetaDataBuilder(final Object appFactory) {
		this.appFactory = appFactory;
		type(DefinitionTypes.APP);
		location(appFactory.getClass().getName());
	}

	@Override
	protected String buildReferenceId() {
		final AppFactory appFactoryAnnotation = AopUtils
				.getTargetClass(appFactory)
				.getAnnotation(AppFactory.class);
		final String id = appFactoryAnnotation.id();
		final String module = id.contains(":") ? StringUtils.substringBefore(id, ":") : null;
		final String relativeLocation = id.contains(":") ? StringUtils.substringAfter(id, ":") : id;
		final String name = relativeLocation.indexOf('/') != -1 ? StringUtils.substringAfterLast(relativeLocation, "/") : relativeLocation;
		module(module).relativeLocation(relativeLocation).name(name);
		return name;
	}

}
