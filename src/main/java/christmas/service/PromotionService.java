package christmas.service;

import christmas.domain.Badge;
import christmas.domain.Menu;
import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.service.badge.BadgeManager;
import christmas.service.event.Event;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PromotionService {

    private final List<Event> events;
    private final BadgeManager badgeManager;

    public PromotionService(List<Event> events, BadgeManager badgeManager) {
        this.events = events;
        this.badgeManager = badgeManager;
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
        return badgeManager.awardBadge(totalBenefitAmount);
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
            Money benefitAmount = event.calculateBenefitAmount(reservation);
            if (benefitAmount.equals(Money.ZERO)) {
                continue;
            }

            benefitAmounts.put(event.getEventName(), benefitAmount);
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
