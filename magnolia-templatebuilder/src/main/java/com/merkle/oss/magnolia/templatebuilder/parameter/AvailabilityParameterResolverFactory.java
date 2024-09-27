package com.merkle.oss.magnolia.templatebuilder.parameter;

import info.magnolia.rendering.template.TemplateDefinition;

import javax.jcr.Node;

import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;

public interface AvailabilityParameterResolverFactory {
    ParameterResolver create(Node node, TemplateDefinition templateDefinition);
}
