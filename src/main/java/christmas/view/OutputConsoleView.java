package christmas.view;

import christmas.controller.OutputView;

public class OutputConsoleView implements OutputView {

    private static final String ERROR_PREFIX = "[ERROR] ";

    @Override
    public void printWelcomeMessage() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
    }

    @Override
    public void printError(Exception e) {
        System.out.println(e.getMessage());
    }
}
