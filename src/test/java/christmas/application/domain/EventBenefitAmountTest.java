package christmas.application.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import christmas.application.service.Event;
import christmas.application.service.EventPolicy;
import christmas.mock.TestEventPolicy;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EventBenefitAmountTest {

    @Test
    public void 이벤트와_혜택_금액으로_생성할_수_있다() throws Exception {
        // given
        EventPolicy eventPolicy = new TestEventPolicy(Money.of(10_000L),
                new Menu("메뉴", Money.of(1_000L), MenuType.MAIN_DISH),
                2);
        Event event = new Event("이벤트", r -> true, eventPolicy);

        // when
        // then
        assertThatCode(() -> {
            new EventBenefitAmount(event, Money.of(20_000L));
        }).doesNotThrowAnyException();

    }


}