package christmas.application.service.event.policy;

import christmas.application.domain.MenuQuantity;
import christmas.application.domain.MenuType;
import christmas.application.domain.Money;
import christmas.application.domain.Reservation;
import christmas.application.service.EventPolicy;
import java.util.Optional;

public class WeekendEventPolicy implements EventPolicy {

    private final MenuType discountMenuType;
    private final Money discountAmountPerMenuQuantity;

    public WeekendEventPolicy(MenuType discountMenuType, Money discountAmountPerMenuQuantity) {
        this.discountMenuType = discountMenuType;
        this.discountAmountPerMenuQuantity = discountAmountPerMenuQuantity;
    }

    @Override
    public Money calculateDiscountAmount(Reservation reservation) {
        if (!reservation.isWeekend()) {
            return Money.ZERO;
        }

        int numDiscountMenu = reservation.countMenusByType(discountMenuType);
        return discountAmountPerMenuQuantity.multiply(numDiscountMenu);
    }

    @Override
    public Optional<MenuQuantity> receiveGift(Reservation reservation) {
        return Optional.empty();
    }
}
