package christmas.service;

import christmas.domain.Badge;
import christmas.domain.Menu;
import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.service.event.Event;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromotionService {

    private final List<Event> events;
    private final BadgeAwardEventProcessor badgeAwardEventProcessor;

    public PromotionService(List<Event> events, BadgeAwardEventProcessor badgeAwardEventProcessor) {
        this.events = events;
        this.badgeAwardEventProcessor = badgeAwardEventProcessor;
    }

    public Money calculatePreDiscountCharge(Reservation reservation) {
        return reservation.calculateTotalCost();
    }

    public Map<Menu, Integer> receiveGifts(Reservation reservation) {

        Map<Menu, Integer> gifts = new HashMap<>();
        for (Event event : events) {
            MenuQuantity gift = event.receiveGift(reservation);
            if (gift.isNone()) {
                continue;
            }

            gifts.put(gift.getMenu(), gifts.getOrDefault(gift.getMenu(), 0) + 1);
        }
        return gifts;
    }

    public Badge recieveBadge(Reservation reservation) {
        Money totalBenefitAmount = calculateTotalBenefitAmount(reservation);
        return badgeAwardEventProcessor.applyEvent(totalBenefitAmount);
    }

    public Money calculateTotalBenefitAmount(Reservation reservation) {
        Money totalBenefitAmount = Money.ZERO;
        for (Event event : events) {
            totalBenefitAmount = totalBenefitAmount.add(event.calculateBenefitAmount(reservation));
        }
        return totalBenefitAmount;
    }

    public Map<String, Money> calculateBenefitAmounts(Reservation reservation) {
        Map<String, Money> benefitAmounts = new HashMap<>();
        for (Event event : events) {
            benefitAmounts.put(event.getEventName(), event.calculateBenefitAmount(reservation));
        }
        return benefitAmounts;
    }

    public Money calculateTotalDiscountAmount(Reservation reservation) {
        Money totalDiscountAmount = Money.ZERO;
        for (Event event : events) {
            totalDiscountAmount = totalDiscountAmount.add(event.calculateDiscountAmount(reservation));
        }
        return totalDiscountAmount;
    }

    public Money calculateChargeAfterDiscount(Reservation reservation) {
        Money preDiscountCharge = calculatePreDiscountCharge(reservation);
        Money totalDiscountAmount = calculateTotalDiscountAmount(reservation);
        return preDiscountCharge.minus(totalDiscountAmount);
    }
}
