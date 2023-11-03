package com.github.littlewoo.validation;

import org.junit.jupiter.api.Test;

import static com.github.littlewoo.validation.PersonalNumberValidator.PERSONAL_NUMBER_VALIDATION_RULE;
import static org.junit.jupiter.api.Assertions.*;

public class PersonalNumberValidationTests {

    @Test
    public void personalNumberShouldNotValidateForInvalidLuhn() {
        doPersonalNumberValidationTest("199001010000", false);
    }

    @Test
    public void personalNumberShouldValidateForValidLuhn() {
        doPersonalNumberValidationTest("190001019801", true);
    }

    @Test
    public void personalNumberShouldNotValidateForNonNumeric() {
        doPersonalNumberValidationTest("19000101A", false);
    }

    @Test
    public void personalNumberShouldValidateWithSeparator() {
        doPersonalNumberValidationTest("19000102-9818", true);
    }

    @Test
    public void personalNumberShouldNotValidateForInvalidSeparator() {
        doPersonalNumberValidationTest("19000101#0000", false);
    }

    @Test
    public void personalNumberShouldNotValidateForInvalidLength() {
        doPersonalNumberValidationTest("19000101", false);
    }

    private void doPersonalNumberValidationTest(String input, boolean isValid) {
        final var result = PERSONAL_NUMBER_VALIDATION_RULE.validate(input);
        assertEquals(result.isValid(), isValid);
    }
}
