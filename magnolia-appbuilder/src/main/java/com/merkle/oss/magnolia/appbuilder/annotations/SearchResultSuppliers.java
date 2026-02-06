package com.merkle.oss.magnolia.appbuilder.annotations;

import com.google.inject.BindingAnnotation;

import java.lang.annotation.*;

@BindingAnnotation
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER})
public @interface SearchResultSuppliers {
}
