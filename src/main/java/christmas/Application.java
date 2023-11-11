package christmas;

import christmas.controller.PromotionController;
import christmas.domain.Menu;
import christmas.domain.MenuType;
import christmas.domain.Money;
import christmas.handler.InfiniteRetryExceptionHandler;
import christmas.service.BadgeAwardEventProcessor;
import christmas.service.DDayDiscountEventProcessor;
import christmas.service.GiftEventProcessor;
import christmas.service.PromotionService;
import christmas.service.SpecialDiscountEventProcessor;
import christmas.service.WeekdayDiscountEventProcessor;
import christmas.service.WeekendDiscountEventProcessor;
import christmas.view.InputConsoleView;
import christmas.view.OutputConsoleView;
import java.time.LocalDate;
import java.util.Set;

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
                        ),
                        new WeekendDiscountEventProcessor(
                                Money.of(10_000L),
                                Money.of(2_023L),
                                MenuType.MAIN_DISH
                        ),
                        new SpecialDiscountEventProcessor(
                                Money.of(10_000L),
                                Money.of(1_000L),
                                Set.of(
                                        LocalDate.of(eventYear, eventMonth, 3),
                                        LocalDate.of(eventYear, eventMonth, 10),
                                        LocalDate.of(eventYear, eventMonth, 17),
                                        LocalDate.of(eventYear, eventMonth, 24),
                                        LocalDate.of(eventYear, eventMonth, 25),
                                        LocalDate.of(eventYear, eventMonth, 31)
                                )
                        ),
                        new BadgeAwardEventProcessor()
                )
        );

        promotionController.run();
    }
}
