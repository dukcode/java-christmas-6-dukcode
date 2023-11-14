package christmas.controller.dto;


import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import christmas.controller.dto.request.ReservationDateCreateRequest;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationDateCreateRequestTest {

    @Test
    public void 방문_날짜로_ReservationDateCreateRequest_클래스를_생성할_수_있다() throws Exception {
        // given
        String dayOfMonth = "1";

        // when
        // then
        assertThatCode(() -> {
            new ReservationDateCreateRequest("2023", "12", dayOfMonth);
        }).doesNotThrowAnyException();
    }

    @Test
    public void 숫자_형식이_아닌_방문_날짜로_ReservationDateCreateRequest_클래스를_생성시_예외가_발생한다() throws Exception {
        // given
        String dayOfMonth = "십삼일";

        // when
        // then
        assertThatThrownBy(() -> {
            new ReservationDateCreateRequest("2023", "12", dayOfMonth);
        }).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    public void 존재하지_않는_날짜로_ReservationDateCreateRequest_클래스를_생성시_예외가_발생한다() throws Exception {
        // given
        String dayOfMonth = "100";

        // when
        // then
        assertThatThrownBy(() -> {
            new ReservationDateCreateRequest("2023", "12", dayOfMonth);
        }).isInstanceOf(IllegalArgumentException.class);
    }

}