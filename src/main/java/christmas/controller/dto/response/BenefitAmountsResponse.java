package christmas.controller.dto.response;

import christmas.domain.Money;
import java.util.HashMap;
import java.util.Map;

public class BenefitAmountsResponse {

    private static final String EVENT_NAME_DISCOUNT_AMOUNT_STRING_FORMAT = "%s: -%s";
    private final Map<String, Money> benefitAmounts = new HashMap<>();

    public BenefitAmountsResponse(Map<String, Money> benefitAmounts) {
        for (String eventTitle : benefitAmounts.keySet()) {
            Money benefitAmount = benefitAmounts.get(eventTitle);

            if (benefitAmount.equals(Money.ZERO)) {
                continue;
            }

            this.benefitAmounts.put(eventTitle, benefitAmount);
        }
    }

    @Override
    public String toString() {
        if (isAllZero()) {
            return "없음\n";
        }

        return createEventDiscountAmountString();
    }

    private boolean isAllZero() {
        for (String title : benefitAmounts.keySet()) {
            Money discountAmount = benefitAmounts.get(title);
            if (!discountAmount.equals(Money.ZERO)) {
                return false;
            }
        }
        return true;
    }

    private String createEventDiscountAmountString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String title : benefitAmounts.keySet()) {
            Money discountAmount = benefitAmounts.get(title);
            if (discountAmount.equals(Money.ZERO)) {
                continue;
            }
            stringBuilder.append(String.format(EVENT_NAME_DISCOUNT_AMOUNT_STRING_FORMAT,
                    title, benefitAmounts.get(title).toString()));
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

}
