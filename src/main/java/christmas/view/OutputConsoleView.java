package christmas.view;

import christmas.controller.OutputView;
import christmas.controller.dto.response.DiscountAmountsResponse;
import christmas.controller.dto.response.MenuOrdersResponse;
import christmas.controller.dto.response.MenuQuantityResponse;
import christmas.controller.dto.response.ReservationDateResponse;
import christmas.controller.dto.response.TotalDiscountAmountResponse;
import christmas.domain.Money;

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
    public void printMenuOrders(MenuOrdersResponse menuOrdersResponse) {
        System.out.println("<주문 메뉴>");
        System.out.println(menuOrdersResponse);
    }

    @Override
    public void printPreDiscountCharge(Money preDiscountCharge) {
        System.out.println("<할인 전 총주문 금액>");
        System.out.println(preDiscountCharge);
        System.out.println();
    }

    @Override
    public void printGiftMenu(MenuQuantityResponse giftMenu) {
        System.out.println("<증정 메뉴>");
        System.out.println(giftMenu);
        System.out.println();
    }

    @Override
    public void printDiscountAmounts(DiscountAmountsResponse discountAmountsResponse) {
        System.out.println("<혜택 내역>");
        System.out.println(discountAmountsResponse);
        System.out.println();
    }

    @Override
    public void printTotalBenefitAmount(TotalDiscountAmountResponse totalDiscountAmountResponse) {
        System.out.println("<혜택 내역>");
        System.out.println(totalDiscountAmountResponse);
        System.out.println();
    }

}
