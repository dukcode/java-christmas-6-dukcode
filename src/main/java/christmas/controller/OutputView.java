package christmas.controller;

import christmas.controller.dto.response.MenuOrdersResponse;
import christmas.controller.dto.response.MenuQuantityResponse;
import christmas.controller.dto.response.ReservationDateResponse;
import christmas.domain.Money;

public interface OutputView {
    void printWelcomeMessage();

    void printError(Exception e);

    void printResultTitle(ReservationDateResponse reservationDateResponse);

    void printMenuOrders(MenuOrdersResponse menuOrdersResponse);

    void printPreDiscountCharge(Money preDiscountCharge);

    void printGiftMenu(MenuQuantityResponse giftMenu);
}
