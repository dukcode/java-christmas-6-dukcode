package christmas.service.event.policy;

import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.service.event.EventPolicy;
import java.time.LocalDate;
import java.util.Set;

public class SpecialDayEventPolicy implements EventPolicy {

    private final Money discountAmount;
    private final Set<LocalDate> specialDays;

    public SpecialDayEventPolicy(Money discountAmount, Set<LocalDate> specialDays) {
        this.discountAmount = discountAmount;
        this.specialDays = specialDays;
    }

    @Override
    public Money calculateDiscountAmount(Reservation reservation) {
        if (reservation.isInDates(specialDays)) {
            return discountAmount;
        }

        return Money.ZERO;
    }

    @Override
    public MenuQuantity receiveGift(Reservation reservation) {
        return MenuQuantity.EMPTY;
    }
}
