package christmas.service.event.policy;

import christmas.domain.MenuQuantity;
import christmas.domain.MenuType;
import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.service.event.EventPolicy;

public class WeekdayEventPolicy implements EventPolicy {

    private final MenuType discountMenuType;
    private final Money discountAmountPerMenuQuantity;

    public WeekdayEventPolicy(MenuType discountMenuType, Money discountAmountPerMenuQuantity) {
        this.discountMenuType = discountMenuType;
        this.discountAmountPerMenuQuantity = discountAmountPerMenuQuantity;
    }

    @Override
    public Money calculateDiscountAmount(Reservation reservation) {
        if (reservation.isWeekend()) {
            return Money.ZERO;
        }

        int numDiscountMenu = reservation.countMenusByType(discountMenuType);
        return discountAmountPerMenuQuantity.multiply(numDiscountMenu);
    }

    @Override
    public MenuQuantity receiveGift(Reservation reservation) {
        return MenuQuantity.EMPTY;
    }
}
