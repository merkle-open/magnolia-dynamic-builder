package com.merkle.oss.magnolia.appbuilder.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.merkle.oss.magnolia.builder.annotation.TernaryBoolean;
import com.merkle.oss.magnolia.builder.annotation.Unspecified;

/**
 * Annotation to provide the search result supplier for an App.
 * <p>
 * - Used in classes annotated with {@link AppFactory}, optional.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SearchResultSupplier {
    String workspace();
    String id() default Unspecified.STRING;
    String appName() default Unspecified.STRING;
    String[] nodeTypes() default {};
    String nodeNameProperty() default Unspecified.STRING;
    TernaryBoolean fullTextSearch() default TernaryBoolean.UNSPECIFIED;
    TernaryBoolean propertySearch() default TernaryBoolean.UNSPECIFIED;
    int resultLimit() default Unspecified.INT;
    int timeoutSeconds() default Unspecified.INT;
    String subAppName() default Unspecified.STRING;
    String detailSuppAppName() default Unspecified.STRING;
    TernaryBoolean isEnabled() default TernaryBoolean.UNSPECIFIED;
    String label() default Unspecified.STRING;
    String icon() default Unspecified.STRING;
    String rootPath() default Unspecified.STRING;
    Order order() default @Order();

    @interface Order {
        String property() default Unspecified.STRING;
        Direction direction() default Direction.UNSPECIFIED;

        enum Direction {
            ASC,
            DESC,
            UNSPECIFIED
        }
    }
}
