package christmas.view;

import christmas.controller.OutputView;
import christmas.controller.dto.response.BadgeResponse;
import christmas.controller.dto.response.BenefitAmountsResponse;
import christmas.controller.dto.response.ChargeResponse;
import christmas.controller.dto.response.MenuQuantitiesResponse;
import christmas.controller.dto.response.ReservationDateResponse;
import christmas.controller.dto.response.TotalBenefitAmountResponse;
import java.util.Objects;

public class OutputConsoleView implements OutputView {

    private static final String RESULT_TITLE_FORMAT = "%d월 %d일에 우테코 식당에서 받을 이벤트 혜택 미리 보기!\n\n";
    private static final String MENU_ORDERS_TITLE = "<주문 메뉴>";
    private static final String PRE_DISCOUNT_CHARGE_TITLE = "<할인 전 총주문 금액>";
    private static final String GIFTS_TITLE = "<증정 메뉴>";
    private static final String BENEFIT_AMOUNTS_TITLE = "<혜택 내역>";
    private static final String TOTAL_BENEFIT_AMOUNT_TITLE = "<총혜택 금액>";
    private static final String CHARGE_AFTER_DISCOUNT_TITLE = "<할인 후 예상 결제 금액>";
    private static final String EVENT_BADGE_RESPONSE_TITLE_FORMAT = "<%d월 이벤트 배지>\n";
    private static final String ERROR_PREFIX = "[ERROR] ";
    private static final String ERROR_SUFFIX = " 다시 입력해 주세요.";

    private static final String EMPTY_MESSAGE = "없음";

    private final int eventYear;
    private final int eventMonth;

    public OutputConsoleView(int eventYear, int eventMonth) {
        this.eventYear = eventYear;
        this.eventMonth = eventMonth;
    }

    @Override
    public void printResultTitle(ReservationDateResponse reservationDateResponse) {
        System.out.printf(RESULT_TITLE_FORMAT,
                eventMonth,
                reservationDateResponse.getDayOfMonth());
    }

    @Override
    public void printMenuOrders(MenuQuantitiesResponse menuOrdersResponse) {
        System.out.println(MENU_ORDERS_TITLE);
        System.out.println(menuOrdersResponse);
        System.out.println();
    }

    @Override
    public void printPreDiscountCharge(ChargeResponse preDiscountCharge) {
        System.out.println(PRE_DISCOUNT_CHARGE_TITLE);
        System.out.println(preDiscountCharge);
        System.out.println();
    }

    @Override
    public void printGifts(MenuQuantitiesResponse menuQuantities) {
        System.out.println(GIFTS_TITLE);
        System.out.println(convertWhenNull(menuQuantities));
        System.out.println();
    }


    @Override
    public void printBenefitAmounts(BenefitAmountsResponse benefitAmounts) {
        System.out.println(BENEFIT_AMOUNTS_TITLE);
        System.out.println(convertWhenNull(benefitAmounts));
        System.out.println();
    }

    @Override
    public void printTotalBenefitAmount(TotalBenefitAmountResponse totalBenefitAmount) {
        System.out.println(TOTAL_BENEFIT_AMOUNT_TITLE);
        System.out.println(totalBenefitAmount);
        System.out.println();
    }

    @Override
    public void printChargeAfterDiscount(ChargeResponse chargeAfterDiscount) {
        System.out.println(CHARGE_AFTER_DISCOUNT_TITLE);
        System.out.println(chargeAfterDiscount);
        System.out.println();
    }

    @Override
    public void printBadge(BadgeResponse badge) {
        System.out.printf(EVENT_BADGE_RESPONSE_TITLE_FORMAT, eventYear);
        System.out.println(convertWhenNull(badge));
        System.out.println();
    }

    private <T> String convertWhenNull(T optional) {

        if (Objects.isNull(optional)) {
            return EMPTY_MESSAGE;
        }

        return optional.toString();
    }

    @Override
    public void printError(Exception e) {
        System.out.println(ERROR_PREFIX + e.getMessage() + ERROR_SUFFIX);
    }

}
