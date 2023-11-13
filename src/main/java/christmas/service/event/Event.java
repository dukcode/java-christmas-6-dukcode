package christmas.service.event;

import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.Reservation;

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
            MenuQuantity menuQuantity = eventPolicy.receiveGift(reservation);
            return discountAmount.add(menuQuantity.calculateCost());
        }

        return Money.ZERO;
    }

    public MenuQuantity receiveGift(Reservation reservation) {
        if (eventCondition.isSatisfiedBy(reservation)) {
            return eventPolicy.receiveGift(reservation);
        }

        return MenuQuantity.EMPTY;
    }

    public String getEventName() {
        return eventName;
    }
}
