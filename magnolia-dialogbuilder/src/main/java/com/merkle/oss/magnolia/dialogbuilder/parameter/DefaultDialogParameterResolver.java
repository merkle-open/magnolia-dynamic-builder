package com.merkle.oss.magnolia.dialogbuilder.parameter;

import info.magnolia.ui.dialog.ConfiguredFormDialogDefinition;
import info.magnolia.ui.editor.ConfiguredFormDefinition;

import javax.jcr.Item;
import javax.jcr.Node;

import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;
import com.merkle.oss.magnolia.formbuilder.parameter.DefaultFormParameterResolver;
import com.merkle.oss.magnolia.formbuilder.parameter.FormParameterResolverFactory;

/**
 * Resolves the following types:
 * <ul>
 * <li>{@link Node} - parent node for add actions
 * <li>{@link ConfiguredFormDefinition} - doesn't contain layout and properties in tabFactory methods
 * <li>{@link ConfiguredFormDialogDefinition} - doesn't contain layout and properties in tabFactory methods
 * </ul>
 */
public class DefaultDialogParameterResolver extends DefaultFormParameterResolver {
    private final ConfiguredFormDialogDefinition<Item> dialog;

    public DefaultDialogParameterResolver(
            final ConfiguredFormDefinition<Item> form,
            final ConfiguredFormDialogDefinition<Item> dialog,
            final FormParameterResolverFactory.FormCreationContext context
    ) {
        super(context, form);
        this.dialog = dialog;
    }

    @Override
    public Object resolveParameter(final Class<?> parameterType) {
        if (parameterType.equals(ConfiguredFormDialogDefinition.class)) {
            return dialog;
        }
        return super.resolveParameter(parameterType);
    }

    public static class Factory implements DialogParameterResolverFactory {
        @Override
        public ParameterResolver create(
                final FormParameterResolverFactory.FormCreationContext context,
                final ConfiguredFormDefinition<Item> form,
                final ConfiguredFormDialogDefinition<Item> dialog
        ) {
            return new DefaultDialogParameterResolver(form, dialog, context);
        }
    }
}
