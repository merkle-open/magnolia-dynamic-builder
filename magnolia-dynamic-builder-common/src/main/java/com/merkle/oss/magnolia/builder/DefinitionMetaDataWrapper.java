package com.merkle.oss.magnolia.builder;

import info.magnolia.config.registry.DefinitionMetadata;
import info.magnolia.config.registry.DefinitionType;
import info.magnolia.config.source.ConfigurationSourceType;
import info.magnolia.config.source.Deprecation;
import info.magnolia.resourceloader.Resource;

import java.util.Objects;
import java.util.Optional;

public class DefinitionMetaDataWrapper implements DefinitionMetadata {
    private final DefinitionMetadata wrapped;

    public DefinitionMetaDataWrapper(final DefinitionMetadata wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public DefinitionType getType() {
        return wrapped.getType();
    }

    @Override
    public String getReferenceId() {
        return wrapped.getReferenceId();
    }

    @Override
    public String getLocation() {
        return wrapped.getLocation();
    }

    @Override
    public ConfigurationSourceType getConfigurationSourceType() {
        return wrapped.getConfigurationSourceType();
    }

    @Override
    public String getModule() {
        return wrapped.getModule();
    }

    @Override
    public String getRelativeLocation() {
        return wrapped.getRelativeLocation();
    }

    @Override
    public String getName() {
        return wrapped.getName();
    }

    @Override
    public Optional<Deprecation> getDeprecation() {
        return wrapped.getDeprecation();
    }

    @Override
    public Class<? extends Resource> getResourceClass() {
        return wrapped.getResourceClass();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        DefinitionMetaDataWrapper that = (DefinitionMetaDataWrapper) o;
        return Objects.equals(wrapped, that.wrapped);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(wrapped);
    }

    @Override
    public String toString() {
        return "DefinitionMetaDataWrapper{" +
                "wrapped=" + wrapped +
                '}';
    }
}
