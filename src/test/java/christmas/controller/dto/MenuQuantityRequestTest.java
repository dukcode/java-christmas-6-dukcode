package christmas.controller.dto;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

import christmas.controller.dto.request.MenuOrderRequest;
import christmas.controller.dto.request.MenuOrdersRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuQuantityRequestTest {

    @Test
    public void 메뉴와_갯수로_MenuOrderRequest_클래스를_생성을_할_수_있다() throws Exception {
        // given
        String order = "시저샐러드-1";

        // when
        // then
        assertThatCode(() -> {
            new MenuOrdersRequest(order);
        }).doesNotThrowAnyException();

    }

    @Test
    public void 메뉴와_갯수_사이에_공백이_존재해도_MenuOrderRequest_클래스를_생성을_할_수_있다() throws Exception {
        // given
        String order = "시저샐러드 - 1";

        // when
        // then
        assertThatCode(() -> {
            new MenuOrdersRequest(order);
        }).doesNotThrowAnyException();

    }

    @Test
    public void 메뉴_갯수가_숫자_형식이_아니면_MenuOrderRequest_생성_시_예외가_발생한다() throws Exception {
        // given
        String order = "시저샐러드-a";

        // when
        // then
        assertThatThrownBy(() -> {
            new MenuOrderRequest(order);
        }).isInstanceOf(IllegalArgumentException.class);

    }

    @Test
    public void 메뉴_갯수_구분자가_올바른_형식이_아니면_MenuOrderRequest_생성_시_예외가_발생한다() throws Exception {
        // given
        String order = "시저샐러드_a";

        // when
        // then
        assertThatThrownBy(() -> {
            new MenuOrdersRequest(order);
        }).isInstanceOf(IllegalArgumentException.class);

    }
}