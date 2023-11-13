package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.controller.InputView;
import christmas.controller.dto.request.OrderCreateRequest;
import christmas.controller.dto.request.ReservationDateCreateRequest;

public class InputConsoleView implements InputView {

    private final int eventYear;
    private final int eventMonth;

    public InputConsoleView(int eventYear, int eventMonth) {
        this.eventYear = eventYear;
        this.eventMonth = eventMonth;
    }

    @Override
    public ReservationDateCreateRequest inputReservationDate() {
        System.out.printf("%d월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)\n", eventMonth);
        return new ReservationDateCreateRequest(
                String.valueOf(eventYear),
                String.valueOf(eventMonth),
                Console.readLine()
        );
    }

    @Override
    public OrderCreateRequest inputMenuOrderRequest() {
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
        return new OrderCreateRequest(Console.readLine());
    }
}
