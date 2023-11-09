package christmas.controller;

import christmas.controller.dto.MenuOrdersResponse;
import christmas.controller.dto.ReservationDateResponse;
import christmas.domain.Money;

public interface OutputView {
    void printWelcomeMessage();

    void printError(Exception e);

    void printResultTitle(ReservationDateResponse reservationDateResponse);

    void printMenuOrders(MenuOrdersResponse menuOrdersResponse);

    void printPreDiscountCharge(Money preDiscountCharge);
}
