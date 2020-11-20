package com.namics.oss.magnolia.appbuilder;

import com.namics.oss.magnolia.appbuilder.annotations.AppFactory;
import com.namics.oss.magnolia.appbuilder.annotations.AppPermissions;
import com.namics.oss.magnolia.appbuilder.annotations.ChooseDialog;
import com.namics.oss.magnolia.appbuilder.annotations.SubApp;
import com.namics.oss.magnolia.appbuilder.exception.AppBuilderException;
import info.magnolia.cms.security.operations.AccessDefinition;
import info.magnolia.ui.api.app.SubAppDescriptor;
import info.magnolia.ui.dialog.definition.ChooseDialogDefinition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.support.AopUtils;

import java.lang.annotation.Annotation;
import java.lang.invoke.MethodHandles;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Builds the app-description from app bean (annotated with {@link AppFactory})
 */
public class AppFactoryDescriptionBuilder {

	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	public AppFactoryDescription buildDescription(Object factoryObject) {
		AppFactory annotation = AopUtils.getTargetClass(factoryObject)
				.getAnnotation(AppFactory.class);

		AppFactoryMetaData factoryMetaData = new AppFactoryMetaData()
				.i18nBasename(annotation.i18nBasename())
				.label(annotation.label())
				.icon(annotation.icon())
				.theme(annotation.theme())
				.factoryObject(factoryObject);

		return new AppFactoryDescription()
				.id(annotation.id())
				.name(annotation.name())
				.enabled(annotation.isEnabled())
				.factoryMetaData(factoryMetaData)
				.subApps(findSubApps(factoryObject))
				.chooseDialog(findChooseDialog(factoryObject))
				.permissions(findPermissions(factoryObject));
	}

	private Map<String, SubAppDescriptor> findSubApps(Object factoryObject) {
		return findAnnotatedAppClasses(factoryObject, SubApp.class, SubAppDescriptor.class)
				.collect(Collectors.toMap(
						Method::getName,
						method -> buildDefinition(factoryObject, method, SubAppDescriptor.class)
						)
				);
	}

	private ChooseDialogDefinition findChooseDialog(Object factoryObject) {
		return findAnnotatedAppClasses(factoryObject, ChooseDialog.class, ChooseDialogDefinition.class)
				.map(method -> buildDefinition(factoryObject, method, ChooseDialogDefinition.class))
				.findFirst().orElse(null);
	}

	private AccessDefinition findPermissions(Object factoryObject) {
		return findAnnotatedAppClasses(factoryObject, AppPermissions.class, AccessDefinition.class)
				.map(method -> buildDefinition(factoryObject, method, AccessDefinition.class))
				.findFirst().orElse(null);
	}

	private Stream<Method> findAnnotatedAppClasses(Object factoryObject, Class<? extends Annotation> annotation, Class<?> returnType) {
		Class<?> factoryClass = AopUtils.getTargetClass(factoryObject);
		return Arrays.stream(factoryClass.getDeclaredMethods())
				.filter(method -> method.isAnnotationPresent(annotation))
				.peek(method -> {
					if (Modifier.isStatic(method.getModifiers())) {
						LOG.error("{} annotation is not supported on static methods, skipping.", annotation.getSimpleName());
					}
					if (!returnType.isAssignableFrom(method.getReturnType())) {
						LOG.error("{} annotated classes must return a '{}', skipping.", annotation.getSimpleName(), returnType.getName());
					}
				})
				.filter(method -> !Modifier.isStatic(method.getModifiers()))
				.filter(method -> returnType.isAssignableFrom(method.getReturnType()));
	}

	@SuppressWarnings("unchecked")
	private <T> T buildDefinition(Object factoryObject, Method method, Class<T> definitionType) {
		try {
			return (T) method.invoke(factoryObject);
		} catch (IllegalAccessException | InvocationTargetException e) {
			LOG.error("Could not build '{}', skipping. '{}'", definitionType.getClass().getSimpleName(), e.getMessage());
			LOG.debug("Could not build '{}', skipping", definitionType.getClass().getSimpleName(), e);
			throw AppBuilderException.wrap(e);
		}
	}

}
