package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BadgeTest {

    @Test
    public void 전체_혜택_금액이_배지_수여_최소_금액_이상일_시_배지를_수여한다() throws Exception {
        // given
        Money totalBenefitAmount = Money.of(10_000L);
        Badge badge = new Badge("배지", Money.of(10_000L));

        // when
        boolean canBeAward = badge.canBeAward(totalBenefitAmount);

        // then
        assertThat(canBeAward).isEqualTo(true);
    }

    @Test
    public void 전체_혜택_금액이_배지_수여_최소_금액_미만일_시_배지를_수여한다() throws Exception {
        // given
        Money totalBenefitAmount = Money.of(9_999L);
        Badge badge = new Badge("배지", Money.of(10_000L));

        // when
        boolean canBeAward = badge.canBeAward(totalBenefitAmount);

        // then
        assertThat(canBeAward).isEqualTo(false);
    }
}