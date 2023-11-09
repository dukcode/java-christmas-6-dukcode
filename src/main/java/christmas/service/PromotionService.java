package christmas.service;

import christmas.domain.MenuOrders;
import christmas.domain.MenuQuantity;
import christmas.domain.Money;

public class PromotionService {

    private final GiftEventProcessor giftEventProcessor;

    public PromotionService(GiftEventProcessor giftEventProcessor) {
        this.giftEventProcessor = giftEventProcessor;
    }

    public Money calculatePreDiscountCharge(MenuOrders menuOrders) {
        return menuOrders.calculateTotalCost();
    }

    public MenuQuantity applyGiftEvent(MenuOrders menuOrders) {
        return giftEventProcessor.applyEvent(menuOrders);
    }

}
