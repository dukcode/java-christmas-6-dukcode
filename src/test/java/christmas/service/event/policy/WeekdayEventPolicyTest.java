package christmas.service.event.policy;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Menu;
import christmas.domain.MenuQuantity;
import christmas.domain.MenuType;
import christmas.domain.Money;
import christmas.domain.Order;
import christmas.domain.Reservation;
import christmas.domain.ReservationDate;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class WeekdayEventPolicyTest {

    @Test
    public void 예약일이_평일이면_정해진_메뉴타입_당_할인이_적용된다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);
        Menu menu3 = new Menu("메뉴3", Money.of(30_000L), MenuType.DESSERT);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2),
                new MenuQuantity(menu3, 3)));

        ReservationDate reservationDate1 = new ReservationDate(LocalDate.of(2023, 12, 7)); // 평일
        ReservationDate reservationDate2 = new ReservationDate(LocalDate.of(2023, 12, 8)); // 주말

        Reservation reservation1 = new Reservation(order, reservationDate1);
        Reservation reservation2 = new Reservation(order, reservationDate2);

        WeekdayEventPolicy weekdayEventPolicy1 = new WeekdayEventPolicy(MenuType.MAIN_DISH, Money.of(1L));
        WeekdayEventPolicy weekdayEventPolicy2 = new WeekdayEventPolicy(MenuType.APPETIZER, Money.of(1L));
        WeekdayEventPolicy weekdayEventPolicy3 = new WeekdayEventPolicy(MenuType.DESSERT, Money.of(1L));

        // when
        Money discountAmount1 = weekdayEventPolicy1.calculateDiscountAmount(reservation1);
        Money discountAmount2 = weekdayEventPolicy1.calculateDiscountAmount(reservation2);

        Money discountAmount3 = weekdayEventPolicy2.calculateDiscountAmount(reservation1);
        Money discountAmount4 = weekdayEventPolicy2.calculateDiscountAmount(reservation2);

        Money discountAmount5 = weekdayEventPolicy3.calculateDiscountAmount(reservation1);
        Money discountAmount6 = weekdayEventPolicy3.calculateDiscountAmount(reservation2);

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

        WeekdayEventPolicy weekdayEventPolicy = new WeekdayEventPolicy(MenuType.MAIN_DISH, Money.of(1L));

        // when
        Optional<MenuQuantity> gift = weekdayEventPolicy.receiveGift(reservation);

        // then
        assertThat(gift.isEmpty()).isTrue();

    }

}