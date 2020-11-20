package com.namics.oss.magnolia.appbuilder;

import com.namics.oss.magnolia.appbuilder.annotations.AppFactory;
import com.namics.oss.magnolia.appbuilder.launcher.AppLauncherDefinitionHandler;
import info.magnolia.module.blossom.support.AbstractBeanDetector;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.api.app.registry.AppDescriptorRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.ClassUtils;

import javax.inject.Inject;
import java.lang.invoke.MethodHandles;

/**
 * Scans for app classes annotated with {@link AppFactory},
 * builds the app descriptions and registers the app.
 */
public class AppExporter extends AbstractBeanDetector implements InitializingBean {

	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private AppFactoryDescriptionBuilder appFactoryDescriptionBuilder;

	@Inject
	private AppLauncherDefinitionHandler appLauncherDefinitionHandler;

	@Override
	protected boolean include(Class<?> beanType, String beanName) {
		beanType = ClassUtils.isCglibProxyClass(beanType) ? beanType.getSuperclass() : beanType;
		return beanType.isAnnotationPresent(AppFactory.class);
	}

	@Override
	protected void onBeanDetection(Object bean, String beanName) {
		LOG.info("Detected app bean with name '{}'", beanName);
		// build app descriptor from detected factory bean
		AppFactoryDescription appFactoryDescription = appFactoryDescriptionBuilder.buildDescription(bean);

		// register app descriptor
		AppDescriptorRegistry appDescriptorRegistry = Components.getComponent(AppDescriptorRegistry.class);
		appDescriptorRegistry.register(new AppDescriptorProvider(appFactoryDescription));

		// add app to launcher (if accordingly configured)
		appLauncherDefinitionHandler.addApp(bean, appFactoryDescription.getName());
		LOG.info("Registered app '{}'", appFactoryDescription.getName());
	}

	@Override
	public void afterPropertiesSet() throws Exception {
		if (appFactoryDescriptionBuilder == null) {
			setAppFactoryDescriptionBuilder(new AppFactoryDescriptionBuilder());
		}
	}

	public AppFactoryDescriptionBuilder getAppFactoryDescriptionBuilder() {
		return appFactoryDescriptionBuilder;
	}

	public void setAppFactoryDescriptionBuilder(AppFactoryDescriptionBuilder appFactoryDescriptionBuilder) {
		this.appFactoryDescriptionBuilder = appFactoryDescriptionBuilder;
	}

}
