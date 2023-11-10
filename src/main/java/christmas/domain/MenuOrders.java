package christmas.domain;

import christmas.domain.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;

public class MenuOrders {

    private static final int MAX_ORDER_QUANTITY = 20;

    private final List<MenuQuantity> menuQuantities = new ArrayList<>();

    public MenuOrders(List<MenuQuantity> menuQuantities) {
        validateMenuDuplicated(menuQuantities);
        validateQuantitySize(menuQuantities);
        this.menuQuantities.addAll(menuQuantities);
    }

    private void validateQuantitySize(List<MenuQuantity> menuQuantities) {
        int numQuantity = 0;
        for (MenuQuantity menuQuantity : menuQuantities) {
            numQuantity += menuQuantity.getQuantity();
        }

        if (numQuantity > MAX_ORDER_QUANTITY) {
            throw new IllegalArgumentException(ExceptionMessage.MAX_ORDER_QUANTITY_EXCEEDED);
        }
    }


    private void validateMenuDuplicated(List<MenuQuantity> menuQuantities) {
        List<Menu> distinctMenu = menuQuantities.stream()
                .map(MenuQuantity::getMenu)
                .distinct()
                .toList();

        if (menuQuantities.size() != distinctMenu.size()) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_ORDER);
        }
    }

    public List<MenuQuantity> getMenuOrders() {
        return menuQuantities;
    }

    public Money calculateTotalCost() {
        Money cost = Money.ZERO;
        for (MenuQuantity menuQuantity : menuQuantities) {
            cost = cost.add(menuQuantity.calculateCost());
        }

        return cost;
    }
}
