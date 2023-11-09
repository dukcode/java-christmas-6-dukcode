package christmas.controller.dto;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuOrderRequestTest {

    @Test
    public void 메뉴_목록으로_MenuOrderRequest_클래스를_생성을_할_수_있다() throws Exception {
        // given
        String orders = "시저샐러드-1, 티본스테이크-1";

        // when
        // then
        assertThatCode(() -> {
            new MenuOrderRequest(orders);
        }).doesNotThrowAnyException();

    }

    @Test
    public void 메뉴_주문_갯수가_숫자_형식이_아니면_MenuOrderRequest_생성_시_예외가_발생한다() throws Exception {
        // given
        String orders = "시저샐러드-a, 티본스테이크-1";

        // when
        // then
        assertThatThrownBy(() -> {
            new MenuOrderRequest(orders);
        }).isInstanceOf(IllegalArgumentException.class);

    }


}