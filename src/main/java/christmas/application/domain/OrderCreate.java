package christmas.application.domain;

import java.util.List;

public class OrderCreate {

    private final List<MenuQuantityCreate> menuQuantityCreates;

    public OrderCreate(List<MenuQuantityCreate> menuQuantityCreates) {
        this.menuQuantityCreates = menuQuantityCreates;
    }

    public List<MenuQuantityCreate> getMenuQuantityCreates() {
        return menuQuantityCreates;
    }
}
