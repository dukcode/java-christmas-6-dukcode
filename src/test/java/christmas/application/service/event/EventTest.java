package christmas.application.service.event;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.application.domain.Menu;
import christmas.application.domain.MenuQuantity;
import christmas.application.domain.MenuType;
import christmas.application.domain.Money;
import christmas.application.service.Event;
import christmas.application.service.EventPolicy;
import christmas.mock.TestEventPolicy;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class EventTest {

    private EventPolicy eventPolicy;

    @BeforeEach
    public void init() {
        eventPolicy = new TestEventPolicy(Money.of(10_000L),
                new Menu("메뉴", Money.of(1_000L), MenuType.MAIN_DISH),
                2);
    }

    @Test
    public void 할인_금액을_계산할_수_있다() throws Exception {
        // given
        Event event = new Event("테스트", (reservation -> true), eventPolicy);
        // when
        Money discountAmount = event.calculateDiscountAmount(null);

        // then
        assertThat(discountAmount).isEqualTo(Money.of(10_000L));
    }

    @Test
    public void 이벤트_조건에_부합하지_않으면_할인_금액은_0원이다() throws Exception {
        // given
        Event event = new Event("테스트", (reservation -> false), eventPolicy);
        // when
        Money discountAmount = event.calculateDiscountAmount(null);

        // then
        assertThat(discountAmount).isEqualTo(Money.ZERO);
    }

    @Test
    public void 혜택_금액은_할인_금액과_증정품_전체_가격의_합이다() throws Exception {
        // given
        Event event = new Event("테스트", (reservation -> true), eventPolicy);

        // when
        Money benefitAmount = event.calculateBenefitAmount(null);

        // then
        assertThat(benefitAmount).isEqualTo(Money.of(12_000L));
    }

    @Test
    public void 이벤트_조건에_부합하지_않으면_혜택_금액은_0원이다() throws Exception {
        // given
        Event event = new Event("테스트1", (reservation -> false), eventPolicy);

        // when
        Money benefitAmount = event.calculateBenefitAmount(null);

        // then
        assertThat(benefitAmount).isEqualTo(Money.ZERO);
    }

    @Test
    public void 증정품을_받을_수_있다() throws Exception {
        // given
        Event event = new Event("테스트2", (reservation -> true), eventPolicy);

        // when
        Optional<MenuQuantity> gift = event.receiveGift(null);

        // then

        assertThat(gift.isEmpty()).isFalse();
        assertThat(gift.get().getMenu().getName()).isEqualTo("메뉴");
        assertThat(gift.get().getQuantity()).isEqualTo(2);
    }

    @Test
    public void 이벤트_조건에_부합하지_않으면_증정품을_받을_수_없다() throws Exception {
        // given
        Event event = new Event("테스트1", (reservation -> false), eventPolicy);

        // when
        Optional<MenuQuantity> gift = event.receiveGift(null);

        // then
        assertThat(gift.isEmpty()).isTrue();
    }

}