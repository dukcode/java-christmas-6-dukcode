package christmas.repository;

import christmas.domain.Menu;
import java.util.Optional;

public interface MenuRepository {

    Optional<Menu> findByName(String name);

    boolean existByName(String name);
}
