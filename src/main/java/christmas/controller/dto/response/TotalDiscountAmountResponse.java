package christmas.controller.dto.response;

import christmas.domain.Money;

public class TotalDiscountAmountResponse {

    private static final String TOTAL_DISCOUNT_AMOUNT_STRING_FORMAT = "-%s";

    private final Money totalDiscountAmount;

    public TotalDiscountAmountResponse(Money totalDiscountAmount) {
        this.totalDiscountAmount = totalDiscountAmount;
    }

    @Override
    public String toString() {
        if (totalDiscountAmount.equals(Money.ZERO)) {
            return "없음";
        }

        return String.format(TOTAL_DISCOUNT_AMOUNT_STRING_FORMAT, totalDiscountAmount.toString());
    }
}
