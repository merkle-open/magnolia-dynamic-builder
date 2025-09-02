package com.merkle.oss.magnolia.builder.parameter;


import jakarta.annotation.Nullable;

/**
 * Resolves parameters when invoking methods with reflection.
 */
public abstract class ParameterResolver {
    private final ParameterResolver parent;

    protected ParameterResolver() {
        this(null);
    }

    protected ParameterResolver(@Nullable final ParameterResolver parent) {
        this.parent = parent;
    }

    public Object resolveParameter(final Class<?> parameterType) {
        if (parent != null) {
            return parent.resolveParameter(parameterType);
        } else {
            throw new IllegalStateException("Unable to resolve parameter of type [" + parameterType + "]");
        }
    }
}
