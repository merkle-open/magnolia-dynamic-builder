package com.namics.oss.magnolia.appbuilder.launcher.group;

import java.util.Map;

/**
 * Helper to reference already existing app launcher groups.
 */
public class LauncherGroup {
	public static final String EDIT = "edit";
	public static final String TARGET = "target";
	public static final String SETUP = "manage";
	public static final String TOOLS = "tools";
	public static final String DEV = "dev";
	public static final String WEB_DEV = "stk";

	private LauncherGroup() {
	}

	public static Map<String, SimpleGroupDefinition> getMagnoliaDefaultGroups() {
		return Map.of(
				EDIT, new SimpleGroupDefinition("edit"),
				TARGET, new SimpleGroupDefinition("target"),
				SETUP, new SimpleGroupDefinition("manage"),
				TOOLS, new SimpleGroupDefinition("tools"),
				DEV, new SimpleGroupDefinition("dev"),
				WEB_DEV, new SimpleGroupDefinition("stk")
		);
	}

}
