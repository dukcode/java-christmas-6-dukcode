package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrderTest {

    @Test
    public void MenuOrders_를_생성할_수_있다() throws Exception {
        // given
        List<MenuQuantity> menuQuantities = List.of(new MenuQuantity(Menu.BBQ_RIB.getName(), 1),
                new MenuQuantity(Menu.CAESAR_SALAD.getName(), 2),
                new MenuQuantity(Menu.ZERO_COKE.getName(), 3));

        // when
        // then
        assertThatCode(() -> {
            new Order(menuQuantities);
        }).doesNotThrowAnyException();
    }

    @Test
    public void 중복된_메뉴로_MenuOrders_를_생성하면_예외가_발생한다() throws Exception {
        // given
        List<MenuQuantity> menuQuantities = List.of(new MenuQuantity(Menu.BBQ_RIB.getName(), 1),
                new MenuQuantity(Menu.CAESAR_SALAD.getName(), 2),
                new MenuQuantity(Menu.CAESAR_SALAD.getName(), 2));

        // when
        // then
        assertThatThrownBy(() -> {
            new Order(menuQuantities);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 최대_주문_가능_숫자_20개_이상의_메뉴로_MenuOrders_를_생성하면_예외가_발생한다() throws Exception {
        // given
        List<MenuQuantity> menuQuantities = List.of(new MenuQuantity(Menu.CAESAR_SALAD.getName(), 21));

        // when
        // then
        assertThatThrownBy(() -> {
            new Order(menuQuantities);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 음료만으로_구성된_메뉴로_MenuOrders_를_생성하면_예외가_발생한다() throws Exception {
        // given
        List<MenuQuantity> menuQuantities = List.of(new MenuQuantity(Menu.ZERO_COKE.getName(), 1),
                new MenuQuantity(Menu.CHAMPAGNE, 2),
                new MenuQuantity(Menu.RED_WINE, 3));

        // when
        // then
        assertThatThrownBy(() -> {
            new Order(menuQuantities);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 주문_총_금액을_계산할_수_있다() throws Exception {
        // given

        Order order = new Order(List.of(new MenuQuantity(Menu.BBQ_RIB.getName(), 1),
                new MenuQuantity(Menu.CAESAR_SALAD.getName(), 2),
                new MenuQuantity(Menu.ZERO_COKE.getName(), 3)));

        // 바베큐립(54,000원) - 1개
        // 시저샐러드(8,000원) - 2개
        // 제로콜라(3,000) - 3개
        // 총 금액 = 54000 + 16000 + 9000 = 79000
        Money totalAmount = Money.of(79000L);

        // when
        Money calculatedTotalAmount = order.calculateTotalCost();

        // then
        Assertions.assertThat(calculatedTotalAmount).isEqualTo(totalAmount);

    }


}