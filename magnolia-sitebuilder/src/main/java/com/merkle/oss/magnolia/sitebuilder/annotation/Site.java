package com.merkle.oss.magnolia.sitebuilder.annotation;

import info.magnolia.cms.i18n.AbstractI18nContentSupport;
import info.magnolia.rendering.template.TemplateAvailability;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.merkle.oss.magnolia.builder.annotation.TernaryBoolean;
import com.merkle.oss.magnolia.sitebuilder.DomainMapper;
import com.merkle.oss.magnolia.sitebuilder.DomainPredicate;

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface Site {
    String id();
    String name();
    boolean enabled() default true;
    Templates templates() default @Templates(enabled = false);
    Domain[] domains() default {};
    I18n i18n() default @I18n();
    String theme() default UNDEFINED;
    Mapping[] mappings() default {};
    Cors[] cors() default {};

    @interface Templates {
        boolean enabled() default true;
        String prototypeId() default UNDEFINED;
        Class<? extends TemplateAvailability> availability() default TemplateAvailability.class;
    }

    @interface Domain {
        String name();
        Class<? extends DomainMapper> mapper() default DomainMapper.class;
        Class<? extends DomainPredicate> predicate() default DomainPredicate.class;
        String protocol() default UNDEFINED;
        int port() default -1;
        String context() default UNDEFINED;
    }

    @interface I18n {
        boolean enabled() default true;
        Class<? extends AbstractI18nContentSupport> clazz() default AbstractI18nContentSupport.class;
        Locale fallbackLocale() default @Locale;
        Locale defaultLocale() default @Locale;
        Locale[] locales() default {};

        @interface Locale {
            boolean enabled() default true;
            String country() default UNDEFINED;
            String language() default UNDEFINED;
        }
    }

    @interface Mapping {
        String workspace();
        String uriPrefix() default UNDEFINED;
        String handlePrefix();
    }

    @interface Cors {
        String[] uris() default {};
        String[] allowedOrigins() default {};
        String[] allowedMethods() default { "*" };
        String[] allowedHeaders() default { "*" };
        TernaryBoolean supportsCredentials() default TernaryBoolean.UNSPECIFIED;
        int maxAge() default -1;
    }

    String UNDEFINED = "<sitebuilder-undefined>";
}
