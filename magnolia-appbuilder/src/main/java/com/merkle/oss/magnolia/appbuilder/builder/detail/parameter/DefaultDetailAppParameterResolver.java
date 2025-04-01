package com.merkle.oss.magnolia.appbuilder.builder.detail.parameter;

import info.magnolia.ui.contentapp.detail.DetailDescriptor;

import javax.jcr.Node;

import com.merkle.oss.magnolia.appbuilder.builder.detail.DetailSubApp;
import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;
import com.merkle.oss.magnolia.formbuilder.parameter.DefaultFormParameterResolver;
import com.merkle.oss.magnolia.formbuilder.parameter.FormParameterResolverFactory;

/**
 * Resolves the following types:
 * <ul>
 * <li>{@link Node} - parent node for add actions
 * <li>{@link DetailDescriptor} - doesn't contain layout and form in tabFactory methods
 * </ul>
 */
public class DefaultDetailAppParameterResolver extends DefaultFormParameterResolver {
    private final DetailSubApp.Definition detailSubAppDefinition;

    public DefaultDetailAppParameterResolver(
            final DetailSubApp.Definition detailSubAppDefinition,
            final FormParameterResolverFactory.FormCreationContext context
    ) {
        super(context);
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
        public ParameterResolver create(final FormParameterResolverFactory.FormCreationContext context, final DetailSubApp.Definition detailSubAppDefinition) {
            return new DefaultDetailAppParameterResolver(detailSubAppDefinition, context);
        }
    }
}
