package christmas.controller.dto.validator;

import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class NumberFormatValidatorTest {

    @Test
    public void 숫자가_숫자_형식이면_예외를_발생시키지_않는다() throws Exception {
        // given
        String number = "100";

        // when
        // then
        assertThatCode(() -> {
            NumberFormatValidator.validate(number, "예외 메세지");
        }).doesNotThrowAnyException();
    }

    @Test
    public void 숫자가_숫자_형식이_아니면_예외를_발생시킨다() throws Exception {
        // given
        String number = "asd";

        // when
        // then
        assertThatThrownBy(() -> {
            NumberFormatValidator.validate(number, "예외 메세지");
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 예외_발생_시_파라미터로_넘긴_예외_메시지를_담아_던진다() throws Exception {
        // given
        String number = "asd";

        // when
        // then
        assertThatThrownBy(() -> {
            NumberFormatValidator.validate(number, "예외 메세지");
        }).isInstanceOf(IllegalArgumentException.class)
                .hasMessage("예외 메세지");
    }

}