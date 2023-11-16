package christmas.application.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.application.domain.Menu;
import christmas.application.domain.MenuQuantity;
import christmas.application.domain.MenuType;
import christmas.application.domain.Money;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuQuantityTest {

    @Test
    public void MenuQuantity_를_생성할_수_있다() throws Exception {
        // given
        Menu menu = new Menu("메뉴", Money.of(10_000L), MenuType.MAIN_DISH);
        int orderCount = 1;

        // when
        // then
        assertThatCode(() -> {
            new MenuQuantity(menu, orderCount);
        }).doesNotThrowAnyException();

    }

    @Test
    public void MenuQuantity_생성_시_갯수가_양수가_아니면_예외가_발생한다() throws Exception {
        // given
        Menu menu = new Menu("메뉴", Money.of(10_000L), MenuType.MAIN_DISH);
        int orderCount = 0;

        // when
        // then
        assertThatThrownBy(() -> {
            new MenuQuantity(menu, orderCount);
        }).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void 총_금액을_계산할_수_있다() throws Exception {
        // given
        Menu menu = new Menu("메뉴", Money.of(10_000L), MenuType.MAIN_DISH);
        int orderCount = 0;
        MenuQuantity menuQuantity = new MenuQuantity(menu, 3);

        Money totalCost = Money.of(30_000L);

        // when
        Money calculatedCost = menuQuantity.calculateCost();

        // then
        assertThat(calculatedCost).isEqualTo(totalCost);
    }

    @Test
    public void isSameMenuType_호출_시_Menu_타입이_같으면_true_를_반환한다() throws Exception {
        // given
        MenuType menuType = MenuType.MAIN_DISH;
        Menu menu = new Menu("aaaaa", Money.of(10_000L), MenuType.MAIN_DISH);
        MenuQuantity menuQuantity = new MenuQuantity(menu, 3);
        // when
        boolean isSameMenuType = menuQuantity.isSameMenuType(menuType);

        // then
        assertThat(isSameMenuType).isEqualTo(true);
    }


    @Test
    public void isSameMenuType_호출_시_Menu_타입이_다르면_false_를_반환한다() throws Exception {
        // given
        MenuType menuType = MenuType.BEVERAGE;
        Menu menu = new Menu("aaaaa", Money.of(10_000L), MenuType.MAIN_DISH);
        MenuQuantity menuQuantity = new MenuQuantity(menu, 3);

        // when
        boolean isSameMenuType = menuQuantity.isSameMenuType(menuType);

        // then
        assertThat(isSameMenuType).isEqualTo(false);
    }
}