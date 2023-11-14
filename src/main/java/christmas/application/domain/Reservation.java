package christmas.application.domain;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

public class Reservation {

    private final Order order;
    private final ReservationDate reservationDate;

    public Reservation(Order order, ReservationDate reservationDate) {
        this.order = order;
        this.reservationDate = reservationDate;
    }

    public boolean isInRange(LocalDate startDate, LocalDate endDate) {
        return reservationDate.isInRange(startDate, endDate);
    }

    public Money calculateTotalOrderAmount() {
        return order.calculateTotalCost();
    }

    public long daysAfter(LocalDate startDate) {
        return reservationDate.daysAfter(startDate);
    }

    public boolean isWeekend() {
        return reservationDate.isWeekend();
    }

    public int countMenusByType(MenuType menuType) {
        return order.countMenusByType(menuType);
    }

    public boolean isInDates(Set<LocalDate> dates) {
        return reservationDate.isInDates(dates);
    }

    public Money calculateTotalCost() {
        return order.calculateTotalCost();
    }

    public LocalDate getReservationDate() {
        return reservationDate.getReservationDate();
    }

    public List<MenuQuantity> getMenuQuantities() {
        return order.getMenuQuantities();
    }
}
