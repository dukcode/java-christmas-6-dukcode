package christmas.application.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import christmas.application.domain.Menu;
import christmas.application.domain.MenuType;
import christmas.application.domain.Money;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuTest {

    @Test
    public void Menu_를_생성할_수_있다() throws Exception {
        // given
        int orderCount = 1;

        // when
        // then
        assertThatCode(() -> {
            Menu menu = new Menu("메뉴", Money.of(10_000L), MenuType.MAIN_DISH);
        }).doesNotThrowAnyException();

    }

    @Test
    public void isSameName_호출_시_Menu_이름이_같으면_true_를_반환한다() throws Exception {
        // given
        String name = "메뉴";
        Menu menu = new Menu("메뉴", Money.of(10_000L), MenuType.MAIN_DISH);

        // when
        boolean isSameName = menu.isSameName(name);

        // then
        assertThat(isSameName).isEqualTo(true);
    }

    @Test
    public void isSameName_호출_시_Menu_이름이_다르면_false_를_반환한다() throws Exception {
        // given
        String name = "xxxxx";
        Menu menu = new Menu("aaaaa", Money.of(10_000L), MenuType.MAIN_DISH);

        // when
        boolean isSameName = menu.isSameName(name);

        // then
        assertThat(isSameName).isEqualTo(false);
    }

    @Test
    public void isSameMenuType_호출_시_Menu_타입이_같으면_true_를_반환한다() throws Exception {
        // given
        MenuType menuType = MenuType.MAIN_DISH;
        Menu menu = new Menu("aaaaa", Money.of(10_000L), MenuType.MAIN_DISH);

        // when
        boolean isSameMenuType = menu.isSameMenuType(menuType);

        // then
        assertThat(isSameMenuType).isEqualTo(true);
    }


    @Test
    public void isSameMenuType_호출_시_Menu_타입이_다르면_false_를_반환한다() throws Exception {
        // given
        MenuType menuType = MenuType.BEVERAGE;
        Menu menu = new Menu("aaaaa", Money.of(10_000L), MenuType.MAIN_DISH);

        // when
        boolean isSameMenuType = menu.isSameMenuType(menuType);

        // then
        assertThat(isSameMenuType).isEqualTo(false);
    }

}