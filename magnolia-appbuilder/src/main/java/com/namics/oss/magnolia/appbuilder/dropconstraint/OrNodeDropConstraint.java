package com.namics.oss.magnolia.appbuilder.dropconstraint;

import info.magnolia.ui.contentapp.browser.drop.DropConstraint;

import java.util.Set;

import com.vaadin.shared.ui.grid.DropLocation;

/**
 * Returns true if any of the constraints returns true
 */
public class OrNodeDropConstraint<T> implements DropConstraint<T> {
	private final Set<DropConstraint<T>> dropConstraints;

	public OrNodeDropConstraint(final Set<DropConstraint<T>> dropConstraints) {
		this.dropConstraints = dropConstraints;
	}

	@Override
	public boolean isAllowedAt(T source, T target, DropLocation dropLocation) {
		return dropConstraints.stream().anyMatch(dropConstraint ->
				dropConstraint.isAllowedAt(source, target, dropLocation)
		);
	}
}
