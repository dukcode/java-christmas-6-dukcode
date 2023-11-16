package christmas.application.domain;

import christmas.application.service.Event;

public class EventBenefitAmount {
    private final Event event;
    private final Money benefitAmount;

    public EventBenefitAmount(Event event, Money benefitAmount) {
        this.event = event;
        this.benefitAmount = benefitAmount;
    }

    public String getEventName() {
        return event.getEventName();
    }

    public Money getBenefitAmount() {
        return benefitAmount;
    }

}
