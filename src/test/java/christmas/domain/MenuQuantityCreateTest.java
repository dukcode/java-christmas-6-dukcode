package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuQuantityCreateTest {

    @Test
    public void MenuQuantityCreate_를_생성할_수_있다() throws Exception {
        // given
        String menuName = "메뉴";
        int orderCount = 1;

        // when
        // then
        assertThatCode(() -> {
            new MenuQuantityCreate(menuName, orderCount);
        }).doesNotThrowAnyException();
    }


}