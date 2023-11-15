package christmas.application.domain;

import static java.time.temporal.ChronoUnit.DAYS;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.Set;

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

    public long daysAfter(LocalDate startDate) {
        return DAYS.between(startDate, reservationDate);
    }

    public boolean isWeekend() {
        DayOfWeek dayOfWeek = reservationDate.getDayOfWeek();
        return dayOfWeek.equals(DayOfWeek.FRIDAY) || dayOfWeek.equals(DayOfWeek.SATURDAY);
    }

    public boolean isInDates(Set<LocalDate> dates) {
        return dates.contains(reservationDate);
    }

}
