package christmas.controller.dto.response;

import christmas.domain.Money;
import java.util.HashMap;
import java.util.Map;

public class BenefitsResponse {

    private static final String CHRISTMAS_D_DAY_EVENT_TITLE = "크리스마스 디데이 할인";
    private static final String WEEKDAY_EVENT_TITLE = "평일 할인";
    private static final String WEEKEND_EVENT_TITLE = "주말 할인";
    private static final String SPECIAL_EVENT_TITLE = "특별 할인";
    private static final String GIFT_EVENT_TITLE = "증정 이벤트";

    private static final String EVENT_NAME_DISCOUNT_AMOUNT_STRING_FORMAT = "%s: -%s";
    private final Map<String, Money> discountAmounts = new HashMap<>();

    public BenefitsResponse(Money christmasDDayEventDiscountAmount, Money weekdayEventDiscountAmount,
                            Money weekendEventDiscountAmount, Money specialEventDiscountAmount,
                            Money giftEventDiscountAmount) {
        discountAmounts.put(CHRISTMAS_D_DAY_EVENT_TITLE, christmasDDayEventDiscountAmount);
        discountAmounts.put(WEEKDAY_EVENT_TITLE, weekdayEventDiscountAmount);
        discountAmounts.put(WEEKEND_EVENT_TITLE, weekendEventDiscountAmount);
        discountAmounts.put(SPECIAL_EVENT_TITLE, specialEventDiscountAmount);
        discountAmounts.put(GIFT_EVENT_TITLE, giftEventDiscountAmount);
    }

    @Override
    public String toString() {
        if (discountAmounts.isEmpty()) {
            return "없음";
        }

        return createEventDiscountAmountString();
    }

    private String createEventDiscountAmountString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (String title : discountAmounts.keySet()) {
            Money discountAmount = discountAmounts.get(title);
            if (discountAmount.equals(Money.ZERO)) {
                continue;
            }
            stringBuilder.append(String.format(EVENT_NAME_DISCOUNT_AMOUNT_STRING_FORMAT,
                    title, discountAmounts.get(title).toString()));
            stringBuilder.append("\n");
        }

        return stringBuilder.toString();
    }

}
