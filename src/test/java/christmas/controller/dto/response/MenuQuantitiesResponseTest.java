package christmas.controller.dto.response;

import christmas.domain.Menu;
import christmas.domain.MenuQuantity;
import christmas.domain.MenuType;
import christmas.domain.Money;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuQuantitiesResponseTest {

    @Test
    public void MenuQuantity_로_생성할_수_있다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);
        List<MenuQuantity> menuQuantities =
                List.of(new MenuQuantity(menu1, 1),
                        new MenuQuantity(menu2, 2));

        // when
        // then
        Assertions.assertThatCode(() -> {
            MenuQuantitiesResponse.from(menuQuantities);
        }).doesNotThrowAnyException();
    }

    @Test
    public void toString_메서드_호출_시_여러_라인의_정해진_형식을_반환한다() throws Exception {
        // given
        Menu menu1 = new Menu("메뉴1", Money.of(10_000L), MenuType.MAIN_DISH);
        Menu menu2 = new Menu("메뉴2", Money.of(20_000L), MenuType.APPETIZER);
        List<MenuQuantity> menuQuantities =
                List.of(new MenuQuantity(menu1, 1),
                        new MenuQuantity(menu2, 2));

        // when
        MenuQuantitiesResponse menuQuantitiesResponse = MenuQuantitiesResponse.from(menuQuantities);
        String string = menuQuantitiesResponse.toString();

        // then
        Assertions.assertThat(string).isEqualTo("""
                메뉴1 1개
                메뉴2 2개""");
    }
}