package christmas.domain.exception;

public class ExceptionMessage {
    public static final String INVALID_DATE_RANGE = "유효하지 않은 날짜입니다.";
    public static final String INVALID_ORDER = "유효하지 않은 주문입니다.";
    public static final String MAX_ORDER_QUANTITY_EXCEEDED = "최대 주문 가능 갯수를 초과했습니다.";
    public static final String INVALID_MENU_COMPOSITION = "메뉴는 음료로만 구성될 수 없습니다.";

    private ExceptionMessage() {
    }
}
