package com.merkle.oss.magnolia.templatebuilder.definition;

import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.rendering.engine.RenderingEngine;
import info.magnolia.rendering.template.TemplateAvailability;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import javax.jcr.Node;

import com.merkle.oss.magnolia.templatebuilder.annotation.Template;

public class DynamicPermissionTemplateDefinition extends ConfiguredTemplateDefinition {
    private final RenderingEngine renderingEngine;
    private final ComponentProvider componentProvider;
    private final Template template;

    @Inject
    public DynamicPermissionTemplateDefinition(
            final RenderingEngine renderingEngine,
            final ComponentProvider componentProvider,
            final TemplateAvailability templateAvailability,
            final Template template
    ) {
        super(templateAvailability);
        this.renderingEngine = renderingEngine;
        this.componentProvider = componentProvider;
        this.template = template;
    }

    @Override
    public Boolean getVisible() {
        return Boolean.FALSE != super.getVisible() && evaluateHandlingFallback(template.visible());
    }

    @Override
    public Boolean getDeletable() {
        return Boolean.FALSE != super.getDeletable() && evaluateHandlingFallback(template.deletable());
    }

    @Override
    public Boolean getMoveable() {
        return Boolean.FALSE != super.getMoveable() && evaluateHandlingFallback(template.moveable());
    }

    @Override
    public Boolean getWritable() {
        return Boolean.FALSE != super.getWritable() && evaluateHandlingFallback(template.writable());
    }

    @Override
    public Boolean getEditable() {
        return Boolean.FALSE != super.getEditable() && evaluateHandlingFallback(template.editable());
    }

    private boolean evaluateHandlingFallback(final Template.Permission permission) {
        return getPermission(permission).map(this::evaluate).orElse(true);
    }

    private boolean evaluate(final Template.Permission permission) {
        final Node node = renderingEngine.getRenderingContext().getCurrentContent();
        final Map<String, String> params = Arrays
                .stream(permission.params())
                .collect(Collectors.toMap(
                        Template.Permission.Param::key,
                        Template.Permission.Param::value
                ));
        return componentProvider.newInstance(permission.predicate(), params).test(node, this);
    }

    private Optional<Template.Permission> getPermission(final Template.Permission permission) {
        return Optional.of(permission).filter(p -> p.predicate() != PermissionPredicate.Unspecified.class).or(() ->
                Optional.of(template.permission()).filter(p -> p.predicate() != PermissionPredicate.Unspecified.class)
        );
    }
}
