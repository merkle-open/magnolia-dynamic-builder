package com.namics.oss.magnolia.appbuilder.action;

import info.magnolia.ui.availability.rule.JcrIsDeletedRuleDefinition;

public class JcrIsNotDeletedRuleDefinition extends JcrIsDeletedRuleDefinition {

	public JcrIsNotDeletedRuleDefinition() {
		setNegate(true);
	}
}
