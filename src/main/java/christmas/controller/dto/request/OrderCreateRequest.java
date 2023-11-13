package christmas.controller.dto.request;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderCreateRequest {

    private static final String ORDER_DELIMITER = ",";

    private final List<MenuQuantityRequest> menuQuantityRequests = new ArrayList<>();

    public OrderCreateRequest(String orders) {
        List<String> menuOrders = parseOrders(orders);
        for (String menuOrder : menuOrders) {
            menuQuantityRequests.add(new MenuQuantityRequest(menuOrder));
        }
    }

    private List<String> parseOrders(String orders) {
        return Arrays.stream(orders.split(ORDER_DELIMITER))
                .map(String::trim)
                .toList();
    }

    public List<MenuQuantityRequest> getMenuOrderRequests() {
        return menuQuantityRequests;
    }

}
