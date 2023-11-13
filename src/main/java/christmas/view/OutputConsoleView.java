package christmas.view;

import christmas.controller.OutputView;
import christmas.controller.dto.response.BadgeResponse;
import christmas.controller.dto.response.BenefitAmountsResponse;
import christmas.controller.dto.response.ChargeResponse;
import christmas.controller.dto.response.DiscountAmountResponse;
import christmas.controller.dto.response.MenuQuantitiesResponse;
import christmas.controller.dto.response.ReservationDateResponse;

public class OutputConsoleView implements OutputView {

    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String ERROR_SUFFIX = " 다시 입력해 주세요.";

    private final int eventYear;
    private final int eventMonth;

    public OutputConsoleView(int eventYear, int eventMonth) {
        this.eventYear = eventYear;
        this.eventMonth = eventMonth;
    }

    @Override
    public void printWelcomeMessage() {
        System.out.printf("안녕하세요! 우테코 식당 %d월 이벤트 플래너입니다.\n", eventMonth);
    }

    @Override
    public void printError(Exception e) {
        System.out.println(ERROR_PREFIX + e.getMessage() + ERROR_SUFFIX);
    }

    @Override
    public void printResultTitle(ReservationDateResponse reservationDateResponse) {
        System.out.printf("%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n\n",
                eventMonth,
                reservationDateResponse.getDayOfMonth());
    }

    @Override
    public void printMenuOrders(MenuQuantitiesResponse menuOrdersResponse) {
        System.out.println("<주문 메뉴>");
        System.out.println(menuOrdersResponse);
    }

    @Override
    public void printPreDiscountCharge(ChargeResponse preDiscountCharge) {
        System.out.println("<할인 전 총주문 금액>");
        System.out.println(preDiscountCharge);
        System.out.println();
    }

    @Override
    public void printGifts(MenuQuantitiesResponse menuQuantitiesResponse) {
        System.out.println("<증정 메뉴>");
        System.out.println(menuQuantitiesResponse);
    }

    @Override
    public void printDiscountAmounts(BenefitAmountsResponse benefitAmountsResponse) {
        System.out.println("<혜택 내역>");
        System.out.println(benefitAmountsResponse);
    }

    @Override
    public void printTotalBenefitAmount(DiscountAmountResponse discountAmountResponse) {
        System.out.println("<총혜택 금액>");
        System.out.println(discountAmountResponse);
        System.out.println();
    }

    @Override
    public void printChargeAfterDiscount(ChargeResponse chargeAfterDiscount) {
        System.out.println("<할인 후 예상 결제 금액>");
        System.out.println(chargeAfterDiscount);
        System.out.println();
    }

    @Override
    public void printBadge(BadgeResponse badgeResponse) {
        System.out.println("<12월 이벤트 배지>");
        System.out.println(badgeResponse);
        System.out.println();
    }
}
