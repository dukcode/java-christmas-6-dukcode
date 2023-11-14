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

    private void validateQuantityPositive(int quantity) {
        if (quantity <= 0) {
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

    public boolean isNone() {
        return menu.equals(Menu.NONE);
    }

    public boolean isSameType(MenuType menuType) {
        return menu.isMenuTypeEquals(menuType);
    }
}
