package christmas.controller.dto;

import christmas.controller.dto.validator.NumberFormatValidator;

public class MenuOrderRequest {

    private static final String MENU_QUANTITY_DELIMITER = "-";
    private final String menuName;
    private final int orderCount;

    public MenuOrderRequest(String menuOrder) {
        String menuName = parseMenuName(menuOrder);
        String orderCount = parseOrderCount(menuOrder);

        validateOrderCount(orderCount);

        this.menuName = menuName;
        this.orderCount = Integer.parseInt(orderCount);
    }

    private static String parseMenuName(String order) {
        return order.split(MENU_QUANTITY_DELIMITER)[0].trim();
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
