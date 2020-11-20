package com.namics.oss.magnolia.appbuilder.builder.generated.availability;

import info.magnolia.cms.security.operations.AccessDefinition;
import info.magnolia.ui.api.availability.AvailabilityRuleDefinition;
import info.magnolia.ui.api.availability.ConfiguredAvailabilityDefinition;
import java.lang.String;
import java.util.Arrays;
import java.util.Collection;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.278674"
)
public class AvailabilityBuilder extends ConfiguredAvailabilityDefinition {
	public AvailabilityBuilder properties(boolean properties) {
		this.setProperties(properties);
		return this;
	}

	public AvailabilityBuilder root(boolean root) {
		this.setRoot(root);
		return this;
	}

	public AvailabilityBuilder nodes(boolean nodes) {
		this.setNodes(nodes);
		return this;
	}

	public AvailabilityBuilder multiple(boolean multiple) {
		this.setMultiple(multiple);
		return this;
	}

	public AvailabilityBuilder nodeTypes(Collection<String> nodeTypes) {
		this.setNodeTypes(nodeTypes);
		return this;
	}

	public AvailabilityBuilder nodeType(String nodeType) {
		this.addNodeType(nodeType);
		return this;
	}

	public AvailabilityBuilder access(AccessDefinition access) {
		this.setAccess(access);
		return this;
	}

	public AvailabilityBuilder writePermissionRequired(boolean writePermissionRequired) {
		this.setWritePermissionRequired(writePermissionRequired);
		return this;
	}

	public AvailabilityBuilder rules(Collection<? extends AvailabilityRuleDefinition> rules) {
		this.setRules(rules);
		return this;
	}

	public AvailabilityBuilder nodeTypes(String... nodeTypes) {
		this.setNodeTypes(Arrays.asList(nodeTypes));
		return this;
	}

	public AvailabilityBuilder rules(AvailabilityRuleDefinition... rules) {
		this.setRules(Arrays.asList(rules));
		return this;
	}
}
