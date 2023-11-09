package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuOrdersTest {

    @Test
    public void MenuOrders_를_생성할_수_있다() throws Exception {
        // given
        List<MenuOrder> menuOrders = List.of(new MenuOrder(Menu.BBQ_RIB.getName(), 1),
                new MenuOrder(Menu.CAESAR_SALAD.getName(), 2),
                new MenuOrder(Menu.ZERO_COKE.getName(), 3));

        // when
        // then
        assertThatCode(() -> {
            new MenuOrders(menuOrders);
        }).doesNotThrowAnyException();
    }

    @Test
    public void 중복된_메뉴로_MenuOrders_를_생성하면_예외가_발생한다() throws Exception {
        // given
        List<MenuOrder> menuOrders = List.of(new MenuOrder(Menu.BBQ_RIB.getName(), 1),
                new MenuOrder(Menu.CAESAR_SALAD.getName(), 2),
                new MenuOrder(Menu.CAESAR_SALAD.getName(), 2));

        // when
        // then
        assertThatThrownBy(() -> {
            new MenuOrders(menuOrders);
        }).isInstanceOf(IllegalArgumentException.class);
    }


}