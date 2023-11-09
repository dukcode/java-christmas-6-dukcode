package christmas.view;

import christmas.controller.ChristmasPromotionOutputView;

public class ChristmasPromotionOutputConsoleView implements ChristmasPromotionOutputView {

    @Override
    public void printWelcomeMessage() {
        System.out.println("안녕하세요! 우테코 식당 12월 이벤트 플래너입니다.");
    }
}
