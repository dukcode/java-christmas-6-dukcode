package christmas.domain;

import static org.assertj.core.api.Assertions.assertThatCode;

import java.util.List;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class OrderCreateTest {

    @Test
    public void MenuCreateQuantity_로_생성할_수_있다() throws Exception {
        // given
        MenuQuantityCreate menuQuantityCreate1 = new MenuQuantityCreate("메뉴1", 1);
        MenuQuantityCreate menuQuantityCreate2 = new MenuQuantityCreate("메뉴2", 2);
        MenuQuantityCreate menuQuantityCreate3 = new MenuQuantityCreate("메뉴3", 3);

        // when
        // then
        assertThatCode(() -> {
            new OrderCreate(List.of(menuQuantityCreate1, menuQuantityCreate2, menuQuantityCreate3));
        }).doesNotThrowAnyException();
    }


}