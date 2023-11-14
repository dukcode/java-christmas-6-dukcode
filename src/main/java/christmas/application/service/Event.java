package christmas.application.service;

import christmas.application.domain.MenuQuantity;
import christmas.application.domain.Money;
import christmas.application.domain.Reservation;
import java.util.Optional;

public class Event {

    private final String eventName;
    private final EventCondition eventCondition;
    private final EventPolicy eventPolicy;

    public Event(String eventName, EventCondition eventCondition, EventPolicy eventPolicy) {
        this.eventName = eventName;
        this.eventCondition = eventCondition;
        this.eventPolicy = eventPolicy;
    }

    public Money calculateDiscountAmount(Reservation reservation) {
        if (eventCondition.isSatisfiedBy(reservation)) {
            return eventPolicy.calculateDiscountAmount(reservation);
        }

        return Money.ZERO;
    }

    public Money calculateBenefitAmount(Reservation reservation) {
        if (eventCondition.isSatisfiedBy(reservation)) {
            Money discountAmount = eventPolicy.calculateDiscountAmount(reservation);
            Optional<MenuQuantity> giftOptional = eventPolicy.receiveGift(reservation);
            if (giftOptional.isEmpty()) {
                return discountAmount;
            }

            MenuQuantity gift = giftOptional.get();
            return discountAmount.add(gift.calculateCost());
        }

        return Money.ZERO;
    }

    public Optional<MenuQuantity> receiveGift(Reservation reservation) {
        if (eventCondition.isSatisfiedBy(reservation)) {
            return eventPolicy.receiveGift(reservation);
        }

        return Optional.empty();
    }

    public String getEventName() {
        return eventName;
    }
}
