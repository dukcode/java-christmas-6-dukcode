package christmas.service.event;

import christmas.domain.Reservation;

public interface EventCondition {
    boolean isSatisfiedBy(Reservation reservation);
}
