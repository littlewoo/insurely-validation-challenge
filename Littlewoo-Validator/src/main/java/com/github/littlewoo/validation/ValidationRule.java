package com.github.littlewoo.validation;

import java.util.function.Predicate;
import java.util.function.Supplier;

/**
 * A rule for performing validation on an input.
 */
@FunctionalInterface
public interface ValidationRule<T, F> {

    ValidationResult<T, F> validate(T input);

    /**
     * Build a validation rule from a predicate. If the predicate returns true, the validation is successful and a
     * successful result is returned. If the validation fails, the failure supplier is used to return a failing result.
     */
    static <T, F> ValidationRule<T, F> fromPredicate(Predicate<T> predicate, Supplier<F> failure) {
        return (input) -> {
            if (predicate.test(input)) {
                return ValidationResultInternal.valid(input);
            } else {
                return ValidationResultInternal.invalid(failure.get());
            }
        };
    }
}
