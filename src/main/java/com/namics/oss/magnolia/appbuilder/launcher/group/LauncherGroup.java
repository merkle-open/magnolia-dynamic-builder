package com.namics.oss.magnolia.appbuilder.launcher.group;

import com.google.common.collect.ImmutableMap;

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

	public static Map<String, SimpleGroupDefinition> getAllExistingGroups() {
		return ImmutableMap.<String, SimpleGroupDefinition>builder()
				.put(EDIT, new SimpleGroupDefinition("edit"))
				.put(TARGET, new SimpleGroupDefinition("target"))
				.put(SETUP, new SimpleGroupDefinition("manage"))
				.put(TOOLS, new SimpleGroupDefinition("tools"))
				.put(DEV, new SimpleGroupDefinition("dev"))
				.put(WEB_DEV, new SimpleGroupDefinition("stk"))
				.build();
	}

}
