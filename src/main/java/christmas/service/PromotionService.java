package christmas.service;

import christmas.domain.Menu;
import christmas.domain.MenuOrders;
import christmas.domain.MenuQuantity;
import christmas.domain.Money;

public class PromotionService {

    public static final Money GIFT_EVENT_MIN_ORDER_AMOUNT = Money.of(120_000L);

    public Money calculatePreDiscountCharge(MenuOrders menuOrders) {
        return menuOrders.calculateTotalCost();
    }

    public MenuQuantity applyGiftEvent(MenuOrders menuOrders) {
        Money totalCost = menuOrders.calculateTotalCost();

        if (totalCost.isGreaterThanOrEqual(GIFT_EVENT_MIN_ORDER_AMOUNT)) {
            return new MenuQuantity(Menu.CHAMPAGNE, 1);
        }
        return MenuQuantity.EMPTY;
    }
}
