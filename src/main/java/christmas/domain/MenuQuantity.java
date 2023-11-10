package christmas.domain;

import christmas.domain.exception.ExceptionMessage;

public class MenuQuantity {

    public static final MenuQuantity EMPTY = new MenuQuantity(Menu.NONE, 1);

    private final Menu menu;
    private final int quantity;

    public MenuQuantity(Menu menu, int quantity) {
        validateQuantityPositive(quantity);

        this.menu = menu;
        this.quantity = quantity;
    }

    public MenuQuantity(String menuName, int quantity) {
        validateMenuExist(menuName);
        validateQuantityPositive(quantity);

        this.menu = Menu.withName(menuName);
        this.quantity = quantity;
    }

    private void validateQuantityPositive(int quantity) {
        if (quantity <= 0) {
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

    public int getQuantity() {
        return quantity;
    }

    public Money calculateCost() {
        return menu.getCost().multiply(quantity);
    }

    public boolean isEmpty() {
        return menu.equals(Menu.NONE);
    }

    public boolean isBeverage() {
        return menu.isBeverage();
    }

    public boolean isSameType(MenuType menuType) {
        return menu.isSameType(menuType);
    }
}
