package com.merkle.oss.magnolia.appbuilder.annotations;

import info.magnolia.periscope.search.jcr.JcrSearchResultSupplierOrder;

import java.lang.annotation.*;

/**
 * Annotation to provide the search result supplier for an App.
 * <p>
 * - Used in classes annotated with {@link AppFactory}, optional.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface SearchResultSupplier {
    String name() default UNDEFINED;
    String appName() default UNDEFINED;
    String[] nodeTypes();
    String nodeNameProperty() default "jcrName";
    boolean fullTextSearch() default true;
    boolean propertySearch() default false;
    int resultLimit() default 100;
    int timeoutSeconds() default 15;
    String subAppName() default "browser";
    String detailSuppAppName() default "detail";
    boolean isEnabled() default true;
    String label() default UNDEFINED;
    String icon() default UNDEFINED;
    String workspace();
    String rootPath() default "/";
    Order order() default @Order();

    @interface Order {
        String property() default UNDEFINED;
        JcrSearchResultSupplierOrder.Direction direction() default JcrSearchResultSupplierOrder.Direction.DESC;
    }

    String UNDEFINED = "<appbuilder-searchresultsupplier-undefined>";
}
