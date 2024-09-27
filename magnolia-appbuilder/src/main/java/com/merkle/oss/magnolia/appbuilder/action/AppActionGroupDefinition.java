package com.merkle.oss.magnolia.appbuilder.action;

import java.util.List;

public class AppActionGroupDefinition {
	private final String name;
	private final List<AppActionDefinition> actions;

	public AppActionGroupDefinition(final String name, final List<AppActionDefinition> actions) {
		this.name = name;
		this.actions = actions;
	}

	public AppActionGroupDefinition(final String name, final AppActionDefinition... actions) {
		this(name, List.of(actions));
	}

	public List<AppActionDefinition> actions() {
		return actions;
	}

	public String name() {
		return name;
	}
}
