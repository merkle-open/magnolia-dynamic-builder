package com.merkle.oss.magnolia.dialogbuilder.parameter;

import info.magnolia.ui.dialog.ConfiguredFormDialogDefinition;

import javax.jcr.Node;

import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;

public interface DialogParameterResolverFactory {
    ParameterResolver create(DialogCreationContext context, ConfiguredFormDialogDefinition<Node> dialogDefinition);
}
