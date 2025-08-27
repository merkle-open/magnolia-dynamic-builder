package com.merkle.oss.magnolia.appbuilder.builder.detail.parameter;

import info.magnolia.ui.editor.ConfiguredFormDefinition;

import javax.jcr.Item;
import javax.jcr.Node;

import com.merkle.oss.magnolia.appbuilder.builder.detail.DetailSubApp;
import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;
import com.merkle.oss.magnolia.formbuilder.parameter.DefaultFormParameterResolver;
import com.merkle.oss.magnolia.formbuilder.parameter.FormParameterResolverFactory;

/**
 * Resolves the following types:
 * <ul>
 * <li>{@link Node} - parent node for add actions
 * <li>{@link ConfiguredFormDefinition} - doesn't contain layout and form in tabFactory methods
 * <li>{@link DetailSubApp.Definition} - doesn't contain layout and form in tabFactory methods
 * </ul>
 */
public class DefaultDetailAppParameterResolver extends DefaultFormParameterResolver {
    private final DetailSubApp.Definition detailSubAppDefinition;

    public DefaultDetailAppParameterResolver(
            final DetailSubApp.Definition detailSubAppDefinition,
            final ConfiguredFormDefinition<Item> formDefinition,
            final FormParameterResolverFactory.FormCreationContext context
    ) {
        super(context, formDefinition);
        this.detailSubAppDefinition = detailSubAppDefinition;
    }

    @Override
    public Object resolveParameter(final Class<?> parameterType) {
        if (parameterType.equals(DetailSubApp.Definition.class)) {
            return detailSubAppDefinition;
        }
        return super.resolveParameter(parameterType);
    }

    public static class Factory implements DetailAppParameterResolverFactory {
        @Override
        public ParameterResolver create(
                final FormParameterResolverFactory.FormCreationContext context,
                final ConfiguredFormDefinition<Item> formDefinition,
                final DetailSubApp.Definition detailSubAppDefinition
        ) {
            return new DefaultDetailAppParameterResolver(detailSubAppDefinition, formDefinition, context);
        }
    }
}
