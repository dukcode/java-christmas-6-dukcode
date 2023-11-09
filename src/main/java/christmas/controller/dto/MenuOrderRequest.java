package christmas.controller.dto;

import christmas.controller.dto.validator.NumberFormatValidator;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class MenuOrderRequest {

    private static final String MENU_SEPARATOR = ",";
    private static final String MENU_QUANTITY_DELIMITER = "-";
    private Map<String, Integer> orders = new HashMap<>();

    public MenuOrderRequest(String orders) {
        validateOrder(orders);
        Arrays.stream(orders.split(MENU_SEPARATOR))
                .forEach(order -> {
                    String[] tokens = order.trim()
                            .split(MENU_QUANTITY_DELIMITER);
                    String menu = tokens[0].trim();
                    int orderQuantity = Integer.parseInt(tokens[1].trim());
                    this.orders.put(menu, orderQuantity);
                });
    }


    private void validateOrder(String orders) {
        Arrays.stream(orders.split(MENU_SEPARATOR))
                .forEach(order -> {
                    String orderQuantity = order.trim()
                            .split(MENU_QUANTITY_DELIMITER)[1]
                            .trim();
                    NumberFormatValidator.validate(orderQuantity);
                });
    }
}
