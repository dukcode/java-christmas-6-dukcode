package christmas.service;

import christmas.domain.MenuOrders;
import christmas.domain.Money;
import christmas.domain.ReservationDate;
import java.time.LocalDate;

public class DDayDiscountEventProcessor {

    private final Money minimumOrderAmountForEvent;

    private final Money baseDiscountAmount;
    private final Money perDayDiscountAmount;

    private final LocalDate eventStartDate;
    private final LocalDate dDay;

    public DDayDiscountEventProcessor(Money minimumOrderAmountForEvent,
                                      Money baseDiscountAmount, Money perDayDiscountAmount,
                                      LocalDate eventStartDate, LocalDate dDay) {
        this.minimumOrderAmountForEvent = minimumOrderAmountForEvent;
        this.baseDiscountAmount = baseDiscountAmount;
        this.perDayDiscountAmount = perDayDiscountAmount;
        this.eventStartDate = eventStartDate;
        this.dDay = dDay;
    }

    public Money calculateDiscountAmount(ReservationDate reservationDate, MenuOrders menuOrders) {
        Money totalOrderAmount = menuOrders.calculateTotalCost();
        if (!totalOrderAmount.isGreaterThanOrEqual(minimumOrderAmountForEvent)) {
            return Money.ZERO;
        }

        if (!reservationDate.isInRange(eventStartDate, dDay)) {
            return Money.ZERO;
        }

        Money discountAmount = baseDiscountAmount;
        long dateDifference = reservationDate.daysAfter(eventStartDate);
        discountAmount = discountAmount.add(perDayDiscountAmount.multiply(dateDifference));

        return discountAmount;
    }
}
