package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.controller.InputView;
import christmas.controller.dto.request.OrderCreateRequest;
import christmas.controller.dto.request.ReservationDateCreateRequest;

public class InputConsoleView implements InputView {

    private static final String RESERVATION_DATE_CREATE_REQUEST_FORMAT
            = """
            안녕하세요! 우테코 식당 %d월 이벤트 플래너입니다.
            %d월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)
            """;
    private static final String MENU_ORDER_REQUEST =
            "주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)";

    private final int eventYear;
    private final int eventMonth;

    public InputConsoleView(int eventYear, int eventMonth) {
        this.eventYear = eventYear;
        this.eventMonth = eventMonth;
    }

    @Override
    public ReservationDateCreateRequest inputReservationDate() {
        System.out.printf(RESERVATION_DATE_CREATE_REQUEST_FORMAT, eventMonth, eventMonth);
        return new ReservationDateCreateRequest(
                String.valueOf(eventYear),
                String.valueOf(eventMonth),
                Console.readLine()
        );
    }

    @Override
    public OrderCreateRequest inputMenuOrderRequest() {
        System.out.println(MENU_ORDER_REQUEST);
        return new OrderCreateRequest(Console.readLine());
    }
}
