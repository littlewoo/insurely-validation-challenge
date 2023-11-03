package com.github.littlewoo.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class ValidationRuleTests {

    @Test
    public void notBlankShouldValidateNonBlankString() {
        final var input = "Content!";
        final var result = ValidationRules.notBlank().validate(input);
        assertTrue(result.isValid());
        assertEquals(result.get(), input);
    }

    @Test
    public void notBlankShouldFailValidatingBlankString() {
        final var input = " ";
        final var result = ValidationRules.notBlank().validate(input);
        assertFalse(result.isValid());
        assertEquals(result.getValidationFailure(), "String is blank");
    }

    @Test
    public void notBlankShouldFailValidatingNull() {
        final String input = null;
        final var result = ValidationRules.notBlank().validate(input);
        assertFalse(result.isValid());
    }

    @Test
    public void notNullShouldValidateNonNullObject() {
        final var input = new Object();
        final var result = ValidationRules.notNull().validate(input);
        assertTrue(result.isValid());
        assertEquals(result.get(), input);
    }

    @Test
    public void notNullShouldFailValidatingNullObject() {
        final Object input = null;
        final var result = ValidationRules.notNull().validate(input);
        assertFalse(result.isValid());
        assertEquals(result.getValidationFailure(), "Object is null");
    }

}
