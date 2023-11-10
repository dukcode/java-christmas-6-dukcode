package christmas.service;

import christmas.domain.MenuOrders;
import christmas.domain.Money;
import christmas.domain.ReservationDate;
import java.time.LocalDate;
import java.util.Set;

public class SpecialDiscountEventProcessor {

    private final Money minimumOrderAmountForEvent;
    private final Money discountAmount;
    private final Set<LocalDate> specialDays;

    public SpecialDiscountEventProcessor(Money minimumOrderAmountForEvent, Money discountAmount,
                                         Set<LocalDate> specialDays) {
        this.minimumOrderAmountForEvent = minimumOrderAmountForEvent;
        this.discountAmount = discountAmount;
        this.specialDays = specialDays;
    }

    public Money calculateDiscountAmount(ReservationDate reservationDate, MenuOrders menuOrders) {
        Money totalOrderAmount = menuOrders.calculateTotalCost();
        if (!totalOrderAmount.isGreaterThanOrEqual(minimumOrderAmountForEvent)) {
            return Money.ZERO;
        }

        if (!reservationDate.isInDates(specialDays)) {
            return Money.ZERO;
        }

        return discountAmount;
    }
}
