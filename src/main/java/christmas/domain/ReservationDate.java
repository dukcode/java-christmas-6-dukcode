package christmas.domain;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.LocalDate;

public class ReservationDate {

    private final LocalDate reservationDate;

    public ReservationDate(LocalDate reservationDate) {
        this.reservationDate = reservationDate;
    }

    public boolean isInRange(LocalDate startDate, LocalDate endDate) {
        if (reservationDate.equals(startDate) || reservationDate.equals(endDate)) {
            return true;
        }
        return reservationDate.isAfter(startDate) && reservationDate.isBefore(endDate);
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

    public long daysUntil(LocalDate targetDate) {
        return DAYS.between(reservationDate, targetDate);
    }
}
