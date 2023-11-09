package christmas.controller.dto.request;

import christmas.controller.dto.validator.NumberFormatValidator;
import christmas.domain.exception.ExceptionMessage;

public class MenuOrderRequest {

    private static final String MENU_QUANTITY_DELIMITER = "-";
    private final String menuName;
    private final int orderCount;

    public MenuOrderRequest(String menuOrder) {
        validateDelimiter(menuOrder);

        String menuName = parseMenuName(menuOrder);
        String orderCount = parseOrderCount(menuOrder);

        validateOrderCount(orderCount);

        this.menuName = menuName;
        this.orderCount = Integer.parseInt(orderCount);
    }

    private static String parseMenuName(String order) {
        return order.split(MENU_QUANTITY_DELIMITER)[0].trim();
    }

    private void validateDelimiter(String menuOrder) {

        if (menuOrder.split(MENU_QUANTITY_DELIMITER).length != 2) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_ORDER);
        }
    }

    private String parseOrderCount(String order) {
        return order.split(MENU_QUANTITY_DELIMITER)[1].trim();
    }

    private void validateOrderCount(String orderCount) {
        NumberFormatValidator.validate(orderCount);
    }

    public String getMenuName() {
        return menuName;
    }

    public int getOrderCount() {
        return orderCount;
    }
}
