package christmas.controller;

import christmas.controller.dto.response.ChargeResponse;
import christmas.controller.dto.response.DiscountAmountResponse;
import christmas.controller.dto.response.DiscountAmountsResponse;
import christmas.controller.dto.response.MenuOrdersResponse;
import christmas.controller.dto.response.MenuQuantityResponse;
import christmas.controller.dto.response.ReservationDateResponse;

public interface OutputView {
    void printWelcomeMessage();

    void printError(Exception e);

    void printResultTitle(ReservationDateResponse reservationDateResponse);

    void printMenuOrders(MenuOrdersResponse menuOrdersResponse);

    void printPreDiscountCharge(ChargeResponse preDiscountCharge);

    void printGiftMenu(MenuQuantityResponse giftMenu);

    void printDiscountAmounts(DiscountAmountsResponse discountAmountsResponse);

    void printTotalBenefitAmount(DiscountAmountResponse discountAmountResponse);

    void printChargeAfterDiscount(ChargeResponse chargeAfterDiscount);
}
