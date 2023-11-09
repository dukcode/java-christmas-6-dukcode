package christmas.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class MenuTest {

    @Test
    public void 메뉴_이름으로_Menu_를_찾을_수_있다() throws Exception {
        // given
        String name = Menu.BBQ_RIB.getName();

        // when
        Menu menu = Menu.withName(name);

        // then
        assertThat(menu).isEqualTo(Menu.BBQ_RIB);
    }

    @Test
    public void 존재하지_않는_메뉴_이름으로_Menu_클래스를_찾을_시_예외가_발생한다() throws Exception {
        // given
        String name = "존재하지 않는 메뉴 이름";

        // when
        // then
        assertThatThrownBy(() -> {
            Menu.withName(name);
        }).isInstanceOf(MenuNotFoundException.class);

    }

}