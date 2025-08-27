package com.merkle.oss.magnolia.formbuilder.parameter;

import info.magnolia.ui.editor.ConfiguredFormDefinition;

import javax.jcr.Item;
import javax.jcr.Node;

import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;

/**
 * Resolves the following types:
 * <ul>
 * <li>{@link Node} - parent node for add actions
 * <li>{@link ConfiguredFormDefinition} - doesn't contain layout and properties in tabFactory methods
 * </ul>
 */
public class DefaultFormParameterResolver extends ParameterResolver {
    private final FormParameterResolverFactory.FormCreationContext context;
    private final ConfiguredFormDefinition<Item> form;

    public DefaultFormParameterResolver(
            final FormParameterResolverFactory.FormCreationContext context,
            final ConfiguredFormDefinition<Item> form
    ) {
        super(null);
        this.context = context;
        this.form = form;
    }

    @Override
    public Object resolveParameter(final Class<?> parameterType) {
        if (parameterType.equals(Node.class)) {
            return context.contentNodeProvider().get().orElse(null);
        }
        if (parameterType.equals(ConfiguredFormDefinition.class)) {
            return form;
        }
        return super.resolveParameter(parameterType);
    }

    public static class Factory implements FormParameterResolverFactory {
        @Override
        public ParameterResolver create(final FormCreationContext context, final ConfiguredFormDefinition<Item> form) {
            return new DefaultFormParameterResolver(context, form);
        }
    }
}
