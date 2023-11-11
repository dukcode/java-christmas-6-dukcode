package christmas.service;

import christmas.domain.Badge;
import christmas.domain.Money;

public class BadgeAwardEventProcessor {

    public BadgeAwardEventProcessor() {
    }

    public Badge applyEvent(Money totalBenefitAmount) {
        for (Badge badge : Badge.values()) {
            if (badge.canBeAwardedBadge(totalBenefitAmount)) {
                return badge;
            }
        }

        return Badge.NONE;
    }
}
