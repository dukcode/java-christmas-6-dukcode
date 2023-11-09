package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuOrderTest {

    @Test
    public void MenuOrder_를_생성할_수_있다() throws Exception {
        // given
        String menuName = Menu.BBQ_RIB.getName();
        int orderCount = 1;

        // when
        // then
        assertThatCode(() -> {
            new MenuOrder(menuName, orderCount);
        }).doesNotThrowAnyException();

    }

    @Test
    public void MenuOrder_생성_시_없는_메뉴를_주문하면_예외가_발생한다() throws Exception {
        // given
        String menuName = "존재하지 않는 메뉴 이름";

        // when
        // then
        assertThatThrownBy(() -> {
            new MenuOrder(menuName, 1);
        }).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void MenuOrder_생성_시_주문_갯수가_양수가_아니면_예외가_발생한다() throws Exception {
        // given
        String menuName = Menu.BBQ_RIB.getName();
        int orderCount = 0;

        // when
        // then
        assertThatThrownBy(() -> {
            new MenuOrder(menuName, orderCount);
        }).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void 주문_금액을_계산할_수_있다() throws Exception {
        // given

        MenuOrder menuOrder = new MenuOrder(Menu.BBQ_RIB.getName(), 3);

        // 바베큐립(54,000원) - 3개
        // 총 금액 = 54000 * 3 = 162000
        Money totalCost = Money.of(162000L);

        // when
        Money calculatedCost = menuOrder.calculateCost();

        // then
        assertThat(calculatedCost).isEqualTo(totalCost);

    }

}