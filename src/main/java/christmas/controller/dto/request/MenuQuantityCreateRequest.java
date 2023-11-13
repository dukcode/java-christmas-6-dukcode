package christmas.controller.dto.request;

import christmas.controller.dto.exception.ExceptionMessage;
import christmas.controller.dto.validator.NumberFormatValidator;

public class MenuQuantityCreateRequest {

    private static final String MENU_QUANTITY_DELIMITER = "-";
    private final String menuName;
    private final int orderCount;

    public MenuQuantityCreateRequest(String menuQuantity) {
        validateDelimiter(menuQuantity);

        String menuName = parseMenuName(menuQuantity);
        String orderCount = parseOrderCount(menuQuantity);

        validateOrderCount(orderCount);

        this.menuName = menuName;
        this.orderCount = Integer.parseInt(orderCount);
    }

    private static String parseMenuName(String order) {
        return order.split(MENU_QUANTITY_DELIMITER)[0].trim();
    }

    private void validateDelimiter(String menuQuantity) {

        if (menuQuantity.split(MENU_QUANTITY_DELIMITER).length != 2) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_ORDER_FORMAT);
        }
    }

    private String parseOrderCount(String menuQuantity) {
        return menuQuantity.split(MENU_QUANTITY_DELIMITER)[1].trim();
    }

    private void validateOrderCount(String menuQuantity) {
        NumberFormatValidator.validate(menuQuantity, ExceptionMessage.INVALID_ORDER_FORMAT);
    }

    public String getMenuName() {
        return menuName;
    }

    public int getOrderCount() {
        return orderCount;
    }
}
