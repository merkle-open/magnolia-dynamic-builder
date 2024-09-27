package com.merkle.oss.magnolia.templatebuilder.annotation.area;

import info.magnolia.rendering.template.configured.ConfiguredInheritance;

/**
 * Enumeration for modes of inheriting components.
 * <ul>
 * <li><b>ALL</b> All components are inherited</li>
 * <li><b>NONE</b> No components are inherited</li>
 * <li><b>FILTERED</b> Only components having a property <code>inheritable</code> set to true are inherited</li>
 * </ul>
 *
 * @see Inherits
 * @see info.magnolia.rendering.template.InheritanceConfiguration
 * @see ConfiguredInheritance
 */
public enum ComponentInheritanceMode {
    ALL(ConfiguredInheritance.COMPONENTS_ALL),
    NONE(ConfiguredInheritance.COMPONENTS_NONE),
    FILTERED(ConfiguredInheritance.COMPONENTS_FILTERED);

    private final String configurationFormat;

    ComponentInheritanceMode(String configurationFormat) {
        this.configurationFormat = configurationFormat;
    }

    public String getConfigurationFormat() {
        return configurationFormat;
    }
}
