package com.merkle.oss.magnolia.formbuilder.parameter;

import java.util.Optional;

import javax.inject.Provider;
import javax.jcr.Node;

import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;

public interface FormParameterResolverFactory {
    ParameterResolver create(FormCreationContext context);

    /**
     * Holds the parameters required during dialog creation and validation.
     */
    record FormCreationContext(
            Provider<Optional<Node>> contentNodeProvider
    ) {}
}
