package christmas.application.service.event.policy;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.application.domain.Menu;
import christmas.application.domain.MenuQuantity;
import christmas.application.domain.MenuType;
import christmas.application.domain.Money;
import christmas.application.domain.Order;
import christmas.application.domain.Reservation;
import christmas.application.domain.ReservationDate;
import christmas.application.exception.NotFoundException;
import christmas.application.service.event.policy.GiftEventPolicy;
import christmas.repository.DefaultMenuRepository;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class GiftEventPolicyTest {

    @Test
    public void 할인_금액_계산을_요청하면_0원을_반환한다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 13));

        Reservation reservation = new Reservation(order, reservationDate);

        GiftEventPolicy giftEventPolicy = new GiftEventPolicy(new DefaultMenuRepository(),
                Money.of(120_000L),
                "샴페인", 1);

        // when
        Money discountAmount = giftEventPolicy.calculateDiscountAmount(reservation);

        // then
        assertThat(discountAmount).isEqualTo(Money.ZERO);
    }

    @Test
    public void 증정품을_요청하면_최소_주문_금액을_넘길_시_증정품을_반환한다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(119_998L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(1L), MenuType.APPETIZER);

        Order order1 = new Order(List.of(               // 119,999원
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 1)));
        Order order2 = new Order(List.of(               // 120,000원
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));
        Order order3 = new Order(List.of(               // 120,001원
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 3)));

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 13));

        Reservation reservation1 = new Reservation(order1, reservationDate);
        Reservation reservation2 = new Reservation(order2, reservationDate);
        Reservation reservation3 = new Reservation(order3, reservationDate);

        GiftEventPolicy giftEventPolicy = new GiftEventPolicy(new DefaultMenuRepository(),
                Money.of(120_000L),
                "샴페인", 2);

        // when
        Optional<MenuQuantity> gift1 = giftEventPolicy.receiveGift(reservation1);
        Optional<MenuQuantity> gift2 = giftEventPolicy.receiveGift(reservation2);
        Optional<MenuQuantity> gift3 = giftEventPolicy.receiveGift(reservation3);

        // then
        assertThat(gift1.isEmpty()).isTrue();

        assertThat(gift2.isEmpty()).isFalse();
        assertThat(gift2.get().getMenu().getName()).isEqualTo("샴페인");
        assertThat(gift2.get().getQuantity()).isEqualTo(2);

        assertThat(gift3.isEmpty()).isFalse();
        assertThat(gift3.get().getMenu().getName()).isEqualTo("샴페인");
        assertThat(gift3.get().getQuantity()).isEqualTo(2);
    }

    @Test
    public void MenuRepository_에_없는_메뉴로_생성하면_예외를_발생시킨다() throws Exception {
        // given
        // when
        // then
        assertThatThrownBy(() -> {
            new GiftEventPolicy(new DefaultMenuRepository(),
                    Money.of(120_000L),
                    "없는 메뉴", 2);
        }).isInstanceOf(NotFoundException.class);
    }
}