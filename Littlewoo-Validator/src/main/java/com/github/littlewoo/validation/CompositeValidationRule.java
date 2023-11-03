package com.github.littlewoo.validation;

import java.util.List;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

/**
 * A composite validation rule that applies multiple validation rules to an input.
 */
public final class CompositeValidationRule<T> implements ValidationRule<T, List<?>> {
    private final Set<ValidationRule<T, ?>> rules;

    public CompositeValidationRule(Set<ValidationRule<T, ?>> rules) {
        this.rules = rules;
    }

    @SafeVarargs
    public static <T> CompositeValidationRule<T> of(ValidationRule<T, ?>... rules) {
        return new CompositeValidationRule<>(Set.of(rules));
    }

    public ValidationResult<T, List<?>> validate(T input) {
        final var results = rules.stream()
                .map(rule -> rule.validate(input))
                .collect(toSet());
        final var failing = results.stream()
                .filter(result -> !result.isValid())
                .toList();
        return failing.isEmpty() ?
                ValidationResultInternal.valid(input) :
                ValidationResultInternal.invalid(failing);
    }
}
