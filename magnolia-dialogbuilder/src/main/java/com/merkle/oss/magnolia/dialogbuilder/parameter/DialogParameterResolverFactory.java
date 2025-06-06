package com.merkle.oss.magnolia.dialogbuilder.parameter;

import info.magnolia.ui.dialog.ConfiguredFormDialogDefinition;

import javax.jcr.Item;

import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;
import com.merkle.oss.magnolia.formbuilder.parameter.FormParameterResolverFactory;

public interface DialogParameterResolverFactory {
    ParameterResolver create(FormParameterResolverFactory.FormCreationContext context, ConfiguredFormDialogDefinition<Item> dialogDefinition);
}
