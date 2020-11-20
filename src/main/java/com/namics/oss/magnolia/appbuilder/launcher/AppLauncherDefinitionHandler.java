package com.namics.oss.magnolia.appbuilder.launcher;


import com.namics.oss.magnolia.powernode.PowerNodeService;
import com.namics.oss.magnolia.appbuilder.annotations.AppFactory;
import com.namics.oss.magnolia.appbuilder.annotations.AppLauncherGroup;
import com.namics.oss.magnolia.appbuilder.annotations.GroupDefinition;
import com.namics.oss.magnolia.appbuilder.exception.AppBuilderException;
import com.namics.oss.magnolia.appbuilder.launcher.group.LauncherGroup;
import com.namics.oss.magnolia.appbuilder.launcher.group.SimpleGroupDefinition;
import com.namics.oss.magnolia.powernode.PowerNode;
import info.magnolia.jcr.node2bean.Node2BeanException;
import info.magnolia.jcr.node2bean.Node2BeanProcessor;
import info.magnolia.jcr.node2bean.Node2BeanTransformer;
import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.objectfactory.Components;
import info.magnolia.repository.RepositoryConstants;
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
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.*;

@Component
public class AppLauncherDefinitionHandler {

	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static final String APP_LAUNCHER_LAYOUT_CONFIG = "/modules/ui-admincentral/config/appLauncherLayout";

	private ConfiguredAppLauncherLayoutDefinition appLauncherLayoutDefinition;

	private final AppLauncherLayoutManager appLauncherLayoutManager;

	private final Map<String, SimpleGroupDefinition> groupDefinitionRegistry = new HashMap<>();

	@Inject
	public AppLauncherDefinitionHandler(PowerNodeService powerNodeService, ApplicationContext applicationContext) {
		this.appLauncherLayoutManager = Components.getComponent(AppLauncherLayoutManager.class);

		// register all existing launcher groups
		groupDefinitionRegistry.putAll(LauncherGroup.getAllExistingGroups());

		// add user defined launcher groups to registry
		Map<String, Object> appLauncherGroups = applicationContext.getBeansWithAnnotation(AppLauncherGroup.class);
		appLauncherGroups.forEach((s, o) -> {
			SimpleGroupDefinition simpleDefinition = findSimpleDefinition(o);
			groupDefinitionRegistry.putIfAbsent(simpleDefinition.getName(), simpleDefinition);
		});

		initAppLauncherDefinitionFromJcr(powerNodeService);
	}

	public void addApp(Object factoryObject, String appName) {
		AppFactory annotation = AopUtils.getTargetClass(factoryObject)
				.getAnnotation(AppFactory.class);

		// don't add to launcher if no group is specified
		String groupName = annotation.launcherGroup();
		if (StringUtils.isBlank(groupName)) {
			return;
		}

		int order = annotation.order();
		OrderableAppLauncherGroupEntryDefinition app = new OrderableAppLauncherGroupEntryDefinition();
		app.setName(appName);
		app.setEnabled(true);
		app.setOrder(order);

		if (isGroupExisting(groupName)) {
			ConfiguredAppLauncherGroupDefinition existingGroup = getExistingGroup(groupName);
			List<AppLauncherGroupEntryDefinition> apps = existingGroup.getApps();
			apps.add(app);
			apps.sort(((o1, o2) -> {
				Integer order1 = getOrder(o1);
				Integer order2 = getOrder(o2);
				return order1.compareTo(order2);
			}));
			existingGroup.setApps(apps);
		} else {
			SimpleGroupDefinition simpleGroup = groupDefinitionRegistry.getOrDefault(groupName, new SimpleGroupDefinition(groupName));
			ConfiguredAppLauncherGroupDefinition group = simpleGroup.getConfiguredDefinition();
			group.addApp(app);
			appLauncherLayoutDefinition.addGroup(group);
		}
		appLauncherLayoutManager.setLayout(appLauncherLayoutDefinition);
	}

	private Integer getOrder(AppLauncherGroupEntryDefinition app) {
		if (app instanceof OrderableAppLauncherGroupEntryDefinition) {
			return ((OrderableAppLauncherGroupEntryDefinition) app).getOrder();
		} else {
			return -1;
		}
	}

	private ConfiguredAppLauncherGroupDefinition getExistingGroup(String groupName) {
		return (ConfiguredAppLauncherGroupDefinition) appLauncherLayoutDefinition.getGroups().stream()
				.filter(groupDefinition -> groupDefinition.getName().equals(groupName))
				.findFirst()
				.orElseThrow(() -> new AppBuilderException("Optional Item Not Found"));
	}

	private boolean isGroupExisting(String groupName) {
		return appLauncherLayoutDefinition.getGroups().stream()
				.anyMatch(groupDefinition -> groupDefinition.getName().equals(groupName));
	}

	private void initAppLauncherDefinitionFromJcr(PowerNodeService powerNodeService) {
		Session session = powerNodeService.getSystemSession(RepositoryConstants.CONFIG).orElse(null);
		Optional<PowerNode> appLauncherLayoutNode = powerNodeService.getNodeByPath(APP_LAUNCHER_LAYOUT_CONFIG, session);
		this.appLauncherLayoutDefinition = appLauncherLayoutNode
				.map(this::getAppLayoutDefinitionFromNode)
				.orElseGet(ConfiguredAppLauncherLayoutDefinition::new);
		appLauncherLayoutNode.ifPresent(PowerNode::sessionLogout);
	}

	private ConfiguredAppLauncherLayoutDefinition getAppLayoutDefinitionFromNode(PowerNode node) {
		try {
			Node2BeanProcessor node2BeanProcessor = Components.getComponent(Node2BeanProcessor.class);
			ConfiguredAppLauncherLayoutDefinition bean = new ConfiguredAppLauncherLayoutDefinition();
			Node2BeanTransformer transformer = Components.getComponent(Node2BeanTransformer.class);
			ComponentProvider componentProvider = Components.getComponentProvider();
			return (ConfiguredAppLauncherLayoutDefinition) node2BeanProcessor.setProperties(bean, node, true, transformer, componentProvider);
		} catch (Node2BeanException | RepositoryException e) {
			LOG.error("Could not load AppLauncherLayoutDefinition from JCR, Launcher will be empty: {}", e.getMessage());
			LOG.debug("Could not load AppLauncherLayoutDefinition from JCR, Launcher will be empty", e);
			return new ConfiguredAppLauncherLayoutDefinition();
		}
	}

	private SimpleGroupDefinition findSimpleDefinition(Object factoryObject) {
		Class<?> factoryClass = AopUtils.getTargetClass(factoryObject);
		Class<GroupDefinition> annotationClass = GroupDefinition.class;
		AppLauncherGroup annotation = AopUtils.getTargetClass(factoryObject)
				.getAnnotation(AppLauncherGroup.class);
		String groupName = annotation.name();

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
				.findFirst().orElseGet(() -> new SimpleGroupDefinition(groupName));
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
