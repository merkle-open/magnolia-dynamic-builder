package com.merkle.oss.magnolia.formbuilder;


import info.magnolia.ui.editor.ConfiguredFormDefinition;
import info.magnolia.ui.editor.FormDefinition;
import info.magnolia.ui.editor.FormView;
import info.magnolia.ui.editor.LocaleContext;

import jakarta.inject.Inject;

public class RootFormView<T> extends FormView<T> {
    @Inject
    public RootFormView(final FormDefinition<T> formDefinition, final LocaleContext localeContext) {
        super(formDefinition, localeContext);
    }

    public static class Definition<T> extends ConfiguredFormDefinition<T> {
        public Definition() {
            setImplementationClass((Class)RootFormView.class);
        }
    }

    public static class Provider<T> implements jakarta.inject.Provider<RootFormView<T>> {
        private final RootFormView<T> view;

        @Inject
        public Provider(final RootFormView<T> view) {
            this.view = view;
        }

        public RootFormView<T> get() {
            return view;
        }
    }
}
