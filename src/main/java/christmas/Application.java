package christmas;

import christmas.controller.PromotionController;
import christmas.domain.Menu;
import christmas.domain.Money;
import christmas.handler.InfiniteRetryExceptionHandler;
import christmas.service.GiftEventService;
import christmas.service.PromotionService;
import christmas.view.InputConsoleView;
import christmas.view.OutputConsoleView;

public class Application {
    public static void main(String[] args) {
        PromotionController promotionController = new PromotionController(
                new InputConsoleView(), new OutputConsoleView(),
                new InfiniteRetryExceptionHandler(),
                new PromotionService(),
                new GiftEventService(Money.of(120_000L), Menu.CHAMPAGNE, 1));
        promotionController.run();
    }
}
