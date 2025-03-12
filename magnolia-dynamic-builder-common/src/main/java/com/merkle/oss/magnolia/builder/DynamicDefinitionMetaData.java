package com.merkle.oss.magnolia.builder;

import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.DefinitionMetadataBuilder;
import info.magnolia.config.source.ConfigurationSourceTypes;

import java.util.Objects;

import org.apache.commons.lang3.StringUtils;

public class DynamicDefinitionMetaData extends DefinitionMetaDataWrapper {
    private final Class<?> factoryClass;

    public DynamicDefinitionMetaData(
            final DefinitionMetadata wrapped,
            final Class<?> factoryClass
    ) {
        super(wrapped);
        this.factoryClass = factoryClass;
    }

    public Class<?> getFactoryClass() {
        return factoryClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        if (!super.equals(o)) {
            return false;
        }
        DynamicDefinitionMetaData that = (DynamicDefinitionMetaData) o;
        return Objects.equals(factoryClass, that.factoryClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), factoryClass);
    }

    @Override
    public String toString() {
        return "DynamicDefinitionMetaData{" +
                "factoryClass=" + factoryClass +
                "} " + super.toString();
    }

    public static class Builder extends DefinitionMetadataBuilder {
        private final Class<?> factoryClass;
        private final String id;

        public Builder(final Class<?> factoryClass, final String id) {
            this.factoryClass = factoryClass;
            this.id = id;
            location(generateLocation(factoryClass));
            module(generateModule(id));
            relativeLocation(generateRelativeLocation(id));
            configurationSourceType(ConfigurationSourceTypes.code);
            name(generateName(id));
        }

        @Override
        protected String buildReferenceId() {
            return id;
        }

        protected String generateLocation(final Class<?> factoryClass) {
            return factoryClass.getName();
        }

        protected String generateModule(final String id) {
            return id.contains(":") ? StringUtils.substringBefore(id, ":") : null;
        }

        protected String generateRelativeLocation(final String id) {
            return id.contains(":") ? StringUtils.substringAfter(id, ":") : id;
        }

        protected String generateName(final String id) {
            final String relativeLocation = generateRelativeLocation(id);
            return relativeLocation.indexOf('/') != -1 ? StringUtils.substringAfterLast(relativeLocation, "/") : relativeLocation;
        }

        @Override
        public DynamicDefinitionMetaData build() {
            return new DynamicDefinitionMetaData(super.build(), factoryClass);
        }
    }
}
