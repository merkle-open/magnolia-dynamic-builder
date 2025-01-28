package com.merkle.oss.magnolia.templatebuilder;

import info.magnolia.rendering.template.TemplateAvailability;

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
            return (node, templateDefinition) -> false;
        }
        if (matchingMethods.size() > 1) {
            throw new IllegalStateException("Multiple @Available annotated methods found for template [" + template.getClass() + "]");
        }
        final Method method = matchingMethods.get(0);
        if (!method.getReturnType().equals(Boolean.TYPE)) {
            throw new IllegalStateException("Method " + method.getName() + " annotated with @Available for template [" + template.getClass() + "] has wrong return type [" + method.getReturnType() + "] should be boolean.");
        }
        return (node, templateDefinition) -> invoke(template, method, availabilityParameterResolverFactory.create(node, templateDefinition));
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

    private Stream<Method> streamMethods(final Class<?> clazz, final Class<? extends Annotation> annotationClass) {
        return Stream.concat(
                        Arrays.stream(clazz.getDeclaredMethods()),
                        Arrays.stream(clazz.getMethods())
                )
                .distinct()
                .filter(method -> method.isAnnotationPresent(annotationClass) && !Modifier.isStatic(method.getModifiers()));
    }
}
