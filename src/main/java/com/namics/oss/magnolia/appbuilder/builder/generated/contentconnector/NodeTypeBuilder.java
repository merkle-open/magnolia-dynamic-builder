package com.namics.oss.magnolia.appbuilder.builder.generated.contentconnector;

import info.magnolia.ui.vaadin.integration.contentconnector.ConfiguredNodeTypeDefinition;
import java.lang.String;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.943553"
)
public class NodeTypeBuilder extends ConfiguredNodeTypeDefinition {
	public NodeTypeBuilder name(String name) {
		this.setName(name);
		return this;
	}

	public NodeTypeBuilder strict(boolean strict) {
		this.setStrict(strict);
		return this;
	}

	public NodeTypeBuilder hideInList(boolean hideInList) {
		this.setHideInList(hideInList);
		return this;
	}

	public NodeTypeBuilder icon(String icon) {
		this.setIcon(icon);
		return this;
	}
}
