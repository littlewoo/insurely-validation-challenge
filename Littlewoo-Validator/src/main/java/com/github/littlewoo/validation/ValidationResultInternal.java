package com.github.littlewoo.validation;

import java.util.Optional;
import java.util.function.Supplier;


class ValidationResultInternal<T, F> implements ValidationResult<T, F> {
    private final Optional<T> validObject;
    private final Optional<F> validationFailure;

    private ValidationResultInternal(Optional<T> validObject, Optional<F> validationFailure) {
        if (validObject.isPresent() == validationFailure.isPresent()) {
            throw new IllegalArgumentException("Exactly one of validObject and validationFailure must be present");
        }
        this.validObject = validObject;
        this.validationFailure = validationFailure;
    }

    public static <T, F> ValidationResult<T, F> valid(T validObject) {
        return new ValidationResultInternal<>(Optional.of(validObject), Optional.empty());
    }

    public static <T, F> ValidationResult<T, F> invalid(F validationFailure) {
        return new ValidationResultInternal<>(Optional.empty(), Optional.of(validationFailure));
    }

    @Override
    public boolean isValid() {
        return validObject.isPresent();
    }

    @Override
    public T get() {
        return validObject.orElseThrow(() ->
                new ValidationException("Validation failed with: " + validationFailure.get()));
    }


    @Override
    public T orElse(T defaultValue) {
        return validObject.orElse(defaultValue);
    }


    @Override
    public <E extends Throwable> T orElseThrow(Supplier<E> throwableSupplier) throws E {
        return validObject.orElseThrow(throwableSupplier);
    }

    @Override
    public F getValidationFailure() throws ValidationException {
        return validationFailure.orElseThrow(() ->
                new ValidationException("Attempted to fetch failure for a successful validation: "
                        + validObject.get()));
    }
}
