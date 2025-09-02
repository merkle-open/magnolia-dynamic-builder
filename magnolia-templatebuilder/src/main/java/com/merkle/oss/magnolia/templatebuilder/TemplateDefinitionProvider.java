package com.merkle.oss.magnolia.templatebuilder;

import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.Registry;
import info.magnolia.config.registry.decoration.DefinitionDecorator;
import info.magnolia.module.advancedcache.rendering.DynamicFragmentDefinition;
import info.magnolia.objectfactory.Components;
import info.magnolia.rendering.DefinitionTypes;
import info.magnolia.rendering.model.RenderingModel;
import info.magnolia.rendering.template.AreaDefinition;
import info.magnolia.rendering.template.ComponentAvailability;
import info.magnolia.rendering.template.TemplateAvailability;
import info.magnolia.rendering.template.TemplateDefinition;
import info.magnolia.rendering.template.configured.ConfiguredAreaDefinition;
import info.magnolia.rendering.template.configured.ConfiguredAutoGeneration;
import info.magnolia.rendering.template.configured.ConfiguredComponentAvailability;
import info.magnolia.rendering.template.configured.ConfiguredInheritance;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.jcr.Node;

import org.apache.commons.lang3.StringUtils;

import com.merkle.oss.magnolia.builder.AbstractDynamicDefinitionProvider;
import com.merkle.oss.magnolia.builder.DynamicDefinitionMetaData;
import com.merkle.oss.magnolia.templatebuilder.annotation.DynamicFragment;
import com.merkle.oss.magnolia.templatebuilder.annotation.Template;
import com.merkle.oss.magnolia.templatebuilder.annotation.area.Area;
import com.merkle.oss.magnolia.templatebuilder.annotation.area.AutoGenerator;
import com.merkle.oss.magnolia.templatebuilder.annotation.area.AvailableComponentClasses;
import com.merkle.oss.magnolia.templatebuilder.annotation.area.AvailableComponents;
import com.merkle.oss.magnolia.templatebuilder.annotation.area.ComponentCategory;
import com.merkle.oss.magnolia.templatebuilder.annotation.area.Inherits;
import com.merkle.oss.magnolia.templatebuilder.definition.DynamicComponentAvailabilityResolvingAreaDefinition;
import com.merkle.oss.magnolia.templatebuilder.definition.DynamicPermissionTemplateDefinition;

import jakarta.inject.Inject;
import jakarta.inject.Provider;

public class TemplateDefinitionProvider extends AbstractDynamicDefinitionProvider<TemplateDefinition> {
    private final TemplateAvailabilityResolver templateAvailabilityResolver;
    private final BiFunction<TemplateAvailability<Node>, Template, ConfiguredTemplateDefinition> templateDefinitionFactory;
    private final Function<TemplateAvailability<Node>, ConfiguredAreaDefinition> areaDefinitionFactory;
    private final Set<Class<?>> templateFactories;
    private final Function<Class<?>, Object> factoryObjectProvider;
    private final Template annotation;
    private final DefinitionMetadata metadata;
    private final Class<?> factoryClass;

    public TemplateDefinitionProvider(
            final List<DefinitionDecorator<TemplateDefinition>> decorators,
            final TemplateAvailabilityResolver templateAvailabilityResolver,
            final BiFunction<TemplateAvailability<Node>, Template, ConfiguredTemplateDefinition> templateDefinitionFactory,
            final Function<TemplateAvailability<Node>, ConfiguredAreaDefinition> areaDefinitionFactory,
            final Set<Class<?>> templateFactories,
            final Function<Class<?>, Object> factoryObjectProvider,
            final Class<?> factoryClass
    ) {
        super(decorators);
        this.templateAvailabilityResolver = templateAvailabilityResolver;
        this.templateDefinitionFactory = templateDefinitionFactory;
        this.areaDefinitionFactory = areaDefinitionFactory;
        this.templateFactories = templateFactories;
        this.factoryObjectProvider = factoryObjectProvider;
        this.factoryClass = factoryClass;
        this.annotation = factoryClass.getDeclaredAnnotation(Template.class);
        this.metadata = new DynamicDefinitionMetaData.Builder(factoryClass, annotation.id())
                .type(DefinitionTypes.TEMPLATE)
                .build();
    }

    @Override
    public DefinitionMetadata getMetadata() {
        return metadata;
    }

    @Override
    public TemplateDefinition getInternal() throws Registry.InvalidDefinitionException {
        final Object factoryObject = factoryObjectProvider.apply(factoryClass);
        final ConfiguredTemplateDefinition template = templateDefinitionFactory.apply(templateAvailabilityResolver.resolve(factoryObject), annotation);
        template.setId(annotation.id());
        template.setName(metadata.getName());
        template.setTitle(StringUtils.trimToNull(annotation.title()));
        template.setDescription(StringUtils.trimToNull(annotation.description()));
        template.setDialog(StringUtils.trimToNull(annotation.dialog()));
        template.setType(annotation.type());
        template.setSubtype(StringUtils.trimToNull(annotation.subtype()));
        template.setRenderType(annotation.renderer());
        template.setAreas(getAreas(template, factoryObject.getClass()));
        Optional.of(annotation.modelClass()).filter(RenderingModel.class::isAssignableFrom).ifPresent(template::setModelClass);
        template.setTemplateScript(annotation.templateScript());
        dynamicFragment(factoryObject.getClass()).ifPresent(template::setFragmentDefinition);
        return template;
    }

    protected Map<String, AreaDefinition> getAreas(final ConfiguredTemplateDefinition definition, final Class<?> clazz) {
        return streamClasses(clazz, Area.class)
                .map(areaClazz -> getAreaDefinition(definition, areaClazz, areaClazz.getDeclaredAnnotation(Area.class)))
                .collect(Collectors.toMap(AreaDefinition::getName, Function.identity(), (first, second) -> first));
    }

    protected AreaDefinition getAreaDefinition(final ConfiguredTemplateDefinition template, final Class<?> areaClazz, final Area annotation) {
        final Object factoryObject = factoryObjectProvider.apply(areaClazz);
        final ConfiguredAreaDefinition area = areaDefinitionFactory.apply(templateAvailabilityResolver.resolve(factoryObject));
        area.setId(annotation.id());
        area.setName(annotation.name());
        area.setTitle(StringUtils.trimToNull(annotation.title()));
        area.setRenderType(Optional.ofNullable(StringUtils.trimToNull(annotation.renderer())).orElseGet(this.annotation::renderer));
        area.setDialog(StringUtils.trimToNull(annotation.dialog()));
        area.setType(annotation.type().getDefinitionFormat());
        Optional.of(annotation.maxComponents()).filter(max -> Integer.MAX_VALUE != max).ifPresent(area::setMaxComponents);
        area.setOptional(annotation.optional().getValue());
        area.setCreateAreaNode(annotation.createAreaNode().getValue());
        area.setTemplateScript(annotation.templateScript()); // If the templateScript is null the area is rendered simply by looping the components. (default annotation value is undefined)
        getInheritanceConfiguration(areaClazz).ifPresent(area::setInheritance);
        getAutoGenerationConfiguration(template, area, factoryObject).ifPresent(area::setAutoGeneration);
        area.setAvailableComponents(getAvailableComponents(areaClazz));
        area.setAreas(getAreas(template, areaClazz));
        dynamicFragment(areaClazz).ifPresent(template::setFragmentDefinition);
        return area;
    }

    private Optional<ConfiguredAutoGeneration> getAutoGenerationConfiguration(
            final ConfiguredTemplateDefinition template,
            final ConfiguredAreaDefinition area,
            final Object factoryObject
    ) {
        final List<Method> matchingMethods = streamMethods(factoryObject.getClass(), AutoGenerator.class).toList();
        if (matchingMethods.isEmpty()) {
            return Optional.empty();
        }
        if (matchingMethods.size() > 1) {
            throw new IllegalStateException("Multiple @AutoGenerator annotated methods found for area [" + factoryObject.getClass() + "]");
        }
        return Optional.of(new DynamicAutoGenerator.Definition(template, area, factoryObject, matchingMethods.get(0)));
    }

    private Optional<ConfiguredInheritance> getInheritanceConfiguration(final Class<?> areaClazz) {
        return Optional
                .ofNullable(areaClazz.getDeclaredAnnotation(Inherits.class))
                .map(inherits -> {
                    final ConfiguredInheritance inheritance = new ConfiguredInheritance();
                    inheritance.setEnabled(true);
                    inheritance.setComponents(inherits.components().getConfigurationFormat());
                    inheritance.setProperties(inherits.properties().getConfigurationFormat());
                    return inheritance;
                });
    }

    protected Map<String, ComponentAvailability> getAvailableComponents(final Class<?> areaClazz) {
        return Stream
                .concat(
                        Stream.ofNullable(areaClazz.getDeclaredAnnotation(AvailableComponents.class))
                                .map(AvailableComponents::value).flatMap(Arrays::stream),
                        Stream.ofNullable(areaClazz.getDeclaredAnnotation(AvailableComponentClasses.class))
                                .map(AvailableComponentClasses::value).flatMap(Arrays::stream)
                                .flatMap(componentClazz -> resolveAvailableComponentIds(areaClazz, componentClazz))
                                .map(componentClazz -> Optional.ofNullable(componentClazz.getDeclaredAnnotation(Template.class)).map(Template::id).orElseThrow(() ->
                                        new IllegalArgumentException("available component clazz " + componentClazz.getName() + " is not annotated with @" + Template.class.getSimpleName())
                                ))
                )
                .map(componentId -> {
                    final ConfiguredComponentAvailability availability = new ConfiguredComponentAvailability();
                    availability.setId(componentId);
                    return availability;
                })
                .collect(Collectors.toMap(ComponentAvailability::getId, Function.identity()));
    }

    private Optional<Class<?>> resolveComponentFactory(final String componentId) {
        return templateFactories.stream()
                .filter(componentClazz -> Optional
                        .ofNullable(componentClazz.getDeclaredAnnotation(Template.class))
                        .filter(template -> Objects.equals(template.id(), componentId))
                        .isPresent()
                )
                .findFirst();
    }

    private Stream<Class<?>> resolveAvailableComponentIds(final Class<?> areaClazz, final Class<?> componentClazz) {
        if (componentClazz.isAnnotation()) {
            if (!componentClazz.isAnnotationPresent(ComponentCategory.class)) {
                throw new IllegalArgumentException("Annotation [" + componentClazz.getName() + "] specified on area [" + areaClazz.getName() + "] is not a @" + ComponentCategory.class.getSimpleName());
            }
            return templateFactories.stream().filter(templateClazz -> templateClazz.isAnnotationPresent((Class<? extends Annotation>) componentClazz));
        }
        return Stream.of(componentClazz);
    }

    private Optional<DynamicFragmentDefinition> dynamicFragment(final Class<?> templateClazz) {
        return Optional.ofNullable(templateClazz.getAnnotation(DynamicFragment.class)).map(dynamicFragment -> {
            final DynamicFragmentDefinition fragmentDefinition = new DynamicFragmentDefinition();
            fragmentDefinition.setMechanism("sitemesh");
            fragmentDefinition.setTtl(String.valueOf(dynamicFragment.ttlInSeconds()));
            return fragmentDefinition;
        });
    }

    private Stream<Class<?>> streamClasses(final Class<?> clazz, final Class<? extends Annotation> annotationClass) {
        return Stream.concat(
                        Arrays.stream(clazz.getDeclaredClasses()),
                        Arrays.stream(clazz.getClasses())
                )
                .distinct()
                .filter(anonymousClass -> anonymousClass.isAnnotationPresent(annotationClass));
    }

    private Stream<Method> streamMethods(final Class<?> clazz, final Class<? extends Annotation> annotationClass) {
        return Stream.concat(
                        Arrays.stream(clazz.getDeclaredMethods()),
                        Arrays.stream(clazz.getMethods())
                )
                .distinct()
                .filter(method -> method.isAnnotationPresent(annotationClass) && !Modifier.isStatic(method.getModifiers()));
    }

    public static class Factory {
        private final TemplateAvailabilityResolver templateAvailabilityResolver;

        @Inject
        public Factory(final TemplateAvailabilityResolver templateAvailabilityResolver) {
            this.templateAvailabilityResolver = templateAvailabilityResolver;
        }

        public TemplateDefinitionProvider create(final Set<Class<?>> templateFactories, final Class<?> factoryClass) {
            return new TemplateDefinitionProvider(
                    Collections.emptyList(),
                    templateAvailabilityResolver,
                    (templateAvailability, template) -> Components.newInstance(DynamicPermissionTemplateDefinition.class, templateAvailability, template),
                    templateAvailability -> Components.newInstance(DynamicComponentAvailabilityResolvingAreaDefinition.class, templateAvailability),
                    templateFactories,
                    Components::newInstance,
                    factoryClass
            );
        }
    }
}
