package com.merkle.oss.magnolia.appbuilder.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.merkle.oss.magnolia.builder.annotation.TernaryBoolean;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface ChooserDialogFactory {
    String id();
    String label();
    TernaryBoolean hasSearchBar() default TernaryBoolean.UNSPECIFIED;

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.METHOD})
    @interface Order {
        int value();
    }
}
