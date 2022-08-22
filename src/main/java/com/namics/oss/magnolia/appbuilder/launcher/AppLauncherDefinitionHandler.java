package com.namics.oss.magnolia.appbuilder.launcher;


import com.namics.oss.magnolia.appbuilder.annotations.AppFactory;
import com.namics.oss.magnolia.appbuilder.annotations.AppLauncherGroup;
import com.namics.oss.magnolia.appbuilder.annotations.GroupDefinition;
import com.namics.oss.magnolia.appbuilder.launcher.group.LauncherGroup;
import com.namics.oss.magnolia.appbuilder.launcher.group.SimpleGroupDefinition;
import info.magnolia.admincentral.AdmincentralModule;
import info.magnolia.admincentral.layout.DefaultGroupDefinition;
import info.magnolia.admincentral.layout.DefaultRowDefinition;
import info.magnolia.config.registry.Registry;
import info.magnolia.ui.api.app.launcherlayout.AppLauncherGroupEntryDefinition;
import info.magnolia.ui.api.app.launcherlayout.AppLauncherLayoutManager;
import info.magnolia.ui.api.app.launcherlayout.ConfiguredAppLauncherGroupDefinition;
import info.magnolia.ui.api.app.launcherlayout.ConfiguredAppLauncherLayoutDefinition;
import info.magnolia.ui.vaadin.gwt.client.applauncher.shared.RowDefinition;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
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
	private final AdmincentralModule admincentralModule;

	@Inject
	public AppLauncherDefinitionHandler(
			final ApplicationContext applicationContext,
			final AppLauncherLayoutManager appLauncherLayoutManager,
			final AdmincentralModule admincentralModule) {
		this.appLauncherLayoutManager = appLauncherLayoutManager;
		this.appLauncherLayoutDefinition = (ConfiguredAppLauncherLayoutDefinition) admincentralModule.getAppLauncherLayout();
		this.groupDefinitionRegistry = streamSimpleGroupDefinitions(applicationContext)
				.collect(Collectors.toMap(SimpleGroupDefinition::getName, Function.identity()));
		this.admincentralModule = admincentralModule;
	}

	public void addApp(final Object appFactory) {
		final AppFactory appFactoryAnnotation = AopUtils.getTargetClass(appFactory).getAnnotation(AppFactory.class);

		final String groupName = appFactoryAnnotation.launcherGroup();
		if (!StringUtils.isBlank(groupName)) {
			final int order = appFactoryAnnotation.order();
			final OrderableAppLauncherGroupEntryDefinition app = new OrderableAppLauncherGroupEntryDefinition();
			app.setName(appFactoryAnnotation.name());
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
			admincentralModule.getLayout().getRows().stream()
					.map(DefaultRowDefinition::getGroups)
					.flatMap(Collection::stream)
					.filter(layoutGroup -> Objects.equals(layoutGroup.getName(), group.getName()))
					.forEach(layoutGroup ->
						layoutGroup.setApps(apps.stream().map(AppLauncherGroupEntryDefinition::getName).collect(Collectors.toList()))
					);

			appLauncherLayoutManager.setLayout(appLauncherLayoutDefinition);
		}
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
		final SimpleGroupDefinition group = groupDefinitionRegistry.getOrDefault(groupName, new SimpleGroupDefinition(groupName));
		appLauncherLayoutDefinition.addGroup(group);
		admincentralModule.getLayout().setRows(Stream.concat(
				admincentralModule.getLayout().getRows().stream(),
				Stream.of(getLayoutRow(group))
		).collect(Collectors.toList()));

		admincentralModule.getCompatibilityLayout().setRows(Stream.concat(
				admincentralModule.getCompatibilityLayout().getRows().stream(),
				Stream.of(getCompatibilityLayoutRow(group))
		).collect(Collectors.toList()));

		return group;
}

	private DefaultRowDefinition getLayoutRow(final SimpleGroupDefinition group) {
		final DefaultGroupDefinition groupDefinition = new DefaultGroupDefinition();
		groupDefinition.setName(group.getName());
		final DefaultRowDefinition row = new DefaultRowDefinition();
		row.setGroups(List.of(groupDefinition));
		row.setCssClass("editor");
		return row;
	}

	private RowDefinition getCompatibilityLayoutRow(final SimpleGroupDefinition group) {
		final RowDefinition row = new RowDefinition();
		row.setGroups(List.of(group.getName()));
		row.setCssClass("editor");
		return row;
	}

	private int getOrder(final AppLauncherGroupEntryDefinition app) {
		if (app instanceof OrderableAppLauncherGroupEntryDefinition) {
			return ((OrderableAppLauncherGroupEntryDefinition) app).getOrder();
		}
		return -1;
	}

	private Stream<SimpleGroupDefinition> streamSimpleGroupDefinitions(final ApplicationContext applicationContext) {
		return Stream.concat(
				LauncherGroup.getMagnoliaDefaultGroups().values().stream(),
				applicationContext.getBeansWithAnnotation(AppLauncherGroup.class)
						.values()
						.stream()
						.map(this::getSimpleGroupDefinition)
		);
	}

	private SimpleGroupDefinition getSimpleGroupDefinition(final Object appLauncher) {
		final AppLauncherGroup appLauncherGroupAnnotation = AopUtils.getTargetClass(appLauncher).getAnnotation(AppLauncherGroup.class);
		return Arrays.stream(AopUtils.getTargetClass(appLauncher).getDeclaredMethods())
				.filter(method -> method.isAnnotationPresent(GroupDefinition.class))
				.map(method -> {
					try {
						return (SimpleGroupDefinition) method.invoke(appLauncher);
					} catch (IllegalAccessException | InvocationTargetException e) {
						LOG.error("Could not build SimpleGroupDefinition, for appLauncher " + appLauncherGroupAnnotation.name(), e);
						throw new Registry.InvalidDefinitionException(appLauncherGroupAnnotation.name());
					}
				})
				.findFirst()
				.orElseGet(() -> new SimpleGroupDefinition(appLauncherGroupAnnotation.name()));
	}
}
