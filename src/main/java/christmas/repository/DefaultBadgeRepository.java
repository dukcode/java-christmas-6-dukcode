package christmas.repository;

import christmas.domain.Badge;
import christmas.domain.Money;
import java.util.List;
import java.util.Optional;

public class DefaultBadgeRepository implements BadgeRepository {

    private final List<Badge> badges;

    public DefaultBadgeRepository() {
        badges = initBadges();
    }

    private List<Badge> initBadges() {
        return List.of(
                new Badge("산타", Money.of(20_000L)),
                new Badge("트리", Money.of(10_000L)),
                new Badge("별", Money.of(5_000L)));
    }

    @Override
    public Optional<Badge> findBadgeByTotalBenefitAmount(Money totalBenefitAmount) {
        for (Badge badge : badges) {
            if (totalBenefitAmount.isGreaterThanOrEqual(badge.getMinBadgeAwardAmount())) {
                return Optional.of(badge);
            }
        }

        return Optional.empty();
    }
}
