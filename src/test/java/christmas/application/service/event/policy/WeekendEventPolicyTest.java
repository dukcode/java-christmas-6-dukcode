package christmas.application.service.event.policy;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.application.domain.Menu;
import christmas.application.domain.MenuQuantity;
import christmas.application.domain.MenuType;
import christmas.application.domain.Money;
import christmas.application.domain.Order;
import christmas.application.domain.Reservation;
import christmas.application.domain.ReservationDate;
import christmas.application.service.event.policy.WeekendEventPolicy;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class WeekendEventPolicyTest {

    @Test
    public void 예약일이_주말이면_정해진_메뉴타입_당_할인이_적용된다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);
        Menu menu3 = new Menu("메뉴3", Money.of(30_000L), MenuType.DESSERT);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2),
                new MenuQuantity(menu3, 3)));

        ReservationDate reservationDate1 = new ReservationDate(LocalDate.of(2023, 12, 9)); // 평일
        ReservationDate reservationDate2 = new ReservationDate(LocalDate.of(2023, 12, 10)); // 주말

        Reservation reservation1 = new Reservation(order, reservationDate1);
        Reservation reservation2 = new Reservation(order, reservationDate2);

        WeekendEventPolicy weekendEventPolicy1 = new WeekendEventPolicy(MenuType.MAIN_DISH, Money.of(1L));
        WeekendEventPolicy weekendEventPolicy2 = new WeekendEventPolicy(MenuType.APPETIZER, Money.of(1L));
        WeekendEventPolicy weekendEventPolicy3 = new WeekendEventPolicy(MenuType.DESSERT, Money.of(1L));

        // when
        Money discountAmount1 = weekendEventPolicy1.calculateDiscountAmount(reservation1);
        Money discountAmount2 = weekendEventPolicy1.calculateDiscountAmount(reservation2);

        Money discountAmount3 = weekendEventPolicy2.calculateDiscountAmount(reservation1);
        Money discountAmount4 = weekendEventPolicy2.calculateDiscountAmount(reservation2);

        Money discountAmount5 = weekendEventPolicy3.calculateDiscountAmount(reservation1);
        Money discountAmount6 = weekendEventPolicy3.calculateDiscountAmount(reservation2);

        // then
        assertThat(discountAmount1).isEqualTo(Money.of(1L));
        assertThat(discountAmount2).isEqualTo(Money.ZERO);

        assertThat(discountAmount3).isEqualTo(Money.of(2L));
        assertThat(discountAmount4).isEqualTo(Money.ZERO);

        assertThat(discountAmount5).isEqualTo(Money.of(3L));
        assertThat(discountAmount6).isEqualTo(Money.ZERO);
    }

    @Test
    public void 증정품은_존재하지_않는다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 7)); // 평일

        Reservation reservation = new Reservation(order, reservationDate);

        WeekendEventPolicy weekendEventPolicy = new WeekendEventPolicy(MenuType.MAIN_DISH, Money.of(1L));

        // when
        Optional<MenuQuantity> gift = weekendEventPolicy.receiveGift(reservation);

        // then
        assertThat(gift.isEmpty()).isTrue();

    }

}