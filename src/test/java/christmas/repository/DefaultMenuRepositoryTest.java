package christmas.repository;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.application.domain.Menu;
import java.util.Optional;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DefaultMenuRepositoryTest {

    @Test
    public void 메뉴_이름으로_메뉴를_찾을_수_있다() throws Exception {
        // given
        DefaultMenuRepository repository = new DefaultMenuRepository();

        // when
        Optional<Menu> menu1 = repository.findByName("없는 메뉴");
        Optional<Menu> menu2 = repository.findByName("제로콜라");

        // then
        assertThat(menu1.isEmpty()).isTrue();
        assertThat(menu2.isEmpty()).isFalse();
    }

    @Test
    public void 메뉴_이름으로_메뉴_존재_여부를_판단할_수_있다() throws Exception {
        // given
        DefaultMenuRepository repository = new DefaultMenuRepository();

        // when
        boolean exist1 = repository.existByName("없는 메뉴");
        boolean exist2 = repository.existByName("제로콜라");

        // then
        assertThat(exist1).isFalse();
        assertThat(exist2).isTrue();
    }

}