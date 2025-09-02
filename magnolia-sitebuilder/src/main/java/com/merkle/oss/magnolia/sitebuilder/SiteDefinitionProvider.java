package com.merkle.oss.magnolia.sitebuilder;

import static java.util.function.Predicate.not;

import info.magnolia.cms.beans.config.URI2RepositoryMapping;
import info.magnolia.cms.i18n.AbstractI18nContentSupport;
import info.magnolia.cms.i18n.I18nContentSupport;
import info.magnolia.cms.i18n.LocaleDefinition;
import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.Registry;
import info.magnolia.config.registry.decoration.DefinitionDecorator;
import info.magnolia.module.site.ConfiguredSite;
import info.magnolia.module.site.Domain;
import info.magnolia.module.site.registry.DefinitionTypes;
import info.magnolia.module.site.templates.ConfiguredTemplateSettings;
import info.magnolia.module.site.templates.ReferencingPrototypeTemplateSettings;
import info.magnolia.module.site.templates.TemplateSettings;
import info.magnolia.module.site.theme.ThemeReference;
import info.magnolia.objectfactory.ComponentProvider;
import info.magnolia.objectfactory.Components;
import info.magnolia.rendering.template.TemplateAvailability;
import info.magnolia.rendering.template.registry.TemplateDefinitionRegistry;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.merkle.oss.magnolia.builder.AbstractDynamicDefinitionProvider;
import com.merkle.oss.magnolia.builder.DynamicDefinitionMetaData;
import com.merkle.oss.magnolia.sitebuilder.annotation.Extends;
import com.merkle.oss.magnolia.sitebuilder.annotation.Site;

import jakarta.annotation.Nullable;
import jakarta.inject.Inject;

public class SiteDefinitionProvider extends AbstractDynamicDefinitionProvider<info.magnolia.module.site.Site> {
    private final TemplateDefinitionRegistry templateDefinitionRegistry;
    private final ComponentProvider componentProvider;
    private final Site annotation;
    @Nullable
    private final Extends extendsAnnotation;
    private final DefinitionMetadata metadata;

    public SiteDefinitionProvider(
            final List<DefinitionDecorator<info.magnolia.module.site.Site>> decorators,
            final TemplateDefinitionRegistry templateDefinitionRegistry,
            final ComponentProvider componentProvider,
            final Class<?> factoryClass
    ) {
        super(decorators);
        this.templateDefinitionRegistry = templateDefinitionRegistry;
        this.componentProvider = componentProvider;
        this.annotation = factoryClass.getDeclaredAnnotation(Site.class);
        this.extendsAnnotation = factoryClass.getDeclaredAnnotation(Extends.class);
        this.metadata = new SiteDefinitionMetaDataBuilder(factoryClass, annotation.id())
                .type(DefinitionTypes.SITE)
                .build();
    }

    @Override
    public DefinitionMetadata getMetadata() {
        return metadata;
    }

    @Override
    public info.magnolia.module.site.Site getInternal() throws Registry.InvalidDefinitionException {
        final ConfiguredSite site = new ConfiguredSite();
        site.setName(annotation.name());
        site.setEnabled(annotation.enabled());
        streamSites().map(this::getTemplates).flatMap(Optional::stream).findFirst().ifPresent(site::setTemplates);
        site.setDomains(streamSites().flatMap(this::getDomains).toList());
        site.setMappings(streamSites().flatMap(this::getMappings).collect(Collectors.toMap(
                URI2RepositoryMapping::getRepository,
                Function.identity(),
                (annotationMapping, extendsAnnotationMapping) -> annotationMapping
        )));
        streamSites().map(this::getTheme).flatMap(Optional::stream).findFirst().ifPresent(site::setTheme);
        streamSites().map(this::getI18n).flatMap(Optional::stream).findFirst().ifPresent(site::setI18n);
        return site;
    }

    private Stream<Site> streamSites() {
        return Stream.concat(
                Stream.of(annotation),
                streamExtendedSites(extendsAnnotation)
        );
    }

    private Stream<Site> streamExtendedSites(@Nullable final Extends annotation) {
        return Stream.ofNullable(annotation).map(Extends::value).flatMap(clazz -> Stream.concat(
                Stream.of(clazz.getDeclaredAnnotation(Site.class)),
                streamExtendedSites(clazz.getDeclaredAnnotation(Extends.class))
        ));
    }

    private Optional<I18nContentSupport> getI18n(final Site annotation) {
        return Optional
                .of(annotation.i18n())
                .filter(i18n -> !Objects.equals(AbstractI18nContentSupport.class, i18n.clazz()))
                .map(this::create);
    }
    private AbstractI18nContentSupport create(final Site.I18n i18n) {
        final AbstractI18nContentSupport i18nContentSupport = componentProvider.newInstance(i18n.clazz());
        i18nContentSupport.setEnabled(i18n.enabled());
        create(i18n.fallbackLocale()).filter(LocaleDefinition::isEnabled).map(LocaleDefinition::getLocale).ifPresent(i18nContentSupport::setFallbackLocale);
        create(i18n.defaultLocale()).filter(LocaleDefinition::isEnabled).map(LocaleDefinition::getLocale).ifPresent(i18nContentSupport::setDefaultLocale);
        i18nContentSupport.setLocales(Arrays.stream(i18n.locales()).map(this::create).flatMap(Optional::stream).filter(LocaleDefinition::isEnabled).toList());
        return i18nContentSupport;
    }

    private Optional<LocaleDefinition> create(final Site.I18n.Locale locale) {
        return Optional.of(locale.language())
                .filter(not(Site.UNDEFINED::equals))
                .map(language ->
                        LocaleDefinition.make(
                                language,
                                Optional.of(locale.country()).filter(not(Site.UNDEFINED::equals)).orElse(null),
                                locale.enabled()
                        )
                );
    }

    private Optional<ThemeReference> getTheme(final Site annotation) {
        return Optional.of(annotation.theme())
                .filter(not(Site.UNDEFINED::equals))
                .map(theme -> {
                    final ThemeReference themeReference = new ThemeReference();
                    themeReference.setName(theme);
                    return themeReference;
                });
    }

    private Stream<URI2RepositoryMapping> getMappings(final Site annotation) {
        return Arrays
                .stream(annotation.mappings())
                .map(this::create);
    }
    private URI2RepositoryMapping create(final Site.Mapping mapping) {
        return new URI2RepositoryMapping(
                Optional.of(mapping.uriPrefix()).filter(not(Site.UNDEFINED::equals)).orElse(""),
                mapping.workspace(),
                mapping.handlePrefix()
        );
    }

    private Stream<Domain> getDomains(final Site annotation) {
        return Arrays
                .stream(annotation.domains())
                .map(this::create)
                .flatMap(Optional::stream);
    }
    private Optional<Domain> create(final Site.Domain annotation) {
        final Domain domain = new Domain(annotation.name());
        Optional.of(annotation.context()).filter(not(Site.UNDEFINED::equals)).ifPresent(domain::setContext);
        Optional.of(annotation.protocol()).filter(not(Site.UNDEFINED::equals)).ifPresent(domain::setProtocol);
        Optional.of(annotation.port()).filter(port -> port != -1).ifPresent(domain::setPort);

        final DomainMapper mapper = Optional.of(annotation.mapper())
                .filter(not(DomainMapper.class::equals))
                .map(domainMapperClass -> (DomainMapper)componentProvider.newInstance(domainMapperClass))
                .orElseGet(() -> (input) -> input);
        final DomainPredicate domainPredicate = Optional.of(annotation.predicate())
                .filter(not(DomainPredicate.class::equals))
                .map(predicateClass -> (DomainPredicate)componentProvider.newInstance(predicateClass))
                .orElseGet(() -> ignored -> true);
        return Optional.of(mapper.apply(domain)).filter(domainPredicate);
    }

    private Optional<TemplateSettings> getTemplates(final Site annotation) {
        if(annotation.templates().enabled()) {
            final ConfiguredTemplateSettings templateSettings = create(annotation.templates());
            templateSettings.setAvailability(createTemplateAvailability(annotation.templates()));
            return Optional.of(templateSettings);
        }
        return Optional.empty();
    }
    private ConfiguredTemplateSettings create(final Site.Templates annotation) {
        return Optional.of(annotation.prototypeId())
                .filter(not(Site.UNDEFINED::equals))
                .map(id -> {
                    final ReferencingPrototypeTemplateSettings refTemplateSettings = new ReferencingPrototypeTemplateSettings(templateDefinitionRegistry);
                    refTemplateSettings.setPrototypeId(id);
                    return (ConfiguredTemplateSettings) refTemplateSettings;
                })
                .orElseGet(ConfiguredTemplateSettings::new);
    }
    private <T> TemplateAvailability<T> createTemplateAvailability(final Site.Templates annotation) {
        return Optional
                .of(annotation.availability()).filter(not(TemplateAvailability.class::equals))
                .map(clazz -> (TemplateAvailability<T>) componentProvider.newInstance(clazz))
                .orElseGet(() -> (node, templateDefinition) -> false);
    }

    protected static class SiteDefinitionMetaDataBuilder extends DynamicDefinitionMetaData.Builder {
        public SiteDefinitionMetaDataBuilder(final Class<?> factoryClass, final String id) {
            super(factoryClass, id);
        }
        @Override
        protected String buildReferenceId() {
            return getName();
        }
    }

    public static class Factory {
        private final TemplateDefinitionRegistry templateDefinitionRegistry;

        @Inject
        public Factory(final TemplateDefinitionRegistry templateDefinitionRegistry) {
            this.templateDefinitionRegistry = templateDefinitionRegistry;
        }

        public SiteDefinitionProvider create(final Class<?> factoryClass) {
            return new SiteDefinitionProvider(
                    Collections.emptyList(),
                    templateDefinitionRegistry,
                    Components.getComponentProvider(),
                    factoryClass
            );
        }
    }
}
