package christmas.service.event.condition;

import christmas.domain.Reservation;
import christmas.service.event.EventCondition;
import java.util.List;

public class CompositeEventCondition implements EventCondition {

    private final List<EventCondition> eventConditions;

    public CompositeEventCondition(EventCondition... eventConditions) {
        this.eventConditions = List.of(eventConditions);
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        for (EventCondition eventCondition : eventConditions) {
            if (!eventCondition.isSatisfiedBy(reservation)) {
                return false;
            }
        }

        return true;
    }
}
