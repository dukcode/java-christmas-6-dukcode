package christmas.controller.dto.response;

import christmas.application.domain.MenuQuantity;

public class MenuQuantityResponse {

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
        return String.format("%s %d개", menuName, quantity);
    }
}
