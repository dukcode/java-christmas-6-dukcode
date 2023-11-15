package christmas.controller.dto.response;

import christmas.application.domain.MenuQuantity;

public class MenuQuantityResponse {
    private static final String MENU_QUANTITY_STRING_FORMAT = "%s %d개";

    private final String menuName;
    private final int quantity;

    private MenuQuantityResponse(String menuName, int quantity) {
        this.menuName = menuName;
        this.quantity = quantity;
    }

    public static MenuQuantityResponse from(MenuQuantity menuQuantity) {
        return new MenuQuantityResponse(menuQuantity.getMenu().getName(), menuQuantity.getQuantity());
    }

    @Override
    public String toString() {
        return String.format(MENU_QUANTITY_STRING_FORMAT, menuName, quantity);
    }

}
