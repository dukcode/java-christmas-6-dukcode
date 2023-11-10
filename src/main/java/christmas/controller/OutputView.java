package christmas.controller;

import christmas.controller.dto.response.DiscountAmountsResponse;
import christmas.controller.dto.response.MenuOrdersResponse;
import christmas.controller.dto.response.MenuQuantityResponse;
import christmas.controller.dto.response.ReservationDateResponse;
import christmas.controller.dto.response.TotalDiscountAmountResponse;
import christmas.domain.Money;

public interface OutputView {
    void printWelcomeMessage();

    void printError(Exception e);

    void printResultTitle(ReservationDateResponse reservationDateResponse);

    void printMenuOrders(MenuOrdersResponse menuOrdersResponse);

    void printPreDiscountCharge(Money preDiscountCharge);

    void printGiftMenu(MenuQuantityResponse giftMenu);

    void printDiscountAmounts(DiscountAmountsResponse discountAmountsResponse);

    void printTotalBenefitAmount(TotalDiscountAmountResponse totalDiscountAmountResponse);
}
