package com.github.littlewoo.validation;

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SwedishPersonalNameValidationTests {

    private static final ValidationRule<String, String> ONLY_ALPHABETIC_CHARACTERS_RULE =
            ValidationRule.fromPredicate(
                    input -> input.matches("[a-zäåöA-ZÄÅÖ ]*"),
                    () -> "String contains non-alphabetic characters");

    private static final ValidationRule<String, List<?>> SWEDISH_PERSONAL_NAME_RULE =
            CompositeValidationRule.of(
                    ValidationRules.notBlank(),
                    ONLY_ALPHABETIC_CHARACTERS_RULE
            );

    @Test
    public void shouldValidateSwedishPersonalName() {
        doNameValidation("Kalle Anka", true);
    }

    @Test
    public void shouldFailValidatingSwedishPersonalNameWithNumbers() {
        doNameValidation("Bo Ek 123", false);
    }

    @Test
    public void shouldValidateSwedishNameWithDiaeresis() {
        doNameValidation("Åke Ärlig", true);
    }

    @Test
    public void shouldFailValidatingSwedishNameWithNonSwedishAccent() {
        doNameValidation("Mañuel Martínez", false);
    }

    @Test
    public void shouldFailValidatingBlankName() {
        doNameValidation(" ", false);
    }

    private void doNameValidation(String name, boolean isValid) {
        final var result = SWEDISH_PERSONAL_NAME_RULE.validate(name);
        assertEquals(isValid, result.isValid());
        if (isValid) {
            assertEquals(name, result.get());
        }
    }
}
