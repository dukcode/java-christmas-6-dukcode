package christmas.application.service.event.condition;

import christmas.application.service.event.condition.CompositeEventCondition;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CompositeEventConditionTest {

    @Test
    public void 이벤트_조건_중_하나라도_부합하지_않으면_부합하지_않는다() throws Exception {
        // given
        CompositeEventCondition compositeEventCondition = new CompositeEventCondition((reservation -> true),
                (reservation -> false));

        // when
        boolean isSatisfied = compositeEventCondition.isSatisfiedBy(null);

        // then
        Assertions.assertThat(isSatisfied).isFalse();

    }

    @Test
    public void 이벤트_조건_중_모두가_부합해야_부합한다() throws Exception {
        // given
        CompositeEventCondition compositeEventCondition = new CompositeEventCondition((reservation -> true),
                (reservation -> true));

        // when
        boolean isSatisfied = compositeEventCondition.isSatisfiedBy(null);

        // then
        Assertions.assertThat(isSatisfied).isTrue();
    }

}