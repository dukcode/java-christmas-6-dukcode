package christmas.controller.dto.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import christmas.domain.Menu;
import christmas.domain.MenuQuantity;
import christmas.domain.MenuType;
import christmas.domain.Money;
import christmas.domain.Order;
import christmas.domain.Reservation;
import christmas.domain.ReservationDate;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationDateResponseTest {

    @Test
    public void Reservation_으로_생성할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));
        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 13));
        Reservation reservation = new Reservation(order, reservationDate);

        // when
        // then
        assertThatCode(() -> {
            ReservationDateResponse.from(reservation);
        }).doesNotThrowAnyException();
    }

    @Test
    public void getDayOfMonth_메서드를_호출하면_예약일을_반환한다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));
        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 13));
        Reservation reservation = new Reservation(order, reservationDate);

        // when
        ReservationDateResponse reservationDateResponse = ReservationDateResponse.from(reservation);
        int dayOfMonth = reservationDateResponse.getDayOfMonth();

        // then
        assertThat(dayOfMonth).isEqualTo(13);
    }
}