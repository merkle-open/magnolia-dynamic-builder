package com.merkle.oss.magnolia.templatebuilder;

import info.magnolia.rendering.engine.RenderException;
import info.magnolia.rendering.generator.Generator;
import info.magnolia.rendering.template.configured.ConfiguredAreaDefinition;
import info.magnolia.rendering.template.configured.ConfiguredAutoGeneration;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;

import java.lang.reflect.Method;
import java.util.Arrays;

import javax.jcr.Node;

import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;
import com.merkle.oss.magnolia.templatebuilder.parameter.AutoGeneratorParameterResolverFactory;

import jakarta.inject.Inject;

public class DynamicAutoGenerator implements Generator<DynamicAutoGenerator.Definition> {
    private final AutoGeneratorParameterResolverFactory autoGeneratorParameterResolverFactory;
    private final Node node;

    public DynamicAutoGenerator(
            final AutoGeneratorParameterResolverFactory autoGeneratorParameterResolverFactory,
            final Node node
    ) {
        this.autoGeneratorParameterResolverFactory = autoGeneratorParameterResolverFactory;
        this.node = node;
    }

    @Override
    public void generate(final Definition configuration) throws RenderException {
        try {
            final ParameterResolver parameterResolver = autoGeneratorParameterResolverFactory.create(node, configuration.getTemplate(), configuration.getArea());
            final Object[] parameters = Arrays
                    .stream(configuration.getMethod().getParameterTypes())
                    .map(parameterResolver::resolveParameter)
                    .toArray();
            configuration.getMethod().invoke(configuration.factoryObject, parameters);
        } catch (Exception e) {
            throw new RenderException("AutoGeneration of " + configuration.getFactoryObject().getClass().getName() + "." + configuration.getMethod().getName() + " failed!", e);
        }
    }

    public static class Definition extends ConfiguredAutoGeneration {
        private final ConfiguredTemplateDefinition template;
        private final ConfiguredAreaDefinition area;
        private final Object factoryObject;
        private final Method method;

        public Definition(
                final ConfiguredTemplateDefinition template,
                final ConfiguredAreaDefinition area,
                final Object factoryObject,
                final Method method
        ) {
            setGeneratorClass((Class) DynamicAutoGenerator.class);
            this.method = method;
            this.template = template;
            this.area = area;
            this.factoryObject = factoryObject;
        }

        public ConfiguredTemplateDefinition getTemplate() {
            return template;
        }

        public ConfiguredAreaDefinition getArea() {
            return area;
        }

        public Object getFactoryObject() {
            return factoryObject;
        }

        public Method getMethod() {
            return method;
        }
    }
}
