package christmas.service;

import christmas.domain.MenuOrders;
import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.ReservationDate;

public class PromotionService {

    private final GiftEventProcessor giftEventProcessor;
    private final DDayDiscountEventProcessor dDayDiscountEventProcessor;

    public PromotionService(GiftEventProcessor giftEventProcessor,
                            DDayDiscountEventProcessor dDayDiscountEventProcessor) {
        this.giftEventProcessor = giftEventProcessor;
        this.dDayDiscountEventProcessor = dDayDiscountEventProcessor;
    }

    public Money calculatePreDiscountCharge(MenuOrders menuOrders) {
        return menuOrders.calculateTotalCost();
    }

    public MenuQuantity applyGiftEvent(MenuOrders menuOrders) {
        return giftEventProcessor.applyEvent(menuOrders);
    }

    public Money calculateGiftEventDiscountAmount(MenuOrders menuOrders) {
        MenuQuantity giftMenuQuantity = giftEventProcessor.applyEvent(menuOrders);
        return giftMenuQuantity.calculateCost();
    }

    public Money calculateDDayEventDiscountAmount(ReservationDate reservationDate, MenuOrders menuOrders) {
        return dDayDiscountEventProcessor.calculateDiscountAmount(reservationDate, menuOrders);
    }
}
