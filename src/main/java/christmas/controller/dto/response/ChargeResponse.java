package christmas.controller.dto.response;

import christmas.domain.Money;

public class ChargeResponse {
    private final Money charge;

    private ChargeResponse(Money charge) {
        this.charge = charge;
    }

    public static ChargeResponse from(Money charge) {
        return new ChargeResponse(charge);
    }

    @Override
    public String toString() {
        if (charge.equals(Money.ZERO)) {
            return "없음";
        }

        return charge.toString();
    }
}
