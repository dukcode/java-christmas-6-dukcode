package christmas.application.service;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.application.domain.Badge;
import christmas.application.domain.Menu;
import christmas.application.domain.MenuQuantity;
import christmas.application.domain.MenuType;
import christmas.application.domain.Money;
import christmas.application.domain.Order;
import christmas.application.domain.Reservation;
import christmas.application.domain.ReservationDate;
import christmas.application.service.PromotionService;
import christmas.mock.TestEventPolicy;
import christmas.application.service.BadgeRepository;
import christmas.repository.DefaultBadgeRepository;
import christmas.repository.DefaultMenuRepository;
import christmas.application.service.MenuRepository;
import christmas.application.service.Event;
import christmas.application.service.EventPolicy;
import christmas.application.service.event.policy.GiftEventPolicy;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class PromotionServiceTest {

    private BadgeRepository badgeRepository;
    private MenuRepository menuRepository;

    private EventPolicy eventPolicy;

    @BeforeEach
    public void init() {
        badgeRepository = new DefaultBadgeRepository();
        menuRepository = new DefaultMenuRepository();
        eventPolicy = new TestEventPolicy(Money.of(10_000L),
                new Menu("메뉴", Money.of(1_000L), MenuType.MAIN_DISH),
                2);
    }

    @Test
    public void 할인_전_금액을_계산할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 7)); // 평일

        Reservation reservation = new Reservation(order, reservationDate);

        PromotionService promotionService = new PromotionService(badgeRepository);

        // when
        Money preDiscountAmount = promotionService.calculatePreDiscountCharge(reservation);

        // then
        assertThat(preDiscountAmount).isEqualTo(Money.of(50_000L));
    }

    @Test
    public void 증정품을_받을_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 7)); // 평일

        Reservation reservation = new Reservation(order, reservationDate);

        Event event1 = new Event("이벤트1", (r -> true),
                new GiftEventPolicy(menuRepository, Money.ZERO, "샴페인", 1));
        Event event2 = new Event("이벤트2", (r -> true),
                new GiftEventPolicy(menuRepository, Money.ZERO, "샴페인", 2));

        PromotionService promotionService = new PromotionService(badgeRepository, event1, event2);

        // when
        Map<Menu, Integer> gifts = promotionService.receiveGifts(reservation);

        // then
        assertThat(gifts.size()).isEqualTo(1);
        assertThat(gifts.containsKey(new Menu("샴페인", Money.of(25_000L), MenuType.BEVERAGE))).isTrue();
        assertThat(gifts.get(new Menu("샴페인", Money.of(25_000L), MenuType.BEVERAGE))).isEqualTo(3);
    }

    @Test
    public void 배지를_받을_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 7)); // 평일

        Reservation reservation = new Reservation(order, reservationDate);

        PromotionService promotionService1 = new PromotionService(badgeRepository);
        PromotionService promotionService2 = new PromotionService(badgeRepository,
                new Event("이벤트", r -> true, eventPolicy));
        PromotionService promotionService3 = new PromotionService(badgeRepository,
                new Event("이벤트", r -> true, eventPolicy),
                new Event("이벤트", r -> true, eventPolicy));

        // when
        Optional<Badge> badgeOptional1 = promotionService1.receiveBadge(reservation);
        Optional<Badge> badgeOptional2 = promotionService2.receiveBadge(reservation);
        Optional<Badge> badgeOptional3 = promotionService3.receiveBadge(reservation);

        // then
        assertThat(badgeOptional1.isEmpty()).isTrue();

        assertThat(badgeOptional2.isEmpty()).isFalse();
        assertThat(badgeOptional2.get().getName()).isEqualTo("트리");

        assertThat(badgeOptional3.isEmpty()).isFalse();
        assertThat(badgeOptional3.get().getName()).isEqualTo("산타");
    }

    @Test
    public void 총_혜택_금액을_계산할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 7)); // 평일

        Reservation reservation = new Reservation(order, reservationDate);

        PromotionService promotionService = new PromotionService(badgeRepository,
                new Event("이벤트1", r -> true, eventPolicy),
                new Event("이벤트2", r -> true, eventPolicy));

        // when
        Money totalBenefitAmount = promotionService.calculateTotalBenefitAmount(reservation);

        // then
        assertThat(totalBenefitAmount).isEqualTo(Money.of(24_000L));
    }

    @Test
    public void 각_이벤트의_혜택_금액을_계산할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 7)); // 평일

        Reservation reservation = new Reservation(order, reservationDate);

        PromotionService promotionService = new PromotionService(badgeRepository,
                new Event("이벤트1", r -> true, eventPolicy),
                new Event("이벤트2", r -> true, eventPolicy));

        // when
        Map<String, Money> benefitAmounts = promotionService.calculateBenefitAmounts(reservation);

        // then
        assertThat(benefitAmounts.size()).isEqualTo(2);

        assertThat(benefitAmounts.containsKey("이벤트1")).isTrue();
        assertThat(benefitAmounts.get("이벤트1")).isEqualTo(Money.of(12_000L));

        assertThat(benefitAmounts.containsKey("이벤트2")).isTrue();
        assertThat(benefitAmounts.get("이벤트2")).isEqualTo(Money.of(12_000L));
    }

    @Test
    public void 총_할인_금액을_계산할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 7)); // 평일

        Reservation reservation = new Reservation(order, reservationDate);

        PromotionService promotionService = new PromotionService(badgeRepository,
                new Event("이벤트1", r -> true, eventPolicy),
                new Event("이벤트2", r -> true, eventPolicy));

        // when
        Money totalDiscountAmount
                = promotionService.calculateTotalDiscountAmount(reservation);

        // then
        assertThat(totalDiscountAmount).isEqualTo(Money.of(20_000L));
    }

    @Test
    public void 할인_후_금액을_계산할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);

        Order order = new Order(List.of(
                new MenuQuantity(menu1, 1),
                new MenuQuantity(menu2, 2)));

        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 7)); // 평일

        Reservation reservation = new Reservation(order, reservationDate);

        PromotionService promotionService = new PromotionService(badgeRepository,
                new Event("이벤트1", r -> true, eventPolicy),
                new Event("이벤트2", r -> true, eventPolicy));

        // when
        Money chargeAfterDiscount
                = promotionService.calculateChargeAfterDiscount(reservation);

        // then
        assertThat(chargeAfterDiscount).isEqualTo(Money.of(30_000L));
    }
}