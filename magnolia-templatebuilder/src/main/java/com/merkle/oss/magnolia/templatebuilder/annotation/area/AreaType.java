package com.merkle.oss.magnolia.templatebuilder.annotation.area;

import info.magnolia.rendering.template.AreaDefinition;

/**
 * Enumeration of area types.
 * <ul>
 * <li><b>NO_COMPONENT</b> The area does not have any components within it</li>
 * <li><b>LIST</b> The area can have zero or more components within it</li>
 * <li><b>SINGLE</b> The area can have zero or one component within it</li>
 * </ul>
 *
 * @see Area
 */
public enum AreaType {
    NO_COMPONENT(AreaDefinition.TYPE_NO_COMPONENT),
    LIST(AreaDefinition.TYPE_LIST),
    SINGLE(AreaDefinition.TYPE_SINGLE);

    private final String definitionFormat;

    AreaType(final String definitionFormat) {
        this.definitionFormat = definitionFormat;
    }

    public String getDefinitionFormat() {
        return definitionFormat;
    }
}
