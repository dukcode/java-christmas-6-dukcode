package christmas;

import christmas.controller.PromotionController;
import christmas.handler.InfiniteRetryExceptionHandler;
import christmas.service.PromotionService;
import christmas.view.InputConsoleView;
import christmas.view.OutputConsoleView;

public class Application {
    public static void main(String[] args) {
        PromotionController promotionController = new PromotionController(new InputConsoleView(),
                new OutputConsoleView(), new InfiniteRetryExceptionHandler());
        promotionController.run();
    }
}
