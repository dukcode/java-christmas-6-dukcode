package christmas.service.event;

import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.Reservation;
import java.util.Optional;

public interface EventPolicy {

    Money calculateDiscountAmount(Reservation reservation);

    Optional<MenuQuantity> receiveGift(Reservation reservation);
}
