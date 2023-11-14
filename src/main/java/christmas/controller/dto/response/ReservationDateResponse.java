package christmas.controller.dto.response;

import christmas.application.domain.Reservation;
import java.time.LocalDate;

public class ReservationDateResponse {


    private final LocalDate reservationDate;

    private ReservationDateResponse(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public static ReservationDateResponse from(Reservation reservation) {
        return new ReservationDateResponse(reservation.getReservationDate());
    }

    public int getDayOfMonth() {
        return reservationDate.getDayOfMonth();
    }
}
