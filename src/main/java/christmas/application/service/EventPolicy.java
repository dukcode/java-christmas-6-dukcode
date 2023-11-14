package christmas.application.service;

import christmas.application.domain.MenuQuantity;
import christmas.application.domain.Money;
import christmas.application.domain.Reservation;
import java.util.Optional;

public interface EventPolicy {

    Money calculateDiscountAmount(Reservation reservation);

    Optional<MenuQuantity> receiveGift(Reservation reservation);
}
