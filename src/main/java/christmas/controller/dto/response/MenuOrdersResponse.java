package christmas.controller.dto.response;

import christmas.domain.MenuOrders;
import java.util.List;

public class MenuOrdersResponse {

    private final List<MenuQuantityResponse> menuQuantityResponses;

    private MenuOrdersResponse(List<MenuQuantityResponse> menuQuantityResponses) {
        this.menuQuantityResponses = menuQuantityResponses;
    }

    public static MenuOrdersResponse from(MenuOrders menuOrders) {
        return new MenuOrdersResponse(
                menuOrders.getMenuOrders()
                        .stream()
                        .map(MenuQuantityResponse::from)
                        .toList());
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (MenuQuantityResponse menuQuantityResponse : menuQuantityResponses) {
            sb.append(menuQuantityResponse.toString());
            sb.append("\n");
        }

        return sb.toString();
    }
}
