package christmas.controller.dto.response;

import christmas.domain.Menu;
import christmas.domain.MenuQuantity;

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

    private boolean isEmpty() {
        return menuName.equals(Menu.NONE.getName());
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "없음";
        }
        return String.format("%s %d개", menuName, quantity);
    }
}
