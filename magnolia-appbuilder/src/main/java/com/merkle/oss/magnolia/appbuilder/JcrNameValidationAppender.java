package com.merkle.oss.magnolia.appbuilder;

import info.magnolia.ui.editor.FormDefinition;
import info.magnolia.ui.editor.validator.NodeNameValidatorDefinition;
import info.magnolia.ui.field.TextFieldDefinition;

import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.jcr.Item;

public class JcrNameValidationAppender {
    public void addNodeNameValidatorToJcrNameField(final FormDefinition<? extends Item> form, final NodeNameValidatorDefinition.Mode mode) {
        final NodeNameValidatorDefinition nodeNameValidatorDefinition = new NodeNameValidatorDefinition();
        nodeNameValidatorDefinition.setMode(mode);
        form
                .getFieldDefinition("jcrName")
                .filter(TextFieldDefinition.class::isInstance)
                .map(definition -> (TextFieldDefinition) definition) // NodeNameValidator only validates string fields
                .filter(field -> field.getValidators().stream().map(Object::getClass).noneMatch(NodeNameValidatorDefinition.class::equals))
                .ifPresent(field ->
                        field.setValidators(Stream.concat(
                                field.getValidators().stream(),
                                Stream.of(nodeNameValidatorDefinition)
                        ).collect(Collectors.toList()))
                );
    }
}
