package christmas.controller.dto;

import christmas.domain.MenuOrders;
import java.util.List;

public class MenuOrdersResponse {

    private final List<MenuOrderResponse> menuOrderResponses;

    private MenuOrdersResponse(List<MenuOrderResponse> menuOrderResponses) {
        this.menuOrderResponses = menuOrderResponses;
    }

    public static MenuOrdersResponse from(MenuOrders menuOrders) {
        return new MenuOrdersResponse(
                menuOrders.getMenuOrders()
                        .stream()
                        .map(MenuOrderResponse::from)
                        .toList());
    }

    public List<MenuOrderResponse> getMenuOrderResponses() {
        return menuOrderResponses;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (MenuOrderResponse menuOrderResponse : menuOrderResponses) {
            sb.append(menuOrderResponse.toString());
            sb.append("\n");
        }

        return sb.toString();
    }
}
