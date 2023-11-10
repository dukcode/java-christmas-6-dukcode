package christmas.controller.dto.response;

import christmas.domain.ReservationDate;
import java.time.LocalDate;

public class ReservationDateResponse {


    private final LocalDate reservationDate;

    private ReservationDateResponse(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public static ReservationDateResponse from(ReservationDate reservationDate) {
        return new ReservationDateResponse(reservationDate.getReservationDate());
    }

    public int getDayOfMonth() {
        return reservationDate.getDayOfMonth();
    }
}
