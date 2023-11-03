package com.github.littlewoo.validation;

import java.util.function.Supplier;

/**
 * A result of a validation, containing either the successfully validated object, or some representation of the
 * validation failure.
 *
 * @param <T> The type of the successfully validated object
 * @param <F> The type of the validation failure
 */
public interface ValidationResult<T, F> {
    boolean isValid();

    /**
     * Returns the valid object. If the validation result was a failure, throws an exception.
     */
    T get() throws ValidationException;

    /**
     * Returns the valid object if present, otherwise returns the given default value. Warning - this may not
     * enforce validation on the provided default value.
     */
    T orElse(T defaultValue);

    /**
     * Returns the valid object if present, otherwise throws a ValidationException with the given message.
     */
    <E extends Throwable> T orElseThrow(Supplier<E> throwable) throws E;

    /**
     * Returns the validation failure. If the validation result was a success, throws an exception.
     */
    F getValidationFailure() throws ValidationException;
}
