package christmas.view;

import camp.nextstep.edu.missionutils.Console;
import christmas.controller.InputView;
import christmas.controller.dto.request.MenuOrdersRequest;
import christmas.controller.dto.request.ReservationDateCreateRequest;

public class InputConsoleView implements InputView {

    private static final String EVENT_YEAR = "2023";
    private static final String EVENT_MONTH = "12";

    @Override
    public ReservationDateCreateRequest inputReservationDate() {
        System.out.printf("%s월 중 식당 예상 방문 날짜는 언제인가요? (숫자만 입력해 주세요!)\n", EVENT_MONTH);
        return new ReservationDateCreateRequest(EVENT_YEAR, EVENT_MONTH, Console.readLine());
    }

    @Override
    public MenuOrdersRequest inputMenuOrderRequest() {
        System.out.println("주문하실 메뉴를 메뉴와 개수를 알려 주세요. (e.g. 해산물파스타-2,레드와인-1,초코케이크-1)");
        return new MenuOrdersRequest(Console.readLine());
    }
}
