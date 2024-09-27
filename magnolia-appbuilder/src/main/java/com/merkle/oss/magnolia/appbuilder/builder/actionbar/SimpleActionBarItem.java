package com.merkle.oss.magnolia.appbuilder.builder.actionbar;

import info.magnolia.ui.actionbar.definition.ActionbarItemDefinition;
import info.magnolia.ui.actionbar.definition.ConfiguredActionbarItemDefinition;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Helper to add actions to actionbar.
 */
public class SimpleActionBarItem {

	private SimpleActionBarItem() {
		// do not instantiate
	}

	public static ActionbarItemDefinition name(String name) {
		ConfiguredActionbarItemDefinition itemDefinition = new ConfiguredActionbarItemDefinition();
		itemDefinition.setName(name);
		return itemDefinition;
	}

	public static List<ActionbarItemDefinition> names(String... name) {
		return Arrays.stream(name)
				.map(itemName -> {
					ConfiguredActionbarItemDefinition itemDefinition = new ConfiguredActionbarItemDefinition();
					itemDefinition.setName(itemName);
					return itemDefinition;
				}).collect(Collectors.toList());
	}

}
