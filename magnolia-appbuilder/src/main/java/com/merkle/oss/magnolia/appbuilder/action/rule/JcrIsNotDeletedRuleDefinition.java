package com.merkle.oss.magnolia.appbuilder.action.rule;

import info.magnolia.ui.availability.rule.JcrIsDeletedRuleDefinition;

public class JcrIsNotDeletedRuleDefinition extends JcrIsDeletedRuleDefinition {

	public JcrIsNotDeletedRuleDefinition() {
		setNegate(true);
	}
}
