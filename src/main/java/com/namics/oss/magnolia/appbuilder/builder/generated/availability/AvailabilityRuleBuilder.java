package com.namics.oss.magnolia.appbuilder.builder.generated.availability;

import info.magnolia.ui.api.availability.AvailabilityRule;
import info.magnolia.ui.api.availability.ConfiguredAvailabilityRuleDefinition;
import java.lang.Class;
import javax.annotation.processing.Generated;

@Generated(
		value = "DefinitionExtender",
		date = "2020-06-15T13:24:01.284341"
)
public class AvailabilityRuleBuilder extends ConfiguredAvailabilityRuleDefinition {
	public AvailabilityRuleBuilder negate(boolean negate) {
		this.setNegate(negate);
		return this;
	}

	public AvailabilityRuleBuilder implementationClass(
			Class<? extends AvailabilityRule> implementationClass) {
		this.setImplementationClass(implementationClass);
		return this;
	}
}
