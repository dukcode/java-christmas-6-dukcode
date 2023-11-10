package christmas.domain;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.DayOfWeek;
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

    public boolean isWeekend() {
        DayOfWeek dayOfWeek = reservationDate.getDayOfWeek();
        return dayOfWeek.equals(DayOfWeek.FRIDAY) || dayOfWeek.equals(DayOfWeek.SATURDAY);
    }
}
