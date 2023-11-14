package christmas.repository;

import christmas.domain.Menu;
import christmas.domain.MenuType;
import christmas.domain.Money;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class DefaultMenuRepository implements MenuRepository {

    private final List<Menu> menus = new ArrayList<>();

    public DefaultMenuRepository() {
        init();
    }

    private void init() {
        // APPETIZER
        menus.add(new Menu("양송이수프", Money.of(6000L), MenuType.APPETIZER));
        menus.add(new Menu("타파스", Money.of(5500L), MenuType.APPETIZER));
        menus.add(new Menu("시저샐러드", Money.of(8000L), MenuType.APPETIZER));

        // MAIN_DISH
        menus.add(new Menu("티본스테이크", Money.of(55000L), MenuType.MAIN_DISH));
        menus.add(new Menu("바비큐립", Money.of(54000L), MenuType.MAIN_DISH));
        menus.add(new Menu("해산물파스타", Money.of(35000L), MenuType.MAIN_DISH));
        menus.add(new Menu("크리스마스파스타", Money.of(25000L), MenuType.MAIN_DISH));

        // DESSERT
        menus.add(new Menu("초코케이크", Money.of(15000L), MenuType.DESSERT));
        menus.add(new Menu("아이스크림", Money.of(5000L), MenuType.DESSERT));

        // BEVERAGE
        menus.add(new Menu("제로콜라", Money.of(3000L), MenuType.BEVERAGE));
        menus.add(new Menu("레드와인", Money.of(60000L), MenuType.BEVERAGE));
        menus.add(new Menu("샴페인", Money.of(25000L), MenuType.BEVERAGE));
    }

    @Override
    public Optional<Menu> findByName(String name) {
        for (Menu menu : menus) {
            if (menu.isNameEquals(name)) {
                return Optional.of(menu);
            }
        }

        return Optional.empty();
    }

    @Override
    public boolean existByName(String name) {
        for (Menu menu : menus) {
            if (menu.isNameEquals(name)) {
                return true;
            }
        }

        return false;
    }
}
