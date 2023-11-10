package christmas.service;

import christmas.domain.MenuOrders;
import christmas.domain.MenuType;
import christmas.domain.Money;
import christmas.domain.ReservationDate;

public class WeekendDiscountEventProcessor {

    private final Money minimumOrderAmountForEvent;
    private final Money discountAmountPerMenuQuantity;
    private final MenuType discountMenuType;

    public WeekendDiscountEventProcessor(Money minimumOrderAmountForEvent,
                                         Money discountAmountPerMenuQuantity,
                                         MenuType discountMenuType) {
        this.minimumOrderAmountForEvent = minimumOrderAmountForEvent;
        this.discountAmountPerMenuQuantity = discountAmountPerMenuQuantity;
        this.discountMenuType = discountMenuType;
    }

    public Money calculateDiscountAmount(ReservationDate reservationDate, MenuOrders menuOrders) {
        Money totalOrderAmount = menuOrders.calculateTotalCost();
        if (!totalOrderAmount.isGreaterThanOrEqual(minimumOrderAmountForEvent)) {
            return Money.ZERO;
        }

        if (!reservationDate.isWeekend()) {
            return Money.ZERO;
        }

        return discountAmountPerMenuQuantity.multiply(menuOrders.countMenuByType(discountMenuType));
    }
}
