package christmas.service.badge;

import christmas.domain.Badge;
import christmas.domain.Money;

public class BadgeManager {

    public BadgeManager() {
    }

    public Badge awardBadge(Money totalBenefitAmount) {
        for (Badge badge : Badge.values()) {
            if (badge.canBeAward(totalBenefitAmount)) {
                return badge;
            }
        }

        return Badge.NONE;
    }
}
