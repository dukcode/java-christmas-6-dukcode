package christmas.service;

import christmas.domain.MenuOrders;
import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.ReservationDate;

public class PromotionService {

    private final GiftEventProcessor giftEventProcessor;
    private final DDayDiscountEventProcessor dDayDiscountEventProcessor;
    private final WeekdayDiscountEventProcessor weekdayDiscountEventProcessor;
    private final WeekendDiscountEventProcessor weekendDiscountEventProcessor;

    public PromotionService(GiftEventProcessor giftEventProcessor,
                            DDayDiscountEventProcessor dDayDiscountEventProcessor,
                            WeekdayDiscountEventProcessor weekdayDiscountEventProcessor,
                            WeekendDiscountEventProcessor weekendDiscountEventProcessor) {
        this.giftEventProcessor = giftEventProcessor;
        this.dDayDiscountEventProcessor = dDayDiscountEventProcessor;
        this.weekdayDiscountEventProcessor = weekdayDiscountEventProcessor;
        this.weekendDiscountEventProcessor = weekendDiscountEventProcessor;
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

    public Money calculateWeekdayEventDiscountAmount(ReservationDate reservationDate, MenuOrders menuOrders) {
        return weekdayDiscountEventProcessor.calculateDiscountAmount(reservationDate, menuOrders);
    }

    public Money calculateWeekendEventDiscountAmount(ReservationDate reservationDate, MenuOrders menuOrders) {
        return weekendDiscountEventProcessor.calculateDiscountAmount(reservationDate, menuOrders);
    }
}
