package christmas.application.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.application.domain.Menu;
import christmas.application.domain.MenuQuantity;
import christmas.application.domain.MenuType;
import christmas.application.domain.Money;
import christmas.application.domain.Order;
import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrderTest {

    @Test
    public void Order_를_생성할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);
        List<MenuQuantity> menuQuantities =
                List.of(new MenuQuantity(menu1, 1),
                        new MenuQuantity(menu2, 2));

        // when
        // then
        assertThatCode(() -> {
            new Order(menuQuantities);
        }).doesNotThrowAnyException();
    }

    @Test
    public void 중복된_메뉴로_Order_를_생성하면_예외가_발생한다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴", Money.of(20_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴", Money.of(20_000L), MenuType.MAIN_DISH);
        List<MenuQuantity> menuQuantities =
                List.of(new MenuQuantity(menu1, 1),
                        new MenuQuantity(menu2, 2));

        // when
        // then
        assertThatThrownBy(() -> {
            new Order(menuQuantities);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 최대_주문_가능_숫자_20개_초과의_메뉴로_Order_를_생성하면_예외가_발생한다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴", Money.of(20_000L), MenuType.MAIN_DISH);
        List<MenuQuantity> menuQuantities =
                List.of(new MenuQuantity(menu1, 21));

        // when
        // then
        assertThatThrownBy(() -> {
            new Order(menuQuantities);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 음료만으로_구성된_메뉴로_Order_를_생성하면_예외가_발생한다() throws Exception {
        Menu menu1 = new Menu("메뉴", Money.of(20_000L), MenuType.BEVERAGE);
        List<MenuQuantity> menuQuantities =
                List.of(new MenuQuantity(menu1, 10));

        // when
        // then
        assertThatThrownBy(() -> {
            new Order(menuQuantities);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 주문_총_금액을_계산할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);
        List<MenuQuantity> menuQuantities =
                List.of(new MenuQuantity(menu1, 1),
                        new MenuQuantity(menu2, 2));

        Order order = new Order(menuQuantities);

        // when
        Money totalCost = order.calculateTotalCost();

        // then
        assertThat(totalCost).isEqualTo(Money.of(50_000L));
    }

    @Test
    public void 주문_중_특정_메뉴_타입의_주문_수량을_계산할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);
        Menu menu3 = new Menu("메뉴2", Money.of(30_000L), MenuType.APPETIZER);
        List<MenuQuantity> menuQuantities =
                List.of(new MenuQuantity(menu1, 1),
                        new MenuQuantity(menu2, 2),
                        new MenuQuantity(menu3, 3));

        Order order = new Order(menuQuantities);

        // when
        int countAppetizer = order.countMenusByType(MenuType.APPETIZER);

        // then
        assertThat(countAppetizer).isEqualTo(5);
    }

}