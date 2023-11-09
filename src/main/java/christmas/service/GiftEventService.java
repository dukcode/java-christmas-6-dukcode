package christmas.service;

import christmas.domain.Menu;
import christmas.domain.MenuOrders;
import christmas.domain.MenuQuantity;
import christmas.domain.Money;

public class GiftEventService {

    public final Money eventMinOrderAmount;
    private final Menu giftMenu;
    private final int quantity;

    public GiftEventService(Money eventMinOrderAmount, Menu giftMenu, int quantity) {
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
