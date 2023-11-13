package christmas.service.event.condition;

import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.service.event.EventCondition;

public class MinimumOrderAmountCondition implements EventCondition {

    private final Money minimumOrderAmount;

    public MinimumOrderAmountCondition(Money minimumOrderAmount) {
        this.minimumOrderAmount = minimumOrderAmount;
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        Money orderAmount = reservation.calculateTotalOrderAmount();
        return orderAmount.isGreaterThanOrEqual(minimumOrderAmount);
    }
}
