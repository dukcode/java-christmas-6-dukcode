package christmas.service;

import christmas.domain.Badge;
import christmas.domain.Menu;
import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.repository.BadgeRepository;
import christmas.service.event.Event;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class PromotionService {

    private final List<Event> events;
    private final BadgeRepository badgeRepository;

    public PromotionService(List<Event> events, BadgeRepository badgeRepository) {
        this.events = events;
        this.badgeRepository = badgeRepository;
    }

    public Money calculatePreDiscountCharge(Reservation reservation) {
        return reservation.calculateTotalCost();
    }

    public Map<Menu, Integer> receiveGifts(Reservation reservation) {

        Map<Menu, Integer> gifts = new HashMap<>();
        for (Event event : events) {
            Optional<MenuQuantity> giftOptional = event.receiveGift(reservation);
            if (giftOptional.isEmpty()) {
                continue;
            }

            MenuQuantity gift = giftOptional.get();
            gifts.put(gift.getMenu(), gifts.getOrDefault(gift.getMenu(), 0) + 1);
        }
        return gifts;
    }

    public Optional<Badge> receiveBadge(Reservation reservation) {
        Money totalBenefitAmount = calculateTotalBenefitAmount(reservation);
        return badgeRepository.findBadgeByTotalBenefitAmount(totalBenefitAmount);
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
