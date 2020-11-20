package com.namics.oss.magnolia.appbuilder.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotation to mark methods which produce
 * a {@link info.magnolia.ui.dialog.definition.ChooseDialogDefinition}.
 * <p>
 * - Used in classes annotated with {@link AppFactory}, optional.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface ChooseDialog {
}
