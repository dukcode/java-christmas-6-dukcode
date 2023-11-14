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
import java.util.Set;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class SpecialDayEventPolicyTest {

    @Test
    public void 특별_할인_일자에_예약일_포함_여부에_따라_할인_금액을_반환한다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 13));

        Reservation reservation = new Reservation(order, reservationDate);

        SpecialDayEventPolicy eventPolicy1 = new SpecialDayEventPolicy(Money.of(1_000L),
                Set.of(
                        LocalDate.of(2023, 12, 11),
                        LocalDate.of(2023, 12, 12)
                ));
        SpecialDayEventPolicy eventPolicy2 = new SpecialDayEventPolicy(Money.of(1_000L),
                Set.of(
                        LocalDate.of(2023, 12, 12),
                        LocalDate.of(2023, 12, 13)
                ));
        SpecialDayEventPolicy eventPolicy3 = new SpecialDayEventPolicy(Money.of(1_000L),
                Set.of(
                        LocalDate.of(2023, 12, 13),
                        LocalDate.of(2023, 12, 14)
                ));

        // when
        Money discountAmount1 = eventPolicy1.calculateDiscountAmount(reservation);
        Money discountAmount2 = eventPolicy2.calculateDiscountAmount(reservation);
        Money discountAmount3 = eventPolicy3.calculateDiscountAmount(reservation);

        // then
        assertThat(discountAmount1).isEqualTo(Money.ZERO);
        assertThat(discountAmount2).isEqualTo(Money.of(1_000L));
        assertThat(discountAmount3).isEqualTo(Money.of(1_000L));
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

        SpecialDayEventPolicy eventPolicy1 = new SpecialDayEventPolicy(Money.of(1_000L),
                Set.of(
                        LocalDate.of(2023, 12, 11),
                        LocalDate.of(2023, 12, 12)
                ));
        SpecialDayEventPolicy eventPolicy2 = new SpecialDayEventPolicy(Money.of(1_000L),
                Set.of(
                        LocalDate.of(2023, 12, 12),
                        LocalDate.of(2023, 12, 13)
                ));

        // when
        Optional<MenuQuantity> gift1 = eventPolicy1.receiveGift(reservation);
        Optional<MenuQuantity> gift2 = eventPolicy2.receiveGift(reservation);

        // then
        assertThat(gift1.isEmpty()).isTrue();
        assertThat(gift2.isEmpty()).isTrue();
    }

}