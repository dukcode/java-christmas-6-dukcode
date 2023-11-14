package christmas.controller.dto.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import christmas.application.domain.Menu;
import christmas.application.domain.MenuQuantity;
import christmas.application.domain.MenuType;
import christmas.application.domain.Money;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuQuantityResponseTest {

    @Test
    public void MenuQuantity_로_생성할_수_있다() throws Exception {
        // given
        Menu menu = new Menu("메뉴", Money.of(10_000L), MenuType.MAIN_DISH);
        MenuQuantity menuQuantity = new MenuQuantity(menu, 3);

        // when
        // then
        assertThatCode(() -> {
            MenuQuantityResponse.from(menuQuantity);
        }).doesNotThrowAnyException();
    }

    @Test
    public void toString_메서드_호출_시_정해진_형식을_반환한다() throws Exception {
        Menu menu = new Menu("메뉴", Money.of(10_000L), MenuType.MAIN_DISH);
        MenuQuantity menuQuantity = new MenuQuantity(menu, 3);

        // when
        MenuQuantityResponse menuQuantityResponse = MenuQuantityResponse.from(menuQuantity);
        String string = menuQuantityResponse.toString();

        // then
        assertThat(string).isEqualTo("메뉴 3개");
    }

}