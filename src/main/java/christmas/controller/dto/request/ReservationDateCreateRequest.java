package christmas.controller.dto.request;

import christmas.controller.dto.exception.ExceptionMessage;
import christmas.controller.dto.validator.NumberFormatValidator;

public class ReservationDateCreateRequest {

    private final int dayOfMonth;

    public ReservationDateCreateRequest(String dayOfMonth) {
        NumberFormatValidator.validate(dayOfMonth, ExceptionMessage.INVALID_DAY_OF_MONTH_FORMAT);
        this.dayOfMonth = Integer.parseInt(dayOfMonth);
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }
}
