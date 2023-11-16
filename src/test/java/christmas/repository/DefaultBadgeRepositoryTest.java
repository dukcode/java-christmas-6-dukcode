package christmas.repository;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.application.domain.Badge;
import christmas.application.domain.Money;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DefaultBadgeRepositoryTest {

    @Test
    public void 총_혜택_금액에_맞는_배지를_가져올_수_있다() throws Exception {
        // given
        DefaultBadgeRepository repository = new DefaultBadgeRepository();

        // when
        Optional<Badge> badge1 = repository.findBadgeByTotalBenefitAmount(
                Money.of(4_999L));

        Optional<Badge> badge2 = repository.findBadgeByTotalBenefitAmount(
                Money.of(5_000L));
        Optional<Badge> badge3 = repository.findBadgeByTotalBenefitAmount(
                Money.of(5_001L));
        Optional<Badge> badge4 = repository.findBadgeByTotalBenefitAmount(
                Money.of(9_999L));

        Optional<Badge> badge5 = repository.findBadgeByTotalBenefitAmount(
                Money.of(10_000));
        Optional<Badge> badge6 = repository.findBadgeByTotalBenefitAmount(
                Money.of(10_001));

        Optional<Badge> badge7 = repository.findBadgeByTotalBenefitAmount(
                Money.of(19_999));

        Optional<Badge> badge8 = repository.findBadgeByTotalBenefitAmount(
                Money.of(20_000));
        Optional<Badge> badge9 = repository.findBadgeByTotalBenefitAmount(
                Money.of(20_001));

        // then
        assertThat(badge1.isEmpty()).isEqualTo(true);

        assertThat(badge2.get().getName()).isEqualTo("별");
        assertThat(badge3.get().getName()).isEqualTo("별");
        assertThat(badge4.get().getName()).isEqualTo("별");

        assertThat(badge5.get().getName()).isEqualTo("트리");
        assertThat(badge6.get().getName()).isEqualTo("트리");
        assertThat(badge7.get().getName()).isEqualTo("트리");

        assertThat(badge8.get().getName()).isEqualTo("산타");
        assertThat(badge9.get().getName()).isEqualTo("산타");
    }

}