package christmas.controller.dto.response;

import christmas.domain.Money;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ChargeResponseTest {

    @Test
    public void Money_로_ChargeResponse_를_생성할_수_있다() throws Exception {
        // given
        Money charge = Money.of(10_000L);

        // when
        // then
        Assertions.assertThatCode(() -> {
            new ChargeResponse(charge);
        }).doesNotThrowAnyException();
    }

    @Test
    public void Money_가_0원_일_때_toString_메서드를_호출하면_없음_을_반환한다() throws Exception {
        // given
        Money charge = Money.of(0L);
        ChargeResponse chargeResponse = new ChargeResponse(charge);

        // when
        String string = chargeResponse.toString();

        // then
        Assertions.assertThat(string).isEqualTo("없음");
    }

    @Test
    public void Money_가_0원이_아닐_때_toString_메서드를_호출하면_지정된_형식을_반환한다() throws Exception {
        // given
        Money charge = Money.of(10_000L);
        ChargeResponse chargeResponse = new ChargeResponse(charge);

        // when
        String string = chargeResponse.toString();

        // then
        Assertions.assertThat(string).isEqualTo("10,000원");
    }
}