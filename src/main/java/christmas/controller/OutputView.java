package christmas.controller;

import christmas.controller.dto.response.BadgeResponse;
import christmas.controller.dto.response.BenefitAmountsResponse;
import christmas.controller.dto.response.ChargeResponse;
import christmas.controller.dto.response.MenuQuantitiesResponse;
import christmas.controller.dto.response.ReservationDateResponse;
import christmas.controller.dto.response.TotalBenefitAmountResponse;

public interface OutputView {

    void printError(Exception e);

    void printResultTitle(ReservationDateResponse reservationDate);

    void printMenuOrders(MenuQuantitiesResponse menuOrders);

    void printPreDiscountCharge(ChargeResponse preDiscountCharge);

    void printGifts(MenuQuantitiesResponse menuQuantities);

    void printBenefitAmounts(BenefitAmountsResponse benefitAmounts);

    void printTotalBenefitAmount(TotalBenefitAmountResponse totalBenefitAmount);

    void printChargeAfterDiscount(ChargeResponse chargeAfterDiscount);

    void printBadge(BadgeResponse badgeResponse);

}
