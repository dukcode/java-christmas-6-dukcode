package christmas.controller.dto;

import christmas.view.exception.ExceptionMessage;

public class ReservationDateCreateRequest {

    private final int dayOfMonth;
    public ReservationDateCreateRequest(String dayOfMonth) {
        try {
            this.dayOfMonth = Integer.parseInt(dayOfMonth);
        } catch (NumberFormatException numberFormatException) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_NUMBER_FORMAT);
        }
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }
}
