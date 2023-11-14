package christmas.application.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import christmas.application.domain.Menu;
import christmas.application.domain.MenuQuantity;
import christmas.application.domain.MenuType;
import christmas.application.domain.Money;
import christmas.application.domain.Order;
import christmas.application.domain.Reservation;
import christmas.application.domain.ReservationDate;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationTest {


    @Test
    public void Order_와_Reservation_으로_생성할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);
        List<MenuQuantity> menuQuantities =
                List.of(new MenuQuantity(menu1, 1),
                        new MenuQuantity(menu2, 2));
        Order order = new Order(menuQuantities);

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 13));

        // when
        // then
        assertThatCode(() -> {
            new Reservation(order, reservationDate);
        }).doesNotThrowAnyException();
    }


    @Test
    public void 예약일이_범위_안에_있는지_판단할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);
        List<MenuQuantity> menuQuantities =
                List.of(new MenuQuantity(menu1, 1),
                        new MenuQuantity(menu2, 2));
        Order order = new Order(menuQuantities);

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 13));

        Reservation reservation = new Reservation(order, reservationDate);

        // when
        boolean isInRange1 = reservation.isInRange(LocalDate.of(2023, 12, 13), (
                LocalDate.of(2023, 12, 14)));
        boolean isInRange2 = reservation.isInRange(LocalDate.of(2023, 12, 12), (
                LocalDate.of(2023, 12, 13)));
        boolean isInRange3 = reservation.isInRange(LocalDate.of(2023, 12, 11), (
                LocalDate.of(2023, 12, 12)));
        boolean isInRange4 = reservation.isInRange(LocalDate.of(2023, 12, 14), (
                LocalDate.of(2023, 12, 15)));

        // then
        assertThat(isInRange1).isEqualTo(true);
        assertThat(isInRange2).isEqualTo(true);
        assertThat(isInRange3).isEqualTo(false);
        assertThat(isInRange4).isEqualTo(false);
    }

    @Test
    public void 예약일이_기준일보다_몇일_뒤인지_계산할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);
        List<MenuQuantity> menuQuantities =
                List.of(new MenuQuantity(menu1, 1),
                        new MenuQuantity(menu2, 2));
        Order order = new Order(menuQuantities);

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 13));

        Reservation reservation = new Reservation(order, reservationDate);

        // when
        long daysDifference1 = reservation.daysAfter(LocalDate.of(2023, 12, 12));
        long daysDifference2 = reservation.daysAfter(LocalDate.of(2023, 12, 13));
        long daysDifference3 = reservation.daysAfter(LocalDate.of(2023, 12, 14));

        // then
        assertThat(daysDifference1).isEqualTo(1L);
        assertThat(daysDifference2).isEqualTo(0L);
        assertThat(daysDifference3).isEqualTo(-1L);
    }

    @Test
    public void 예약일이_주말인지_판단할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);
        List<MenuQuantity> menuQuantities =
                List.of(new MenuQuantity(menu1, 1),
                        new MenuQuantity(menu2, 2));
        Order order = new Order(menuQuantities);

        ReservationDate reservationDate1 = new ReservationDate(LocalDate.of(2023, 12, 8)); // 목요일
        ReservationDate reservationDate2 = new ReservationDate(LocalDate.of(2023, 12, 9)); // 금요일
        ReservationDate reservationDate3 = new ReservationDate(LocalDate.of(2023, 12, 10)); // 일요일

        Reservation reservation1 = new Reservation(order, reservationDate1);
        Reservation reservation2 = new Reservation(order, reservationDate2);
        Reservation reservation3 = new Reservation(order, reservationDate3);

        // when
        // then
        assertThat(reservation1.isWeekend()).isEqualTo(true);
        assertThat(reservation2.isWeekend()).isEqualTo(true);
        assertThat(reservation3.isWeekend()).isEqualTo(false);
    }

    @Test
    public void 예약일이_주어진_날들_중에_존재하는지_판단할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);
        List<MenuQuantity> menuQuantities =
                List.of(new MenuQuantity(menu1, 1),
                        new MenuQuantity(menu2, 2));
        Order order = new Order(menuQuantities);

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 13));

        Reservation reservation = new Reservation(order, reservationDate);

        // when
        // then
        assertThat(reservation.isInDates(Set.of(
                LocalDate.of(2023, 12, 13)
        ))).isEqualTo(true);
        assertThat(reservation.isInDates(Set.of(
                LocalDate.of(2023, 12, 13),
                LocalDate.of(2023, 12, 14)
        ))).isEqualTo(true);

        assertThat(reservation.isInDates(Set.of(
                LocalDate.of(2023, 12, 15)
        ))).isEqualTo(false);
        assertThat(reservation.isInDates(Set.of(
                LocalDate.of(2023, 12, 14),
                LocalDate.of(2023, 12, 15)
        ))).isEqualTo(false);
    }

    @Test
    public void 총_주문_금액을_계산할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);
        List<MenuQuantity> menuQuantities =
                List.of(new MenuQuantity(menu1, 1),
                        new MenuQuantity(menu2, 2));
        Order order = new Order(menuQuantities);

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 13));

        Reservation reservation = new Reservation(order, reservationDate);

        // when
        Money totalCost = reservation.calculateTotalCost();

        // then
        assertThat(totalCost).isEqualTo(Money.of(50_000L));
    }

    @Test
    public void 주문에서_특정_메뉴_타입을_가진_주문_수량이_몇개인지_계산할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);
        Menu menu3 = new Menu("메뉴3", Money.of(20_000L), MenuType.MAIN_DISH);
        List<MenuQuantity> menuQuantities =
                List.of(new MenuQuantity(menu1, 1),
                        new MenuQuantity(menu2, 2),
                        new MenuQuantity(menu3, 10));
        Order order = new Order(menuQuantities);

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 13));

        Reservation reservation = new Reservation(order, reservationDate);

        // when
        int countMainDish = reservation.countMenusByType(MenuType.MAIN_DISH);

        // then
        assertThat(countMainDish).isEqualTo(11);
    }
}