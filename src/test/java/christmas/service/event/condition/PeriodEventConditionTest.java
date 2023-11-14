package christmas.service.event.condition;

import static org.assertj.core.api.Assertions.*;

import christmas.domain.Menu;
import christmas.domain.MenuQuantity;
import christmas.domain.MenuType;
import christmas.domain.Money;
import christmas.domain.Order;
import christmas.domain.Reservation;
import christmas.domain.ReservationDate;
import java.time.LocalDate;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PeriodEventConditionTest {

    @Test
    public void 예약일이_이벤트_기간_내_인지_판단할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));
        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 13));

        Reservation reservation = new Reservation(order, reservationDate);

        PeriodEventCondition periodEventCondition1 = PeriodEventCondition.ofRange(
                LocalDate.of(2023, 12, 11),
                LocalDate.of(2023, 12, 12));
        PeriodEventCondition periodEventCondition2 = PeriodEventCondition.ofRange(
                LocalDate.of(2023, 12, 12),
                LocalDate.of(2023, 12, 13));
        PeriodEventCondition periodEventCondition3 = PeriodEventCondition.ofRange(
                LocalDate.of(2023, 12, 13),
                LocalDate.of(2023, 12, 14));
        PeriodEventCondition periodEventCondition4 = PeriodEventCondition.ofRange(
                LocalDate.of(2023, 12, 14),
                LocalDate.of(2023, 12, 15));

        // when
        // then
        assertThat(periodEventCondition1.isSatisfiedBy(reservation)).isFalse();
        assertThat(periodEventCondition2.isSatisfiedBy(reservation)).isTrue();
        assertThat(periodEventCondition3.isSatisfiedBy(reservation)).isTrue();
        assertThat(periodEventCondition4.isSatisfiedBy(reservation)).isFalse();
    }

}