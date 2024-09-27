package com.merkle.oss.magnolia.templatebuilder.annotation.area;

import info.magnolia.rendering.template.configured.ConfiguredInheritance;

/**
 * Enumeration for modes of inheriting properties.
 * <ul>
 * <li><b>ALL</b> All properties are inherited</li>
 * <li><b>NONE</b> No properties are inherited</li>
 * </ul>
 *
 * @see Inherits
 * @see info.magnolia.rendering.template.InheritanceConfiguration
 * @see ConfiguredInheritance
 */
public enum PropertyInheritanceMode {
    ALL(ConfiguredInheritance.PROPERTIES_ALL),
    NONE(ConfiguredInheritance.PROPERTIES_NONE);

    private final String configurationFormat;

    PropertyInheritanceMode(final String configurationFormat) {
        this.configurationFormat = configurationFormat;
    }

    public String getConfigurationFormat() {
        return configurationFormat;
    }
}
