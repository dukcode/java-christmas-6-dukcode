package christmas.domain;

import christmas.domain.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;

public class MenuOrders {

    private final List<MenuOrder> menuOrders = new ArrayList<>();

    public MenuOrders(List<MenuOrder> menuOrders) {
        validateMenuDuplicated(menuOrders);
        this.menuOrders.addAll(menuOrders);
    }


    private void validateMenuDuplicated(List<MenuOrder> menuOrders) {
        List<Menu> distinctMenu = menuOrders.stream()
                .map(MenuOrder::getMenu)
                .distinct()
                .toList();

        if (menuOrders.size() != distinctMenu.size()) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_ORDER);
        }
    }

    public List<MenuOrder> getMenuOrders() {
        return menuOrders;
    }

    public Money calculateTotalCost() {
        Money cost = Money.ZERO;
        for (MenuOrder menuOrder : menuOrders) {
            cost = cost.add(menuOrder.calculateCost());
        }

        return cost;
    }
}
