package com.namics.oss.magnolia.appbuilder;

import com.namics.oss.magnolia.appbuilder.annotations.AppFactory;
import com.namics.oss.magnolia.appbuilder.annotations.AppPermissions;
import com.namics.oss.magnolia.appbuilder.annotations.SubApp;
import info.magnolia.cms.security.operations.AccessDefinition;
import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.DefinitionProvider;
import info.magnolia.config.registry.DefinitionRawView;
import info.magnolia.config.registry.Registry;
import info.magnolia.config.registry.decoration.DefinitionDecorator;
import info.magnolia.ui.api.app.AppDescriptor;
import info.magnolia.ui.api.app.SubAppDescriptor;
import info.magnolia.ui.contentapp.ContentApp;
import info.magnolia.ui.contentapp.configuration.ContentAppDescriptor;
import info.magnolia.ui.datasource.DatasourceDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class AppDescriptorProvider implements DefinitionProvider<AppDescriptor> {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private final ContentAppDescriptor<DatasourceDefinition> appDescriptor;
	private final DefinitionMetadata metadata;

	public AppDescriptorProvider(final Object appFactory) {
		metadata = new AppDefinitionMetaDataBuilder(appFactory).build();

		final AppFactory appFactoryAnnotation = AopUtils
				.getTargetClass(appFactory)
				.getAnnotation(AppFactory.class);

		appDescriptor = new ContentAppDescriptor<>();
		appDescriptor.setAppClass(ContentApp.class);
		appDescriptor.setName(appFactoryAnnotation.name());
		appDescriptor.setEnabled(appFactoryAnnotation.isEnabled());
		appDescriptor.setIcon(appFactoryAnnotation.icon());
		appDescriptor.setTheme(appFactoryAnnotation.theme());
		appDescriptor.setLabel(appFactoryAnnotation.label());
		appDescriptor.setI18nBasename(appFactoryAnnotation.i18nBasename());
		appDescriptor.setSubApps(getSubApps(appFactory, appFactoryAnnotation));
		getPermissions(appFactory, appFactoryAnnotation).ifPresent(appDescriptor::setPermissions);
	}

	@Override
	public List<DefinitionDecorator<AppDescriptor>> getDecorators() {
		return Collections.emptyList();
	}

	@Override
	public DefinitionMetadata getMetadata() {
		return metadata;
	}

	@Override
	public AppDescriptor get() throws Registry.InvalidDefinitionException {
		return appDescriptor;
	}

	@Override
	public DefinitionRawView getRaw() {
		return DefinitionRawView.EMPTY;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public long getLastModified() {
		return System.currentTimeMillis();
	}

	private Map<String, SubAppDescriptor> getSubApps(final Object appFactory, final AppFactory appFactoryAnnotation) {
		return getDefinitions(appFactory, appFactoryAnnotation, SubApp.class, SubAppDescriptor.class).collect(Collectors.toMap(
				SubAppDescriptor::getName,
				Function.identity()
		));
	}

	private Optional<AccessDefinition> getPermissions(final Object appFactory, final AppFactory appFactoryAnnotation) {
		return getDefinitions(appFactory, appFactoryAnnotation, AppPermissions.class, AccessDefinition.class).findFirst();
	}

	private <T> Stream<T> getDefinitions(
			final Object appFactory,
			final AppFactory appFactoryAnnotation,
			final Class<? extends Annotation> annotation,
			final Class<T> definitionType) {
		return Arrays.stream(AopUtils.getTargetClass(appFactory).getDeclaredMethods())
				.filter(method -> method.isAnnotationPresent(annotation))
				.map(method -> {
					try {
						//noinspection unchecked
						return (T) method.invoke(appFactory);
					} catch (IllegalAccessException | InvocationTargetException e) {
						LOG.error("Could not build " + definitionType.getSimpleName() + ", for app " + appFactoryAnnotation.name(), e);
						throw new Registry.InvalidDefinitionException(appFactoryAnnotation.id());
					}
				});
	}
}
