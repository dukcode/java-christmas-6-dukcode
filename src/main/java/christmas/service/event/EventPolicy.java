package christmas.service.event;

import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.Reservation;

public interface EventPolicy {

    Money calculateDiscountAmount(Reservation reservation);

    MenuQuantity receiveGift(Reservation reservation);
}
