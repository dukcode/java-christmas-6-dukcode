package christmas.controller.dto.response;

import christmas.application.domain.Money;

public class TotalBenefitAmountResponse {

    private static final String ZERO_STRING_FOTMAT = "없음";
    private static final String TOTAL_DISCOUNT_AMOUNT_STRING_FORMAT = "-%s";

    private final Money totalDiscountAmount;

    private TotalBenefitAmountResponse(Money totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
    }

    public static TotalBenefitAmountResponse from(Money totalDiscountAmount) {
        return new TotalBenefitAmountResponse(totalDiscountAmount);
    }

    @Override
    public String toString() {
        if (totalDiscountAmount.equals(Money.ZERO)) {
            return ZERO_STRING_FOTMAT;
        }

        return String.format(TOTAL_DISCOUNT_AMOUNT_STRING_FORMAT, totalDiscountAmount);
    }
}
