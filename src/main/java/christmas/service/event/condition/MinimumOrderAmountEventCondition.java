package christmas.service.event.condition;

import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.service.event.EventCondition;

public class MinimumOrderAmountEventCondition implements EventCondition {

    private final Money minimumOrderAmount;

    public MinimumOrderAmountEventCondition(Money minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        Money orderAmount = reservation.calculateTotalOrderAmount();
        return orderAmount.isGreaterThanOrEqual(minimumOrderAmount);
    }
}
