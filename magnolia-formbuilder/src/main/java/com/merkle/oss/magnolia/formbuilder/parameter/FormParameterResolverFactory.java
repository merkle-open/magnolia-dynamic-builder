package com.merkle.oss.magnolia.formbuilder.parameter;

import info.magnolia.ui.editor.ConfiguredFormDefinition;

import java.util.Optional;

import javax.jcr.Item;
import javax.jcr.Node;

import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;

import jakarta.inject.Provider;

public interface FormParameterResolverFactory {
    ParameterResolver create(FormCreationContext context, ConfiguredFormDefinition<Item> formDefinition);

    /**
     * Holds the parameters required during dialog creation and validation.
     */
    record FormCreationContext(
            Provider<Optional<Node>> contentNodeProvider
    ) {}
}
