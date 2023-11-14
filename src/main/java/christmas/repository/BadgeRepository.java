package christmas.repository;

import christmas.domain.Badge;
import christmas.domain.Money;
import java.util.Optional;

public interface BadgeRepository {

    Optional<Badge> findBadgeByTotalBenefitAmount(Money totalBenefitAmount);
}
