package christmas.controller.dto;

import christmas.controller.dto.validator.NumberFormatValidator;

public class ReservationDateCreateRequest {

    private final int dayOfMonth;

    public ReservationDateCreateRequest(String dayOfMonth) {
        NumberFormatValidator.validate(dayOfMonth);
        this.dayOfMonth = Integer.parseInt(dayOfMonth);
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }
}
