package com.github.littlewoo.validation;

/**
 * A collection of commonly used validation rules, supplied with the framework.
 */
public class ValidationRules {

    /**
     * A validation rule that validates that an object is not null.
     */
    public static <T> ValidationRule<T, String> notNull() {
        return (input) -> {
            if (input == null) {
                return ValidationResultInternal.invalid("Object is null");
            } else {
                return ValidationResultInternal.valid(input);
            }
        };
    }

    /**
     * A validation rule that validates that a string is not blank.
     */
    public static ValidationRule<String, String> notBlank() {
        return (input) -> {
            if (input == null) {
                return ValidationResultInternal.invalid("String is null");
            } else if (input.isBlank()) {
                return ValidationResultInternal.invalid("String is blank");
            } else {
                return ValidationResultInternal.valid(input);
            }
        };
    }
}
