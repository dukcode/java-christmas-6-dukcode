package christmas.application.service.event.condition;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

import christmas.application.domain.Menu;
import christmas.application.domain.MenuQuantity;
import christmas.application.domain.MenuType;
import christmas.application.domain.Money;
import christmas.application.domain.Order;
import christmas.application.domain.Reservation;
import christmas.application.domain.ReservationDate;
import christmas.application.service.event.condition.MinimumOrderAmountEventCondition;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MinimumOrderAmountEventConditionTest {

    @Test
    public void 총_주문금액이_최소_주문금액을_넘는지_판단할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));
        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 13));

        Reservation reservation = new Reservation(order, reservationDate);

        MinimumOrderAmountEventCondition condition1 = new MinimumOrderAmountEventCondition(Money.of(49_999L));
        MinimumOrderAmountEventCondition condition2 = new MinimumOrderAmountEventCondition(Money.of(50_000L));
        MinimumOrderAmountEventCondition condition3 = new MinimumOrderAmountEventCondition(Money.of(50_001L));

        // when
        // then
        assertThat(condition1.isSatisfiedBy(reservation)).isTrue();
        assertThat(condition2.isSatisfiedBy(reservation)).isTrue();
        assertThat(condition3.isSatisfiedBy(reservation)).isFalse();
    }
}