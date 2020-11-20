package com.namics.oss.magnolia.appbuilder.launcher.group;

import info.magnolia.cms.security.operations.AccessDefinition;
import info.magnolia.ui.api.app.launcherlayout.ConfiguredAppLauncherGroupDefinition;
import org.apache.commons.lang3.StringUtils;

/**
 * App launcher group definition, needed to define
 * app launcher groups without the need to define
 * the apps in the group.
 */
public class SimpleGroupDefinition {

	private String name;
	private String label;
	private String color;
	private boolean permanent = true;
	private boolean clientGroup = false;
	private AccessDefinition permissions;

	public SimpleGroupDefinition(String name) {
		this.name = name;
	}

	public ConfiguredAppLauncherGroupDefinition getConfiguredDefinition() {
		ConfiguredAppLauncherGroupDefinition group = new ConfiguredAppLauncherGroupDefinition();
		group.setName(StringUtils.defaultString(name));
		group.setClientGroup(clientGroup);
		group.setPermanent(permanent);
		if (StringUtils.isNotEmpty(color)) {
			group.setColor(color);
		}
		if (StringUtils.isNotEmpty(label)) {
			group.setLabel(label);
		}
		if (permissions != null) {
			group.setPermissions(permissions);
		}
		return group;
	}

	public String getName() {
		return name;
	}

	public String getLabel() {
		return label;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public SimpleGroupDefinition label(String label) {
		setLabel(label);
		return this;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public SimpleGroupDefinition color(String color) {
		setColor(color);
		return this;
	}

	public boolean isPermanent() {
		return permanent;
	}

	public void setPermanent(boolean permanent) {
		this.permanent = permanent;
	}

	public SimpleGroupDefinition permanent(boolean permanent) {
		setPermanent(permanent);
		return this;
	}

	public boolean isClientGroup() {
		return clientGroup;
	}

	public void setClientGroup(boolean clientGroup) {
		this.clientGroup = clientGroup;
	}

	public SimpleGroupDefinition clientGroup(boolean clientGroup) {
		setClientGroup(clientGroup);
		return this;
	}

	public AccessDefinition getPermissions() {
		return permissions;
	}

	public void setPermissions(AccessDefinition permissions) {
		this.permissions = permissions;
	}

	public SimpleGroupDefinition permissions(AccessDefinition permissions) {
		setPermissions(permissions);
		return this;
	}

}
