package christmas.controller;

import christmas.controller.dto.response.BadgeResponse;
import christmas.controller.dto.response.BenefitAmountsResponse;
import christmas.controller.dto.response.ChargeResponse;
import christmas.controller.dto.response.MenuQuantitiesResponse;
import christmas.controller.dto.response.ReservationDateResponse;
import christmas.controller.dto.response.TotalBenefitAmountResponse;
import java.util.Optional;

public interface OutputView {
    void printWelcomeMessage();

    void printError(Exception e);

    void printResultTitle(ReservationDateResponse reservationDate);

    void printMenuOrders(MenuQuantitiesResponse menuOrders);

    void printPreDiscountCharge(ChargeResponse preDiscountCharge);

    void printGifts(Optional<MenuQuantitiesResponse> menuQuantities);

    void printDiscountAmounts(Optional<BenefitAmountsResponse> benefitAmounts);

    void printTotalBenefitAmount(TotalBenefitAmountResponse totalBenefitAmount);

    void printChargeAfterDiscount(ChargeResponse chargeAfterDiscount);

    void printBadge(Optional<BadgeResponse> badgeResponse);
}
