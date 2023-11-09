package christmas.controller.dto;

import christmas.domain.MenuOrder;

public class MenuOrderResponse {

    private final String menuName;
    private final int orderCount;

    private MenuOrderResponse(String menuName, int orderCount) {
        this.menuName = menuName;
        this.orderCount = orderCount;
    }

    public static MenuOrderResponse from(MenuOrder menuOrder) {
        return new MenuOrderResponse(menuOrder.getMenu().getName(), menuOrder.getOrderCount());
    }

    @Override
    public String toString() {
        return String.format("%s %d개", menuName, orderCount);
    }
}
