package christmas.controller.dto;

import christmas.domain.ReservationDate;

public class ReservationDateResponse {


    private final int dayOfMonth;

    private ReservationDateResponse(int dayOfMonth) {
        this.dayOfMonth = dayOfMonth;
    }

    public static ReservationDateResponse from(ReservationDate reservationDate) {
        return new ReservationDateResponse(reservationDate.getDayOfMonth());
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }
}
