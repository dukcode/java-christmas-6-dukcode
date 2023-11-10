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
    private final SpecialDiscountEventProcessor specialDiscountEventProcessor;

    public PromotionService(GiftEventProcessor giftEventProcessor,
                            DDayDiscountEventProcessor dDayDiscountEventProcessor,
                            WeekdayDiscountEventProcessor weekdayDiscountEventProcessor,
                            WeekendDiscountEventProcessor weekendDiscountEventProcessor,
                            SpecialDiscountEventProcessor specialDiscountEventProcessor) {
        this.giftEventProcessor = giftEventProcessor;
        this.dDayDiscountEventProcessor = dDayDiscountEventProcessor;
        this.weekdayDiscountEventProcessor = weekdayDiscountEventProcessor;
        this.weekendDiscountEventProcessor = weekendDiscountEventProcessor;
        this.specialDiscountEventProcessor = specialDiscountEventProcessor;
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

    public Money calculateSpecialEventDiscountAmount(ReservationDate reservationDate, MenuOrders menuOrders) {
        return specialDiscountEventProcessor.calculateDiscountAmount(reservationDate, menuOrders);
    }

    public Money calculateTotalBenefitAmount(ReservationDate reservationDate, MenuOrders menuOrders) {
        Money totalBenefitAmount = calculateTotalDiscountAmount(reservationDate, menuOrders);
        totalBenefitAmount = totalBenefitAmount.add(
                calculateGiftEventDiscountAmount(menuOrders));

        return totalBenefitAmount;
    }

    private Money calculateTotalDiscountAmount(ReservationDate reservationDate, MenuOrders menuOrders) {
        Money totalDiscountAmount = Money.ZERO;
        totalDiscountAmount = totalDiscountAmount.add(
                dDayDiscountEventProcessor.calculateDiscountAmount(reservationDate, menuOrders));
        totalDiscountAmount = totalDiscountAmount.add(
                weekdayDiscountEventProcessor.calculateDiscountAmount(reservationDate, menuOrders));
        totalDiscountAmount = totalDiscountAmount.add(
                weekendDiscountEventProcessor.calculateDiscountAmount(reservationDate, menuOrders));
        totalDiscountAmount = totalDiscountAmount.add(
                specialDiscountEventProcessor.calculateDiscountAmount(reservationDate, menuOrders));

        return totalDiscountAmount;
    }

    public Money calculateChargeAfterDiscount(ReservationDate reservationDate, MenuOrders menuOrders) {
        Money preDiscountCharge = calculatePreDiscountCharge(menuOrders);
        Money totalDiscountAmount = calculateTotalDiscountAmount(reservationDate, menuOrders);

        return preDiscountCharge.minus(totalDiscountAmount);
    }
}
