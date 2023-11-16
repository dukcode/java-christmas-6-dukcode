package christmas.controller.dto.validator;

import christmas.controller.dto.exception.ExceptionMessage;

public class NumberFormatValidator {

    private NumberFormatValidator() {
    }

    public static void validate(String number, String exceptionMessage) {
        try {
            Integer.parseInt(number);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
