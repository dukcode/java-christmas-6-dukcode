package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuOrdersTest {

    @Test
    public void MenuOrders_를_생성할_수_있다() throws Exception {
        // given
        List<MenuQuantity> menuQuantities = List.of(new MenuQuantity(Menu.BBQ_RIB.getName(), 1),
                new MenuQuantity(Menu.CAESAR_SALAD.getName(), 2),
                new MenuQuantity(Menu.ZERO_COKE.getName(), 3));

        // when
        // then
        assertThatCode(() -> {
            new MenuOrders(menuQuantities);
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
            new MenuOrders(menuQuantities);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 주문_총_금액을_계산할_수_있다() throws Exception {
        // given

        MenuOrders menuOrders = new MenuOrders(List.of(new MenuQuantity(Menu.BBQ_RIB.getName(), 1),
                new MenuQuantity(Menu.CAESAR_SALAD.getName(), 2),
                new MenuQuantity(Menu.ZERO_COKE.getName(), 3)));

        // 바베큐립(54,000원) - 1개
        // 시저샐러드(8,000원) - 2개
        // 제로콜라(3,000) - 3개
        // 총 금액 = 54000 + 16000 + 9000 = 79000
        Money totalAmount = Money.of(79000L);

        // when
        Money calculatedTotalAmount = menuOrders.calculateTotalCost();

        // then
        Assertions.assertThat(calculatedTotalAmount).isEqualTo(totalAmount);

    }


}