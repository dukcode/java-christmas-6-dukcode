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
class DDayEventPolicyTest {

    @Test
    public void 이벤트_시작일과_예약일의_차이에_일당_할인_금액을_곱하고_기본_할인_금액을_더한_금액이_총_할인_금액이다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));

        ReservationDate reservationDate1 = new ReservationDate(LocalDate.of(2023, 12, 1));
        ReservationDate reservationDate2 = new ReservationDate(LocalDate.of(2023, 12, 4));
        ReservationDate reservationDate3 = new ReservationDate(LocalDate.of(2023, 12, 25));

        Reservation reservation1 = new Reservation(order, reservationDate1);
        Reservation reservation2 = new Reservation(order, reservationDate2);
        Reservation reservation3 = new Reservation(order, reservationDate3);

        DDayEventPolicy dDayEventPolicy = new DDayEventPolicy(
                LocalDate.of(2023, 12, 1),
                LocalDate.of(2023, 12, 25),
                Money.of(1_000L),
                Money.of(100L)
        );

        // when
        Money discountAmount1 = dDayEventPolicy.calculateDiscountAmount(reservation1);
        Money discountAmount2 = dDayEventPolicy.calculateDiscountAmount(reservation2);
        Money discountAmount3 = dDayEventPolicy.calculateDiscountAmount(reservation3);

        // then
        assertThat(discountAmount1).isEqualTo(Money.of(1_000L));
        assertThat(discountAmount2).isEqualTo(Money.of(1_300L));
        assertThat(discountAmount3).isEqualTo(Money.of(3_400));

    }

    @Test
    public void 예약일이_이벤트_기간에_존재하지_않으면_0원을_반환한다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));

        ReservationDate reservationDate1 = new ReservationDate(LocalDate.of(2023, 11, 30));
        ReservationDate reservationDate2 = new ReservationDate(LocalDate.of(2023, 12, 1));
        ReservationDate reservationDate3 = new ReservationDate(LocalDate.of(2023, 12, 25));
        ReservationDate reservationDate4 = new ReservationDate(LocalDate.of(2023, 12, 26));

        Reservation reservation1 = new Reservation(order, reservationDate1);
        Reservation reservation2 = new Reservation(order, reservationDate2);
        Reservation reservation3 = new Reservation(order, reservationDate3);
        Reservation reservation4 = new Reservation(order, reservationDate4);

        DDayEventPolicy dDayEventPolicy = new DDayEventPolicy(
                LocalDate.of(2023, 12, 1),
                LocalDate.of(2023, 12, 25),
                Money.of(1_000L),
                Money.of(100L)
        );

        // when
        Money discountAmount1 = dDayEventPolicy.calculateDiscountAmount(reservation1);
        Money discountAmount2 = dDayEventPolicy.calculateDiscountAmount(reservation2);
        Money discountAmount3 = dDayEventPolicy.calculateDiscountAmount(reservation3);
        Money discountAmount4 = dDayEventPolicy.calculateDiscountAmount(reservation4);

        // then
        assertThat(discountAmount1).isEqualTo(Money.ZERO);
        assertThat(discountAmount2).isNotEqualTo(Money.ZERO);
        assertThat(discountAmount3).isNotEqualTo(Money.ZERO);
        assertThat(discountAmount4).isEqualTo(Money.ZERO);
    }


    @Test
    public void 증정품은_존재하지_않는다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 13));

        Reservation reservation = new Reservation(order, reservationDate);

        DDayEventPolicy dDayEventPolicy = new DDayEventPolicy(
                LocalDate.of(2023, 12, 1),
                LocalDate.of(2023, 12, 25),
                Money.of(1_000L),
                Money.of(100L)
        );

        // when
        Optional<MenuQuantity> gift = dDayEventPolicy.receiveGift(reservation);

        // then
        assertThat(gift.isEmpty()).isTrue();
    }

}