package christmas;

import christmas.controller.PromotionController;
import christmas.domain.Menu;
import christmas.domain.MenuType;
import christmas.domain.Money;
import christmas.handler.InfiniteRetryExceptionHandler;
import christmas.service.DDayDiscountEventProcessor;
import christmas.service.GiftEventProcessor;
import christmas.service.PromotionService;
import christmas.service.WeekdayDiscountEventProcessor;
import christmas.view.InputConsoleView;
import christmas.view.OutputConsoleView;
import java.time.LocalDate;

public class Application {
    public static void main(String[] args) {
        int eventYear = 2023;
        int eventMonth = 12;

        PromotionController promotionController = new PromotionController(
                new InputConsoleView(eventYear, eventMonth),
                new OutputConsoleView(eventYear, eventMonth),
                new InfiniteRetryExceptionHandler(),
                new PromotionService(
                        new GiftEventProcessor(Money.of(120_000L), Menu.CHAMPAGNE, 1),
                        new DDayDiscountEventProcessor(
                                Money.of(10_000L), Money.of(1_000L), Money.of(100L),
                                LocalDate.of(eventYear, eventMonth, 1),
                                LocalDate.of(eventYear, eventMonth, 25)
                        ),
                        new WeekdayDiscountEventProcessor(
                                Money.of(10_000L),
                                Money.of(2_023L),
                                MenuType.DESSERT
                        )
                )
        );
        promotionController.run();
    }
}
