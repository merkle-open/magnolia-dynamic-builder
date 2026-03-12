package com.merkle.oss.magnolia.formbuilder;


import info.magnolia.ui.editor.ConfiguredFormDefinition;
import info.magnolia.ui.editor.FormView;
import info.magnolia.ui.editor.LocaleContext;
import info.magnolia.ui.field.FieldDefinition;

import java.util.Map;

import com.vaadin.ui.Component;

import jakarta.inject.Inject;

public class RootFormView<T> extends FormView<T> {
    private final Definition<T> formDefinition;

    @Inject
    public RootFormView(final RootFormView.Definition<T> formDefinition, final LocaleContext localeContext) {
        super(formDefinition, localeContext);
        this.formDefinition = formDefinition;
        updateViewType(asVaadinComponent());
    }

    @Override
    protected Component computeLayout(final Map<FieldDefinition<T>, Component> boundFields) {
        final Component layout = super.computeLayout(boundFields);
        if(formDefinition != null) {
            updateViewType(layout);
        }
        return layout;
    }

    private void updateViewType(final Component component) {
        component.setEnabled(formDefinition.getViewType() == ViewType.EDIT);
    }

    public static class Definition<T> extends ConfiguredFormDefinition<T> {
        private ViewType viewType = ViewType.EDIT;
        public Definition() {
            setImplementationClass((Class)RootFormView.class);
        }

        public ViewType getViewType() {
            return viewType;
        }

        public void setViewType(ViewType viewType) {
            this.viewType = viewType;
        }
    }

    public enum ViewType {
        EDIT,
        VIEW
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
