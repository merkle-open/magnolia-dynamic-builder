package com.merkle.oss.magnolia.appbuilder.action.rule;

import info.magnolia.ui.api.availability.AvailabilityRuleType;
import info.magnolia.ui.api.availability.ConfiguredAvailabilityRuleDefinition;

@AvailabilityRuleType("permissionRequiredRule")
public class PermissionRequiredRuleDefinition extends ConfiguredAvailabilityRuleDefinition {
	private final long requiredPermissions;

	public PermissionRequiredRuleDefinition(final long requiredPermissions) {
		this.requiredPermissions = requiredPermissions;
		this.setImplementationClass(PermissionRequiredRule.class);
	}

	public long getRequiredPermissions() {
		return requiredPermissions;
	}
}
