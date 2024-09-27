package com.merkle.oss.magnolia.templatebuilder.parameter;

import info.magnolia.rendering.template.configured.ConfiguredAreaDefinition;
import info.magnolia.rendering.template.configured.ConfiguredTemplateDefinition;

import javax.jcr.Node;

import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;

public interface AutoGeneratorParameterResolverFactory {
    ParameterResolver create(Node node, ConfiguredTemplateDefinition template, ConfiguredAreaDefinition area);
}
