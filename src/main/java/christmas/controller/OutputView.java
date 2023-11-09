package christmas.controller;

import christmas.controller.dto.MenuOrdersResponse;
import christmas.controller.dto.ReservationDateResponse;

public interface OutputView {
    void printWelcomeMessage();

    void printError(Exception e);

    void printResultTitle(ReservationDateResponse reservationDateResponse);

    void printMenuOrders(MenuOrdersResponse menuOrdersResponse);
}
