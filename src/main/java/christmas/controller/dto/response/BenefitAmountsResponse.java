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
        StringBuilder stringBuilder = new StringBuilder();
        for (String title : benefitAmounts.keySet()) {
            Money benefitAmount = benefitAmounts.get(title);
            stringBuilder.append(String.format(EVENT_NAME_DISCOUNT_AMOUNT_STRING_FORMAT,
                    title, benefitAmount));
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

}
