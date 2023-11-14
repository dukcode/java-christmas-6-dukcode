package christmas.domain;

import christmas.domain.exception.ExceptionMessage;

public class MenuQuantity {

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

    public boolean isSameMenuType(MenuType menuType) {
        return menu.isSameMenuType(menuType);
    }
}
