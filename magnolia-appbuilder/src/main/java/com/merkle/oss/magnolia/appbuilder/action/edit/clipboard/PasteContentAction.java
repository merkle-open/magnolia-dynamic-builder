package com.merkle.oss.magnolia.appbuilder.action.edit.clipboard;

import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.contentapp.action.clipboard.PasteContentActionDefinition;

import javax.jcr.Item;

import jakarta.inject.Inject;

public class PasteContentAction extends info.magnolia.ui.contentapp.action.clipboard.PasteContentAction<Item> {

	@Inject
	public PasteContentAction(
			final PasteContentActionDefinition definition,
			final ValueContext<Item> valueContext,
			final JcrContentClipboard contentClipboard,
			final SimpleTranslator simpleTranslator) {
		super(definition, valueContext, contentClipboard, simpleTranslator);
	}
}
