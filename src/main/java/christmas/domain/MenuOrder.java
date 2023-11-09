package christmas.domain;

import christmas.domain.exception.ExceptionMessage;

public class MenuOrder {

    private final Menu menu;
    private final int orderCount;

    public MenuOrder(String menuName, int orderCount) {
        validateMenuExist(menuName);
        validateOrderCountPositive(orderCount);

        this.menu = Menu.withName(menuName);
        this.orderCount = orderCount;
    }

    private void validateOrderCountPositive(int orderCount) {
        if (orderCount <= 0) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_ORDER);
        }
    }

    private void validateMenuExist(String menuName) {
        if (!Menu.contains(menuName)) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_ORDER);
        }
    }

    public Menu getMenu() {
        return menu;
    }

    public int getOrderCount() {
        return orderCount;
    }

    public Money calculateCost() {
        return menu.getCost().multiply(orderCount);
    }
}
