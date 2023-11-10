package christmas.domain.exception;

public class ExceptionMessage {
    public static final String INVALID_DATE_RANGE = "유효하지 않은 날짜입니다.";
    public static final String INVALID_ORDER = "유효하지 않은 주문입니다.";
    public static final String MAX_ORDER_QUANTITY_EXCEEDED = "최대 주문 가능 갯수를 초과했습니다.";

    private ExceptionMessage() {
    }
}
