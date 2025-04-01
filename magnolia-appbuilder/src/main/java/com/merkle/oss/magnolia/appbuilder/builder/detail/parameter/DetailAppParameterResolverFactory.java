package com.merkle.oss.magnolia.appbuilder.builder.detail.parameter;

import com.merkle.oss.magnolia.appbuilder.builder.detail.DetailSubApp;
import com.merkle.oss.magnolia.builder.parameter.ParameterResolver;
import com.merkle.oss.magnolia.formbuilder.parameter.FormParameterResolverFactory;

public interface DetailAppParameterResolverFactory {
    ParameterResolver create(FormParameterResolverFactory.FormCreationContext context, DetailSubApp.Definition detailSubAppDefinition);
}
