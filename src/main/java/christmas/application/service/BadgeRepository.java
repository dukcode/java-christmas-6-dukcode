package christmas.application.service;

import christmas.application.domain.Badge;
import christmas.application.domain.Money;
import java.util.Optional;

public interface BadgeRepository {

    Optional<Badge> findBadgeByTotalBenefitAmount(Money totalBenefitAmount);
}
