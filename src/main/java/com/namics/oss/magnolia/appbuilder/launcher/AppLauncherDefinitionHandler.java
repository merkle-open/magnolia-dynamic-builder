package com.namics.oss.magnolia.appbuilder.launcher;


import com.namics.oss.magnolia.appbuilder.annotations.AppFactory;
import com.namics.oss.magnolia.appbuilder.annotations.AppLauncherGroup;
import com.namics.oss.magnolia.appbuilder.annotations.GroupDefinition;
import com.namics.oss.magnolia.appbuilder.exception.AppBuilderException;
import com.namics.oss.magnolia.appbuilder.launcher.group.LauncherGroup;
import com.namics.oss.magnolia.appbuilder.launcher.group.SimpleGroupDefinition;
import info.magnolia.admincentral.AdmincentralModule;
import info.magnolia.ui.api.app.launcherlayout.AppLauncherGroupEntryDefinition;
import info.magnolia.ui.api.app.launcherlayout.AppLauncherLayoutManager;
import info.magnolia.ui.api.app.launcherlayout.ConfiguredAppLauncherGroupDefinition;
import info.magnolia.ui.api.app.launcherlayout.ConfiguredAppLauncherLayoutDefinition;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Component
public class AppLauncherDefinitionHandler {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final ConfiguredAppLauncherLayoutDefinition appLauncherLayoutDefinition;
	private final AppLauncherLayoutManager appLauncherLayoutManager;
	private final Map<String, SimpleGroupDefinition> groupDefinitionRegistry;

	@Inject
	public AppLauncherDefinitionHandler(
			final ApplicationContext applicationContext,
			final AppLauncherLayoutManager appLauncherLayoutManager,
			final AdmincentralModule admincentralModule) {
		this.appLauncherLayoutManager = appLauncherLayoutManager;
		this.appLauncherLayoutDefinition = (ConfiguredAppLauncherLayoutDefinition) admincentralModule.getAppLauncherLayout();
		this.groupDefinitionRegistry = streamGroupDefinitions(applicationContext)
				.collect(Collectors.toMap(SimpleGroupDefinition::getName, Function.identity()));
	}

	public void addApp(Object factoryObject, String appName) {
		final AppFactory annotation = AopUtils.getTargetClass(factoryObject).getAnnotation(AppFactory.class);

		// don't add to launcher if no group is specified
		final String groupName = annotation.launcherGroup();
		if (StringUtils.isBlank(groupName)) {
			return;
		}

		final int order = annotation.order();
		final OrderableAppLauncherGroupEntryDefinition app = new OrderableAppLauncherGroupEntryDefinition();
		app.setName(appName);
		app.setEnabled(true);
		app.setOrder(order);

		final ConfiguredAppLauncherGroupDefinition group = getOrCreateGroup(groupName);
		final List<AppLauncherGroupEntryDefinition> apps = Stream
				.concat(
						group.getApps().stream(),
						Stream.of(app)
				)
				.sorted(Comparator.comparing(this::getOrder))
				.collect(Collectors.toList());
		group.setApps(apps);
		appLauncherLayoutManager.setLayout(appLauncherLayoutDefinition);
	}

	private ConfiguredAppLauncherGroupDefinition getOrCreateGroup(final String groupName) {
		return getExistingGroup(groupName).orElseGet(() ->
				createGroup(groupName)
		);
	}

	private Optional<ConfiguredAppLauncherGroupDefinition> getExistingGroup(final String groupName) {
		return appLauncherLayoutDefinition.getGroups().stream()
				.filter(groupDefinition -> groupDefinition.getName().equals(groupName))
				.findFirst()
				.map(ConfiguredAppLauncherGroupDefinition.class::cast);
	}

	private ConfiguredAppLauncherGroupDefinition createGroup(final String groupName) {
		final SimpleGroupDefinition simpleGroup = groupDefinitionRegistry.getOrDefault(groupName, new SimpleGroupDefinition(groupName));
		final ConfiguredAppLauncherGroupDefinition group = simpleGroup.getConfiguredDefinition();
		appLauncherLayoutDefinition.addGroup(group);
		return group;
	}

	private int getOrder(final AppLauncherGroupEntryDefinition app) {
		if (app instanceof OrderableAppLauncherGroupEntryDefinition) {
			return ((OrderableAppLauncherGroupEntryDefinition) app).getOrder();
		}
		return -1;
	}

	private Stream<SimpleGroupDefinition> streamGroupDefinitions(final ApplicationContext applicationContext) {
		return Stream.concat(
				LauncherGroup.getAllExistingGroups().values().stream(),
				applicationContext.getBeansWithAnnotation(AppLauncherGroup.class)
						.values()
						.stream()
						.map(this::findSimpleDefinition)
		);
	}

	private SimpleGroupDefinition findSimpleDefinition(final Object factoryObject) {
		final Class<?> factoryClass = AopUtils.getTargetClass(factoryObject);
		final Class<GroupDefinition> annotationClass = GroupDefinition.class;
		final AppLauncherGroup annotation = AopUtils.getTargetClass(factoryObject).getAnnotation(AppLauncherGroup.class);
		final String groupName = annotation.name();

		return Arrays.stream(factoryClass.getDeclaredMethods())
				.filter(method -> method.isAnnotationPresent(annotationClass))
				.peek(method -> {
					if (Modifier.isStatic(method.getModifiers())) {
						LOG.error("'{}' annotation is not supported on static methods, skipping.", annotationClass.getSimpleName());
					}
					if (!SimpleGroupDefinition.class.isAssignableFrom(method.getReturnType())) {
						LOG.error("{} annotated classes must return a '{}', skipping.", annotationClass.getSimpleName(), SimpleGroupDefinition.class.getName());
					}
				})
				.filter(method -> !Modifier.isStatic(method.getModifiers()))
				.filter(method -> SimpleGroupDefinition.class.isAssignableFrom(method.getReturnType()))
				.map(method -> buildSimpleGroupDefinition(factoryObject, method))
				.findFirst()
				.orElseGet(() -> new SimpleGroupDefinition(groupName));
	}

	private SimpleGroupDefinition buildSimpleGroupDefinition(Object factoryObject, Method method) {
		try {
			return (SimpleGroupDefinition) method.invoke(factoryObject);
		} catch (IllegalAccessException | InvocationTargetException e) {
			LOG.error("Could not build SimpleGroupDefinition, skipping. '{}'", e.getMessage());
			LOG.debug("Could not build SimpleGroupDefinition, skipping", e);
			throw AppBuilderException.wrap(e);
		}
	}

}
