package christmas.application.service;

import christmas.application.domain.Menu;
import java.util.Optional;

public interface MenuRepository {

    Optional<Menu> findByName(String name);

    boolean existByName(String name);
}
