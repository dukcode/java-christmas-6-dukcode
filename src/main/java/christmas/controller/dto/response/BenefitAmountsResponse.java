package christmas.controller.dto.response;

import christmas.domain.Money;
import java.util.HashMap;
import java.util.Map;
import java.util.StringJoiner;

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
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (String title : benefitAmounts.keySet()) {
            Money benefitAmount = benefitAmounts.get(title);
            stringJoiner.add(String.format(EVENT_NAME_DISCOUNT_AMOUNT_STRING_FORMAT,
                    title, benefitAmount));
        }

        return stringJoiner.toString();
    }

}
