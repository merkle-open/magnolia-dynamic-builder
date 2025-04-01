package com.merkle.oss.magnolia.formbuilder.parameter;

import javax.jcr.Node;

import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;

/**
 * Resolves the following types:
 * <ul>
 * <li>{@link Node} - parent node for add actions
 * </ul>
 */
public class DefaultFormParameterResolver extends ParameterResolver {
    private final FormParameterResolverFactory.FormCreationContext context;

    public DefaultFormParameterResolver(final FormParameterResolverFactory.FormCreationContext context) {
        super(null);
        this.context = context;
    }

    @Override
    public Object resolveParameter(final Class<?> parameterType) {
        if (parameterType.equals(Node.class)) {
            return context.contentNodeProvider().get().orElse(null);
        }
        return super.resolveParameter(parameterType);
    }

    public static class Factory implements FormParameterResolverFactory {
        @Override
        public ParameterResolver create(final FormCreationContext context) {
            return new DefaultFormParameterResolver(context);
        }
    }
}
