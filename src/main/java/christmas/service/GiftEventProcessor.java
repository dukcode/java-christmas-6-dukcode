package christmas.service;

import christmas.domain.Menu;
import christmas.domain.MenuOrders;
import christmas.domain.MenuQuantity;
import christmas.domain.Money;

public class GiftEventProcessor {

    private final Money eventMinOrderAmount;
    private final Menu giftMenu;
    private final int quantity;

    public GiftEventProcessor(Money eventMinOrderAmount, Menu giftMenu, int quantity) {
        this.eventMinOrderAmount = eventMinOrderAmount;
        this.giftMenu = giftMenu;
        this.quantity = quantity;
    }

    public MenuQuantity applyEvent(MenuOrders menuOrders) {
        Money totalCost = menuOrders.calculateTotalCost();

        if (totalCost.isGreaterThanOrEqual(eventMinOrderAmount)) {
            return new MenuQuantity(giftMenu, quantity);
        }
        return MenuQuantity.EMPTY;
    }
}
