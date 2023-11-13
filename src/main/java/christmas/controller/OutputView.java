package christmas.controller;

import christmas.controller.dto.response.BadgeResponse;
import christmas.controller.dto.response.BenefitAmountsResponse;
import christmas.controller.dto.response.ChargeResponse;
import christmas.controller.dto.response.DiscountAmountResponse;
import christmas.controller.dto.response.MenuQuantitiesResponse;
import christmas.controller.dto.response.ReservationDateResponse;

public interface OutputView {
    void printWelcomeMessage();

    void printError(Exception e);

    void printResultTitle(ReservationDateResponse reservationDateResponse);

    void printMenuOrders(MenuQuantitiesResponse menuOrdersResponse);

    void printPreDiscountCharge(ChargeResponse preDiscountCharge);

    void printGifts(MenuQuantitiesResponse menuQuantitiesResponse);

    void printDiscountAmounts(BenefitAmountsResponse benefitAmountsResponse);

    void printTotalBenefitAmount(DiscountAmountResponse discountAmountResponse);

    void printChargeAfterDiscount(ChargeResponse chargeAfterDiscount);

    void printBadge(BadgeResponse badgeResponse);
}
