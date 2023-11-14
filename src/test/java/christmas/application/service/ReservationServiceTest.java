package christmas.application.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.application.domain.Menu;
import christmas.application.domain.MenuQuantity;
import christmas.application.domain.MenuQuantityCreate;
import christmas.application.domain.MenuType;
import christmas.application.domain.Money;
import christmas.application.domain.Order;
import christmas.application.domain.OrderCreate;
import christmas.application.domain.Reservation;
import christmas.application.domain.ReservationDate;
import christmas.application.service.ReservationService;
import christmas.repository.DefaultMenuRepository;
import christmas.application.service.MenuRepository;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationServiceTest {

    private MenuRepository menuRepository;

    @BeforeEach
    public void init() {
        menuRepository = new DefaultMenuRepository();
    }

    @Test
    public void 주문을_생성할_수_있다() throws Exception {
        // given
        List<MenuQuantityCreate> menuQuantityCreates = List.of(
                new MenuQuantityCreate("해산물파스타", 20));
        OrderCreate orderCreate = new OrderCreate(menuQuantityCreates);

        ReservationService reservationService = new ReservationService(menuRepository);

        // when
        // then
        assertThatCode(() -> {
            reservationService.createOrder(orderCreate);
        }).doesNotThrowAnyException();
    }

    @Test
    public void 존재하지_않는_메뉴로_주문_생성_시_예외가_발생한다() throws Exception {
        // given
        List<MenuQuantityCreate> menuQuantityCreates = List.of(
                new MenuQuantityCreate("없는 메뉴", 1));
        OrderCreate orderCreate = new OrderCreate(menuQuantityCreates);

        ReservationService reservationService = new ReservationService(menuRepository);

        // when
        // then
        assertThatThrownBy(() -> {
            reservationService.createOrder(orderCreate);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 주문_생성_시_메뉴의_갯수가_20개를_초과하면_예외가_발생한다() throws Exception {
        // given
        List<MenuQuantityCreate> menuQuantityCreates = List.of(
                new MenuQuantityCreate("초코케이크", 21));
        OrderCreate orderCreate = new OrderCreate(menuQuantityCreates);

        ReservationService reservationService = new ReservationService(menuRepository);

        // when
        // then
        assertThatThrownBy(() -> {
            reservationService.createOrder(orderCreate);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 주문_생성_시_메뉴가_음료로만_구성되면_예외가_발생한다() throws Exception {
        // given
        List<MenuQuantityCreate> menuQuantityCreates = List.of(
                new MenuQuantityCreate("레드와인", 2));
        OrderCreate orderCreate = new OrderCreate(menuQuantityCreates);

        ReservationService reservationService = new ReservationService(menuRepository);

        // when
        // then
        assertThatThrownBy(() -> {
            reservationService.createOrder(orderCreate);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void Order_와_ReservationDate_로_예약을_생성할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));
        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 13));

        ReservationService reservationService = new ReservationService(menuRepository);

        // when
        Reservation reservation = reservationService.createReservation(order, reservationDate);

        // then
        assertThat(reservation).isNotNull();
    }
}