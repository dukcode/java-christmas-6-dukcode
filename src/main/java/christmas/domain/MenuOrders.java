package christmas.domain;

import christmas.domain.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;

public class MenuOrders {

    private final List<MenuQuantity> menuQuantities = new ArrayList<>();

    public MenuOrders(List<MenuQuantity> menuQuantities) {
        validateMenuDuplicated(menuQuantities);
        this.menuQuantities.addAll(menuQuantities);
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
