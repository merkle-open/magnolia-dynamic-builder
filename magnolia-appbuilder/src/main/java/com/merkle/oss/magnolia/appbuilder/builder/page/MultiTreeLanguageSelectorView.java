package com.merkle.oss.magnolia.appbuilder.builder.page;

import info.magnolia.context.Context;
import info.magnolia.i18nsystem.SimpleTranslator;
import info.magnolia.pages.app.detail.PageEditorStatus;
import info.magnolia.pages.app.detail.extension.LanguageSelectorView;
import info.magnolia.ui.ConfiguredViewDefinition;
import info.magnolia.ui.datasource.locale.LocaleSelectFieldSupport;
import info.magnolia.ui.editor.LocaleContext;
import info.magnolia.ui.vaadin.editor.PageEditorView;

import java.util.Locale;
import java.util.stream.Stream;

import com.vaadin.data.provider.AbstractDataProvider;
import com.vaadin.data.provider.DataProvider;
import com.vaadin.data.provider.Query;

import jakarta.inject.Inject;

public class MultiTreeLanguageSelectorView extends LanguageSelectorView {

	@Inject
	public MultiTreeLanguageSelectorView(
			final PageEditorStatus pageEditorStatus,
			final PageEditorView pageEditorView,
			final SimpleTranslator simpleTranslator,
			final LocaleContext localeContext,
			final Context context
	) {
		super(pageEditorStatus, pageEditorView, simpleTranslator, new EmptyLocaleSelectFieldSupport(localeContext, context), localeContext);
	}

	private static class EmptyLocaleSelectFieldSupport extends LocaleSelectFieldSupport {
		private final DataProvider<Locale, String> localeProvider = new EmptyLocaleListProvider();

		private EmptyLocaleSelectFieldSupport(final LocaleContext localeContext, final Context context) {
			super(localeContext, context);
		}

		@Override
		public DataProvider<Locale, ?> getDataProvider() {
			return localeProvider;
		}
	}

	private static class EmptyLocaleListProvider extends AbstractDataProvider<Locale, String> {
		@Override
		public boolean isInMemory() {
			return true;
		}
		@Override
		public int size(final Query<Locale, String> query) {
			return 0;
		}
		@Override
		public Stream<Locale> fetch(final Query<Locale, String> query) {
			return Stream.empty();
		}
	}

	public static class Definition  extends ConfiguredViewDefinition<MultiTreeLanguageSelectorView> {
		public Definition() {
			setImplementationClass(MultiTreeLanguageSelectorView.class);
		}
	}
}
