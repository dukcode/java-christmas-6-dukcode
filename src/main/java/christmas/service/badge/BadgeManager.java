package christmas.service.badge;

import christmas.domain.Badge;
import christmas.domain.Money;
import java.util.Optional;

public class BadgeManager {

    public BadgeManager() {
    }

    public Optional<Badge> awardBadge(Money totalBenefitAmount) {
        for (Badge badge : Badge.values()) {
            if (badge.canBeAward(totalBenefitAmount)) {
                return Optional.of(badge);
            }
        }

        return Optional.empty();
    }
}
