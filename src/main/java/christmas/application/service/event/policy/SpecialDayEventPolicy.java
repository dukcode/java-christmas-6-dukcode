package christmas.application.service.event.policy;

import christmas.application.domain.MenuQuantity;
import christmas.application.domain.Money;
import christmas.application.domain.Reservation;
import christmas.application.service.EventPolicy;
import java.time.LocalDate;
import java.util.Optional;
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
    public Optional<MenuQuantity> receiveGift(Reservation reservation) {
        return Optional.empty();
    }
}
