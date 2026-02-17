package com.merkle.oss.magnolia.dialogbuilder.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.merkle.oss.magnolia.builder.annotation.Unspecified;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface DialogFactory {
    /**
     * Id of the dialog.
     */
    String value();
    String label() default Unspecified.STRING;
}
