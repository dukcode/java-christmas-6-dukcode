package christmas.controller.dto.request;

import christmas.application.domain.OrderCreate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class OrderCreateRequest {

    private static final String ORDER_DELIMITER = ",";

    private final List<MenuQuantityCreateRequest> menuQuantityCreateRequests = new ArrayList<>();

    public OrderCreateRequest(String orders) {
        List<String> menuOrders = parseOrders(orders);
        for (String menuOrder : menuOrders) {
            menuQuantityCreateRequests.add(new MenuQuantityCreateRequest(menuOrder));
        }
    }

    private List<String> parseOrders(String orders) {
        return Arrays.stream(orders.split(ORDER_DELIMITER))
                .map(String::trim)
                .toList();
    }

    public List<MenuQuantityCreateRequest> getMenuOrderRequests() {
        return menuQuantityCreateRequests;
    }

    public OrderCreate toOrderCreate() {
        return new OrderCreate(menuQuantityCreateRequests.stream()
                .map(MenuQuantityCreateRequest::toMenuQuantityCreate)
                .toList());
    }
}
