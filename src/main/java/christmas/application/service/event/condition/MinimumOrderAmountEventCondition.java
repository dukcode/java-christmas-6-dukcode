package christmas.application.service.event.condition;

import christmas.application.domain.Money;
import christmas.application.domain.Reservation;
import christmas.application.service.EventCondition;

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
