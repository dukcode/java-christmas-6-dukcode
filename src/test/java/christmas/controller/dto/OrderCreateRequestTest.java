package christmas.controller.dto;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.controller.dto.request.OrderCreateRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrderCreateRequestTest {

    @Test
    public void 메뉴_목록으로_OrderCreateRequest_클래스를_생성을_할_수_있다() throws Exception {
        // given
        String orders = "시저샐러드-1,티본스테이크-1";

        // when
        // then
        assertThatCode(() -> {
            new OrderCreateRequest(orders);
        }).doesNotThrowAnyException();

    }

    @Test
    public void 메뉴_목록에_공백이_있어도_OrderCreateRequest_클래스를_생성을_할_수_있다() throws Exception {
        // given
        String orders = "시저샐러드-1,     티본스테이크-1";

        // when
        // then
        assertThatCode(() -> {
            new OrderCreateRequest(orders);
        }).doesNotThrowAnyException();

    }

    @Test
    public void 메뉴_주문_갯수가_숫자_형식이_아니면_OrderCreateRequest_생성_시_예외가_발생한다() throws Exception {
        // given
        String orders = "시저샐러드-a, 티본스테이크-1";

        // when
        // then
        assertThatThrownBy(() -> {
            new OrderCreateRequest(orders);
        }).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void 메뉴_주문_구분자가_올바른_형식이_아니면_OrderCreateRequest_생성_시_예외가_발생한다() throws Exception {
        // given
        String orders = "시저샐러드-a & 티본스테이크-1";

        // when
        // then
        assertThatThrownBy(() -> {
            new OrderCreateRequest(orders);
        }).isInstanceOf(IllegalArgumentException.class);

    }

}