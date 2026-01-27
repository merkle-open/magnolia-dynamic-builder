package com.namics.oss.magnolia.appbuilder.action.edit.clipboard;

import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.contentapp.action.clipboard.CopyContentActionDefinition;

import javax.inject.Inject;
import javax.jcr.Item;

public class CopyContentAction extends info.magnolia.ui.contentapp.action.clipboard.CopyContentAction<Item> {

	@Inject
	public CopyContentAction(
			final CopyContentActionDefinition definition,
			final ValueContext<Item> valueContext,
			final JcrContentClipboard contentClipboard,
			final SimpleTranslator simpleTranslator) {
		super(definition, valueContext, contentClipboard, simpleTranslator);
	}
}
