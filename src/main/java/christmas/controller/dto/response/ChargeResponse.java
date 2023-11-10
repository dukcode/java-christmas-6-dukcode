package christmas.controller.dto.response;

import christmas.domain.Money;

public class ChargeResponse {
    private final Money charge;

    public ChargeResponse(Money charge) {
        this.charge = charge;
    }

    @Override
    public String toString() {
        if (charge.equals(Money.ZERO)) {
            return "없음";
        }

        return charge.toString();
    }
}
