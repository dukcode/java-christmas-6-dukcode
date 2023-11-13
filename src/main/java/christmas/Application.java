package christmas;

import christmas.controller.PromotionController;
import christmas.domain.Menu;
import christmas.domain.MenuType;
import christmas.domain.Money;
import christmas.handler.InfiniteRetryExceptionHandler;
import christmas.service.PromotionService;
import christmas.service.badge.BadgeManager;
import christmas.service.event.Event;
import christmas.service.event.condition.CompositeEventCondition;
import christmas.service.event.condition.MinimumOrderAmountCondition;
import christmas.service.event.condition.PeriodEventCondition;
import christmas.service.event.policy.DDayEventPolicy;
import christmas.service.event.policy.GiftEventPolicy;
import christmas.service.event.policy.SpecialDayEventPolicy;
import christmas.service.event.policy.WeekdayEventPolicy;
import christmas.service.event.policy.WeekendEventPolicy;
import christmas.view.InputConsoleView;
import christmas.view.OutputConsoleView;
import java.time.YearMonth;
import java.util.List;
import java.util.Set;

public class Application {
    public static void main(String[] args) {
        int eventYear = 2023;
        int eventMonth = 12;

        YearMonth eventYearMonth = YearMonth.of(eventYear, eventMonth);

        CompositeEventCondition commonEventCondition = createCommonEventCondition(eventYearMonth);

        List<Event> events = createEvents(commonEventCondition, eventYearMonth);
        BadgeManager badgeManager = new BadgeManager();

        PromotionController promotionController = createPromotionController(eventYear, eventMonth, events,
                badgeManager);

        promotionController.run();
    }

    private static List<Event> createEvents(CompositeEventCondition commonEventCondition, YearMonth eventYearMonth) {
        Event christmasDDayDiscountEvent = createChristmasDDayDiscountEvent(commonEventCondition, eventYearMonth);
        Event weekdayDiscountEvent = createWeekdayDiscountEvent(commonEventCondition);
        Event weekendDiscountEvent = createWeekendDiscountEvent(commonEventCondition);
        Event specialDayDiscountEvent = createSpecialDayDiscountEvent(commonEventCondition, eventYearMonth);
        Event giftEvent = createGiftEvent(commonEventCondition);

        return List.of(christmasDDayDiscountEvent, weekdayDiscountEvent, weekendDiscountEvent,
                specialDayDiscountEvent, giftEvent);
    }

    private static PromotionController createPromotionController(int eventYear, int eventMonth, List<Event> events,
                                                                 BadgeManager badgeManager) {
        return new PromotionController(
                new InputConsoleView(eventYear, eventMonth),
                new OutputConsoleView(eventYear, eventMonth),
                new InfiniteRetryExceptionHandler(),
                new PromotionService(events, badgeManager)
        );
    }

    private static Event createGiftEvent(CompositeEventCondition commonEventCondition) {
        return new Event("증정 이벤트",
                commonEventCondition,
                new GiftEventPolicy(
                        Money.of(120_000),
                        Menu.CHAMPAGNE,
                        1
                )
        );
    }

    private static Event createSpecialDayDiscountEvent(CompositeEventCondition commonEventCondition,
                                                       YearMonth eventYearMonth) {
        return new Event(
                "특별 할인",
                commonEventCondition,
                new SpecialDayEventPolicy(
                        Money.of(1_000L),
                        Set.of(
                                eventYearMonth.atDay(3),
                                eventYearMonth.atDay(10),
                                eventYearMonth.atDay(17),
                                eventYearMonth.atDay(24),
                                eventYearMonth.atDay(25),
                                eventYearMonth.atDay(31)
                        )
                ));
    }

    private static Event createWeekendDiscountEvent(CompositeEventCondition commonEventCondition) {
        return new Event(
                "주말 할인",
                commonEventCondition,
                new WeekendEventPolicy(MenuType.MAIN_DISH,
                        Money.of(2_023L))
        );
    }

    private static Event createWeekdayDiscountEvent(CompositeEventCondition commonEventCondition) {
        return new Event(
                "평일 할인",
                commonEventCondition,
                new WeekdayEventPolicy(MenuType.DESSERT,
                        Money.of(2_023L))
        );
    }

    private static Event createChristmasDDayDiscountEvent(CompositeEventCondition commonEventCondition,
                                                          YearMonth eventYearMonth) {
        return new Event(
                "크리스마스 디데이 할인",
                commonEventCondition,
                new DDayEventPolicy(
                        eventYearMonth.atDay(1),
                        eventYearMonth.atDay(25),
                        Money.of(1_000L),
                        Money.of(100L))
        );
    }

    private static CompositeEventCondition createCommonEventCondition(YearMonth eventYearMonth) {
        return new CompositeEventCondition(
                List.of(
                        PeriodEventCondition.wholeMonth(eventYearMonth),
                        new MinimumOrderAmountCondition(Money.of(10_000))
                ));
    }
}
