package com.namics.oss.magnolia.appbuilder.builder.generated.contentconnector;

import info.magnolia.ui.vaadin.integration.contentconnector.ConfiguredContentConnectorDefinition;
import info.magnolia.ui.vaadin.integration.contentconnector.ContentConnector;
import java.lang.Class;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.938141"
)
public class ContentConnectorBuilder extends ConfiguredContentConnectorDefinition {
	public ContentConnectorBuilder implementationClass(
			Class<? extends ContentConnector> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}
}
