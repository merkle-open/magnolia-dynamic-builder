package com.namics.oss.magnolia.appbuilder;

import info.magnolia.module.blossom.support.AbstractBeanDetector;
import info.magnolia.ui.dialog.DialogDefinitionRegistry;

import java.lang.invoke.MethodHandles;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;

import com.namics.oss.magnolia.appbuilder.annotations.ChooserDialogFactory;

/**
 * Scans for app classes annotated with {@link ChooserDialogFactory} and registers them.
 */
@Component
public class ChooserDialogExporter extends AbstractBeanDetector implements ApplicationContextAware {
	private static final Logger LOG = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private final DialogDefinitionRegistry dialogDefinitionRegistry;

	@Inject
	public ChooserDialogExporter(final DialogDefinitionRegistry dialogDefinitionRegistry) {
		this.dialogDefinitionRegistry = dialogDefinitionRegistry;
	}

	@Override
	protected boolean include(final Class<?> beanType, final String beanName) {
		return (ClassUtils.isCglibProxyClass(beanType) ? beanType.getSuperclass() : beanType).isAnnotationPresent(ChooserDialogFactory.class);
	}

	@Override
	protected void onBeanDetection(final Object chooserDialogFactory, final String beanName) {
		LOG.info("Detected chooser dialog factory bean with name '{}'", beanName);
		final ChooserDialogDefinitionProvider<?> chooserDialogDefinitionProvider = new ChooserDialogDefinitionProvider<>(chooserDialogFactory);
		dialogDefinitionRegistry.register(chooserDialogDefinitionProvider);
		LOG.info("Registered chooser dialog '{}'", chooserDialogDefinitionProvider.getMetadata().getName());
	}
}
