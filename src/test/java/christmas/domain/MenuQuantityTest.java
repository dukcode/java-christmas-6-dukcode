package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuQuantityTest {

    @Test
    public void MenuQuantity_를_생성할_수_있다() throws Exception {
        // given
        String menuName = Menu.BBQ_RIB.getName();
        int orderCount = 1;

        // when
        // then
        assertThatCode(() -> {
            new MenuQuantity(menuName, orderCount);
        }).doesNotThrowAnyException();

    }

    @Test
    public void MenuQuantity_생성_시_없는_메뉴로_생성_하면_예외가_발생한다() throws Exception {
        // given
        String menuName = "존재하지 않는 메뉴 이름";

        // when
        // then
        assertThatThrownBy(() -> {
            new MenuQuantity(menuName, 1);
        }).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void MenuQuantity_생성_시_갯수가_양수가_아니면_예외가_발생한다() throws Exception {
        // given
        String menuName = Menu.BBQ_RIB.getName();
        int orderCount = 0;

        // when
        // then
        assertThatThrownBy(() -> {
            new MenuQuantity(menuName, orderCount);
        }).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void 총_금액을_계산할_수_있다() throws Exception {
        // given

        MenuQuantity menuQuantity = new MenuQuantity(Menu.BBQ_RIB.getName(), 3);

        // 바베큐립(54,000원) - 3개
        // 총 금액 = 54000 * 3 = 162000
        Money totalCost = Money.of(162000L);

        // when
        Money calculatedCost = menuQuantity.calculateCost();

        // then
        assertThat(calculatedCost).isEqualTo(totalCost);

    }

}