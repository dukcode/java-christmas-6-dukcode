package christmas.service.event.policy;

import christmas.domain.Menu;
import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.service.event.EventPolicy;

public class GiftEventPolicy implements EventPolicy {

    private final Money eventMinOrderAmount;
    private final Menu giftMenu;
    private final int quantity;

    public GiftEventPolicy(Money eventMinOrderAmount, Menu giftMenu, int quantity) {
        this.eventMinOrderAmount = eventMinOrderAmount;
        this.giftMenu = giftMenu;
        this.quantity = quantity;
    }

    @Override
    public Money calculateDiscountAmount(Reservation reservation) {
        return Money.ZERO;
    }

    @Override
    public MenuQuantity receiveGift(Reservation reservation) {
        Money totalCost = reservation.calculateTotalCost();

        if (totalCost.isGreaterThanOrEqual(eventMinOrderAmount)) {
            return new MenuQuantity(giftMenu, quantity);
        }

        return MenuQuantity.EMPTY;
    }
}
