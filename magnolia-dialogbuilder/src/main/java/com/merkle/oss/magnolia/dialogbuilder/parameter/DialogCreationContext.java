package com.merkle.oss.magnolia.dialogbuilder.parameter;

import info.magnolia.context.MgnlContext;
import info.magnolia.objectfactory.Components;
import info.magnolia.ui.UIComponent;
import info.magnolia.ui.ValueContext;
import info.magnolia.ui.api.app.AppContext;
import info.magnolia.ui.api.app.AppController;
import info.magnolia.ui.framework.ioc.BeanStore;
import info.magnolia.ui.framework.ioc.SessionStore;
import info.magnolia.ui.framework.ioc.UiContextReference;

import java.util.Optional;

import javax.annotation.Nullable;
import javax.jcr.Node;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


/**
 * Holds the parameters required during dialog creation and validation.
 */
public record DialogCreationContext(
        HttpServletRequest request,
        HttpServletResponse response,
        @Nullable Node contentNode,
        @Nullable UIComponent parentView
) {
    public static class Factory {

        public DialogCreationContext create() {
            return new DialogCreationContext(
                    MgnlContext.getWebContext().getRequest(),
                    MgnlContext.getWebContext().getResponse(),
                    getValueContext().flatMap(ValueContext::getSingle).filter(Node.class::isInstance).map(Node.class::cast).orElse(null),
                    getUiComponent().orElse(null)
            );
        }

        protected Optional<UIComponent> getUiComponent() {
            return getBeanStore().map(beanStore -> beanStore.get(UIComponent.class));
        }

        protected Optional<ValueContext<?>> getValueContext() {
            return getBeanStore().map(beanStore -> beanStore.get(ValueContext.class));
        }

        protected Optional<BeanStore> getBeanStore() {
            try {
                return Components.getComponent(AppController.class)
                        .getCurrentAppContext()
                        .map(AppContext::getActiveSubAppContext)
                        .map(activeSubAppContext -> SessionStore.access().getBeanStore(UiContextReference.ofSubApp(activeSubAppContext)));
            } catch (Exception e) {
                return Optional.empty();
            }
        }
    }
}
