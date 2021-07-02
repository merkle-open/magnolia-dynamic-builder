package com.namics.oss.magnolia.appbuilder.dropconstraint;

import com.vaadin.v7.data.Item;
import info.magnolia.ui.workbench.tree.MoveLocation;
import info.magnolia.ui.workbench.tree.drop.DropConstraint;

import java.util.Set;

/**
 * Returns true if any of the constraints returns true
 */
public class OrNodeDropConstraint implements DropConstraint {
	private final Set<DropConstraint> dropConstraints;

	public OrNodeDropConstraint(final Set<DropConstraint> dropConstraints) {
		this.dropConstraints = dropConstraints;
	}

	@Override
	public boolean isAllowedAt(Item sourceItem, Item targetItem, MoveLocation location) {
		return dropConstraints.stream().anyMatch(dropConstraint ->
				dropConstraint.isAllowedAt(sourceItem, targetItem, location)
		);
	}

	@Override
	public boolean allowedAsChild(Item sourceItem, Item targetItem) {
		return dropConstraints.stream().anyMatch(dropConstraint ->
				dropConstraint.allowedAsChild(sourceItem, targetItem)
		);
	}

	@Override
	public boolean allowedBefore(Item sourceItem, Item targetItem) {
		return dropConstraints.stream().anyMatch(dropConstraint ->
				dropConstraint.allowedBefore(sourceItem, targetItem)
		);
	}

	@Override
	public boolean allowedAfter(Item sourceItem, Item targetItem) {
		return dropConstraints.stream().anyMatch(dropConstraint ->
				dropConstraint.allowedAfter(sourceItem, targetItem)
		);
	}

	@Override
	public boolean allowedToMove(Item sourceItem) {
		return dropConstraints.stream().anyMatch(dropConstraint ->
				dropConstraint.allowedToMove(sourceItem)
		);
	}
}
