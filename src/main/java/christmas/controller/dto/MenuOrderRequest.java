package christmas.controller.dto;

import christmas.controller.dto.validator.NumberFormatValidator;
import java.util.Arrays;
import java.util.List;

public class MenuOrderRequest {

    private static final String MENU_SEPARATOR = ",";
    private static final String MENU_QUANTITY_DELIMITER = "-";

    private final List<String> menuNames;
    private final List<Integer> orderCounts;

    public MenuOrderRequest(String orders) {
        this.menuNames = parseMenuNames(orders);

        List<String> orderCounts = parseOrderCounts(orders);
        validateOrderCounts(orderCounts);

        this.orderCounts = orderCounts.stream()
                .map(Integer::valueOf)
                .toList();
    }

    private List<String> parseMenuNames(String orders) {
        return Arrays.stream(orders.split(MENU_SEPARATOR))
                .map(order -> {
                    String[] tokens = order.trim()
                            .split(MENU_QUANTITY_DELIMITER);
                    return tokens[0].trim();
                }).toList();
    }

    private List<String> parseOrderCounts(String orders) {
        return Arrays.stream(orders.split(MENU_SEPARATOR))
                .map(order -> {
                    String[] tokens = order.trim()
                            .split(MENU_QUANTITY_DELIMITER);
                    return tokens[1].trim();
                }).toList();
    }

    private void validateOrderCounts(List<String> orderCounts) {
        for (String orderCount : orderCounts) {
            NumberFormatValidator.validate(orderCount);
        }
    }

    public List<String> getMenuNames() {
        return menuNames;
    }

    public List<Integer> getOrderCounts() {
        return orderCounts;
    }
}
