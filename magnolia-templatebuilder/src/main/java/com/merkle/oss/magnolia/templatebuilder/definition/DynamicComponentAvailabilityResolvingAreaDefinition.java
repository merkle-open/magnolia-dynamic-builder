package com.merkle.oss.magnolia.templatebuilder.definition;

import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.source.ConfigurationSourceTypes;
import info.magnolia.rendering.engine.RenderingEngine;
import info.magnolia.rendering.template.ComponentAvailability;
import info.magnolia.rendering.template.TemplateAvailability;
import info.magnolia.rendering.template.TemplateDefinition;
import info.magnolia.rendering.template.configured.ConfiguredAreaDefinition;
import info.magnolia.rendering.template.registry.TemplateDefinitionRegistry;

import java.util.Map;
import java.util.stream.Collectors;

import jakarta.inject.Inject;
import javax.jcr.Node;

import com.merkle.oss.magnolia.templatebuilder.TemplateAvailabilityResolver;

public class DynamicComponentAvailabilityResolvingAreaDefinition extends ConfiguredAreaDefinition {
    private final RenderingEngine renderingEngine;
    private final TemplateDefinitionRegistry templateDefinitionRegistry;

    @Inject
    public DynamicComponentAvailabilityResolvingAreaDefinition(
            final RenderingEngine renderingEngine,
            final TemplateDefinitionRegistry templateDefinitionRegistry,
            final TemplateAvailability templateAvailability
    ) {
        super(templateAvailability);
        this.renderingEngine = renderingEngine;
        this.templateDefinitionRegistry = templateDefinitionRegistry;
    }

    @Override
    public Map<String, ComponentAvailability> getAvailableComponents() {
        return super.getAvailableComponents().entrySet().stream()
                .filter(entry -> isAvailable(entry.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    protected boolean isAvailable(final String componentId) {
        final DefinitionMetadata componentTemplateMetadata = templateDefinitionRegistry.getProvider(componentId).getMetadata();
        if(componentTemplateMetadata.getConfigurationSourceType() != ConfigurationSourceTypes.code) {
            return true;
        }
        final TemplateDefinition componentTemplate = templateDefinitionRegistry.getProvider(componentId).get();
        final TemplateAvailability<Node> templateAvailability = componentTemplate.getTemplateAvailability();
        final Node node = renderingEngine.getRenderingContext().getCurrentContent();
        return (templateAvailability instanceof TemplateAvailabilityResolver.FallbackAvailabilityResolver || templateAvailability.isAvailable(node, componentTemplate)) && Boolean.FALSE != componentTemplate.getVisible();
    }
}
