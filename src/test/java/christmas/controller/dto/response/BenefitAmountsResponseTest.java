package christmas.controller.dto.response;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import christmas.domain.Money;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class BenefitAmountsResponseTest {

    @Test
    public void Map_으로_BenefitAmountsResponse_를_생성할_수_있다() throws Exception {
        // given
        Map<String, Money> benefitAmounts = new HashMap<>();
        benefitAmounts.put("이벤트 이름", Money.of(10_000L));

        // when
        // then
        assertThatCode(() -> {
            new BenefitAmountsResponse(benefitAmounts);
        }).doesNotThrowAnyException();
    }

    @Test
    public void toString_메서드를_호출하면_지정된_형식을_반환한다() throws Exception {
        // given
        Map<String, Money> benefitAmounts = new HashMap<>();
        benefitAmounts.put("이벤트 이름", Money.of(10_000L));

        BenefitAmountsResponse benefitAmountsResponse = new BenefitAmountsResponse(benefitAmounts);

        // when
        String string = benefitAmountsResponse.toString();

        // then
        assertThat(string).isEqualTo("이벤트 이름: -10,000원");
    }

    @Test
    public void benefitAmount_가_여러개일_결우_toString_메서드를_호출하면_여러_줄의_지정된_형식을_반환한다() throws Exception {
        // given
        Map<String, Money> benefitAmounts = new HashMap<>();
        benefitAmounts.put("이벤트 이름1", Money.of(10_000L));
        benefitAmounts.put("이벤트 이름2", Money.of(20_000L));

        BenefitAmountsResponse benefitAmountsResponse = new BenefitAmountsResponse(benefitAmounts);

        // when
        String string = benefitAmountsResponse.toString();

        // then
        assertThat(string).isEqualTo("""
                이벤트 이름1: -10,000원
                이벤트 이름2: -20,000원""");
    }
}