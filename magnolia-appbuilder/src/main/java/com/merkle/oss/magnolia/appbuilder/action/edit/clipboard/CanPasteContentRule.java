package com.merkle.oss.magnolia.appbuilder.action.edit.clipboard;

import info.magnolia.ui.api.availability.AvailabilityDefinition;
import info.magnolia.ui.contentapp.action.clipboard.CanPasteContentRuleDefinition;

import javax.jcr.Item;

import jakarta.inject.Inject;

public class CanPasteContentRule extends info.magnolia.ui.contentapp.action.clipboard.CanPasteContentRule<Item> {

	@Inject
	public CanPasteContentRule(
			final AvailabilityDefinition availabilityDefinition,
			final CanPasteContentRuleDefinition ruleDefinition,
			final JcrContentClipboard contentClipboard
	) {
		super(availabilityDefinition, ruleDefinition, contentClipboard);
	}
}
