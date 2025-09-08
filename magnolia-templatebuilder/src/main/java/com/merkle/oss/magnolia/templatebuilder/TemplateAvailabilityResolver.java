package com.merkle.oss.magnolia.templatebuilder;

import info.magnolia.rendering.template.TemplateAvailability;
import info.magnolia.rendering.template.TemplateDefinition;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import javax.inject.Inject;
import javax.jcr.Node;

import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;
import com.merkle.oss.magnolia.templatebuilder.annotation.Available;
import com.merkle.oss.magnolia.templatebuilder.parameter.AvailabilityParameterResolverFactory;

public class TemplateAvailabilityResolver {
    private final AvailabilityParameterResolverFactory availabilityParameterResolverFactory;

    @Inject
    public TemplateAvailabilityResolver(final AvailabilityParameterResolverFactory availabilityParameterResolverFactory) {
        this.availabilityParameterResolverFactory = availabilityParameterResolverFactory;
    }

    public TemplateAvailability<Node> resolve(final Object template) {
        final List<Method> matchingMethods = streamMethods(template.getClass(), Available.class).toList();

        if (matchingMethods.isEmpty()) {
            return new FallbackAvailabilityResolver();
        }
        if (matchingMethods.size() > 1) {
            throw new IllegalStateException("Multiple @Available annotated methods found for template [" + template.getClass() + "]");
        }
        final Method method = matchingMethods.get(0);
        if (!method.getReturnType().equals(Boolean.TYPE)) {
            throw new IllegalStateException("Method " + method.getName() + " annotated with @Available for template [" + template.getClass() + "] has wrong return type [" + method.getReturnType() + "] should be boolean.");
        }
        return new AvailabilityResolver(template, method, availabilityParameterResolverFactory);
    }

    private Stream<Method> streamMethods(final Class<?> clazz, final Class<? extends Annotation> annotationClass) {
        return Stream.concat(
                        Arrays.stream(clazz.getDeclaredMethods()),
                        Arrays.stream(clazz.getMethods())
                )
                .distinct()
                .filter(method -> method.isAnnotationPresent(annotationClass) && !Modifier.isStatic(method.getModifiers()));
    }

    public static class AvailabilityResolver implements TemplateAvailability<Node> {
        private final Object template;
        private final Method method;
        private final AvailabilityParameterResolverFactory availabilityParameterResolverFactory;

        public AvailabilityResolver(final Object template, final Method method, final AvailabilityParameterResolverFactory availabilityParameterResolverFactory) {
            this.template = template;
            this.method = method;
            this.availabilityParameterResolverFactory = availabilityParameterResolverFactory;
        }

        @Override
        public boolean isAvailable(final Node node, final TemplateDefinition templateDefinition) {
            return invoke(template, method, availabilityParameterResolverFactory.create(node, templateDefinition));
        }

        private boolean invoke(final Object template, final Method availableMethod, final ParameterResolver parameterResolver) {
            final Object[] parameters = Arrays
                    .stream(availableMethod.getParameterTypes())
                    .map(parameterResolver::resolveParameter)
                    .toArray();
            try {
                return (boolean)availableMethod.invoke(template, parameters);
            } catch (IllegalAccessException | InvocationTargetException e) {
                throw new RuntimeException("Could not invoke " + availableMethod.getName() + ", for template " + template.getClass(), e);
            }
        }
    }

    public static class FallbackAvailabilityResolver implements TemplateAvailability<Node> {
        @Override
        public boolean isAvailable(final Node node, final TemplateDefinition templateDefinition) {
            return false;
        }
    }
}
