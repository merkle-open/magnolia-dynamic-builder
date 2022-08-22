package com.namics.oss.magnolia.appbuilder.launcher.group;

import info.magnolia.ui.api.app.launcherlayout.ConfiguredAppLauncherGroupDefinition;

/**
 * App launcher group definition, needed to define
 * app launcher groups without the need to define
 * the apps in the group.
 */
public class SimpleGroupDefinition extends ConfiguredAppLauncherGroupDefinition {

	public SimpleGroupDefinition(final String name) {
		setName(name);
	}
}
