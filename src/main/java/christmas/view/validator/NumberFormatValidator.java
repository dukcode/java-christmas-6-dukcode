package christmas.view.validator;

import christmas.view.exception.ExceptionMessage;

public class NumberFormatValidator {

    private NumberFormatValidator() {
    }

    public static void validate(String number) {
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_NUMBER_FORMAT);
        }
    }
}
