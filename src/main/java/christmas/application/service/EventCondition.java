package christmas.application.service;

import christmas.application.domain.Reservation;

public interface EventCondition {
    boolean isSatisfiedBy(Reservation reservation);

}
