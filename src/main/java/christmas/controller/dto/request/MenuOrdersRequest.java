package christmas.controller.dto.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MenuOrdersRequest {

    private static final String MENU_SEPARATOR = ",";

    private List<MenuOrderRequest> menuOrderRequests = new ArrayList<>();

    public MenuOrdersRequest(String orders) {
        List<String> menuOrders = parseOrders(orders);
        for (String menuOrder : menuOrders) {
            menuOrderRequests.add(new MenuOrderRequest(menuOrder));
        }
    }

    private List<String> parseOrders(String orders) {
        return Arrays.stream(orders.split(MENU_SEPARATOR))
                .map(String::trim)
                .toList();
    }

    public List<MenuOrderRequest> getMenuOrderRequests() {
        return menuOrderRequests;
    }

}
