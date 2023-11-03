package com.github.littlewoo.validation;

import java.util.List;

import static java.lang.Character.getNumericValue;

public class PersonalNumberValidator {

    private static final ValidationRule<String, String> LENGTH_RULE =
                ValidationRule.fromPredicate((input) -> {
                    final var inputWithoutSeparator = stripSeparatorFromPersonalNumber(input);
                    return inputWithoutSeparator.length() == 10 || inputWithoutSeparator.length() == 12;
                }, () -> "Personal Number must be 10 or 12 characters long");
    private static final ValidationRule<String, String> IS_NUMERIC_RULE =
            ValidationRule.fromPredicate(
                (input) -> {
                    final var inputWithoutSeparator = stripSeparatorFromPersonalNumber(input);
                    return inputWithoutSeparator.chars().allMatch(Character::isDigit);
                }, () -> "Personal Number must be numeric");
    private static final ValidationRule<String, String> LUHN_RULE =
            ValidationRule.fromPredicate(
                (input) -> {
                    final String inputWithoutSeparator = stripSeparatorFromPersonalNumber(input);
                    return (checkLuhn(inputWithoutSeparator));
                }, () -> "Personal Number failed Luhn validation");
    public static final ValidationRule<String, List<?>> PERSONAL_NUMBER_VALIDATION_RULE =
            CompositeValidationRule.of(LENGTH_RULE, IS_NUMERIC_RULE, LUHN_RULE);


    private static String stripSeparatorFromPersonalNumber(String personalNumber) {
        return personalNumber
                .replace("-", "")
                .replace("+", "");
    }

    private static boolean checkLuhn(String personalNumber) {
        final int checkDigit = getNumericValue(personalNumber.charAt(personalNumber.length() - 1));

        // Remove the check digit, and if we have a long-form personal number, remove the leading two digits:
        final int startIndex = personalNumber.length() == 12 ? 2 : 0;
        final int endIndex = personalNumber.length() - 1;

        final int calculatedCheckDigit = calculateCheckDigit(personalNumber.substring(startIndex, endIndex));
        return checkDigit == calculatedCheckDigit;
    }

    private static int calculateCheckDigit(String payload) {
        // Luhn algorithm:
        final int[] digits = payload.chars().map(Character::getNumericValue).toArray();
        int runningSum = 0;
        boolean doubleNext = true;
        for (int i=payload.length() - 1; i>=0; i--) {
            final int multiplier = doubleNext ? 2 : 1;
            doubleNext = !doubleNext;
            final int doubled = multiplier * digits[i];
            final int singleDigit = doubled <= 9 ? doubled :
                    Integer.toString(doubled).chars().map(Character::getNumericValue).sum();
            runningSum += singleDigit;
        }
        return 10 - (runningSum % 10);
    }
}
