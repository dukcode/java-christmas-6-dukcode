package christmas.controller.dto.response;

import christmas.application.domain.Badge;
import christmas.application.domain.Money;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BadgeResponseTest {

    @Test
    public void Badge_로_BadgeResponse_를_생성할_수_있다() throws Exception {
        // given
        Badge badge = new Badge("배지", Money.of(3000));

        // when
        // then
        Assertions.assertThatCode(() -> {
            BadgeResponse.from(badge);
        }).doesNotThrowAnyException();
    }

    @Test
    public void toString_메서드_호출_시_Badge_의_이름을_반환한다() throws Exception {
        // given
        Badge badge = new Badge("배지", Money.of(3000));

        // when
        BadgeResponse badgeResponse = BadgeResponse.from(badge);

        // then
        Assertions.assertThat(badgeResponse.toString()).isEqualTo("배지");
    }

}