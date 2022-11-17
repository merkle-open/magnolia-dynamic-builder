package com.namics.oss.magnolia.appbuilder.dropconstraint;

import info.magnolia.ui.contentapp.drop.JcrDropConstraintDefinition;

public class NodeTypeConstraintAwareDropConstraintDefinition  extends JcrDropConstraintDefinition {
	public NodeTypeConstraintAwareDropConstraintDefinition() {
		setImplementationClass(NodeTypeConstraintAwareDropConstraint.class);
	}
}