package christmas.controller.dto.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import christmas.application.domain.Money;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class TotalBenefitAmountResponseTest {

    @Test
    public void Money_로_생성할_수_있다() throws Exception {
        // given
        Money money = Money.of(10_000L);

        // when
        // then
        assertThatCode(() -> {
            TotalBenefitAmountResponse.from(money);
        }).doesNotThrowAnyException();
    }

    @Test
    public void Money_가_0원_일_때_toString_메서드를_호출하면_없음_을_반환한다() throws Exception {
        // given
        Money money = Money.of(0L);
        TotalBenefitAmountResponse totalBenefitAmountResponse = TotalBenefitAmountResponse.from(money);

        // when
        String string = totalBenefitAmountResponse.toString();

        // then
        assertThat(string).isEqualTo("없음");
    }

    @Test
    public void Money_가_0원이_아닐_때_toString_메서드를_호출하면_정해진_형식에_음수를_붙여_반환한다() throws Exception {
        // given
        Money money = Money.of(10_000L);
        TotalBenefitAmountResponse totalBenefitAmountResponse = TotalBenefitAmountResponse.from(money);

        // when
        String string = totalBenefitAmountResponse.toString();

        // then
        assertThat(string).isEqualTo("-10,000원");
    }
}