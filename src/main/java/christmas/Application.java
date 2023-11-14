package christmas;

import christmas.controller.PromotionController;
import christmas.controller.handler.InfiniteRetryExceptionHandler;
import christmas.domain.MenuType;
import christmas.domain.Money;
import christmas.repository.BadgeRepository;
import christmas.repository.DefaultBadgeRepository;
import christmas.repository.DefaultMenuRepository;
import christmas.repository.MenuRepository;
import christmas.service.PromotionService;
import christmas.service.ReservationService;
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

        MenuRepository menuRepository = createMenuRepository();

        CompositeEventCondition commonEventCondition = createCommonEventCondition(eventYearMonth);

        BadgeRepository badgeRepository = new DefaultBadgeRepository();

        List<Event> events = createEvents(commonEventCondition, eventYearMonth, menuRepository);

        PromotionController promotionController = createPromotionController(eventYear, eventMonth, menuRepository,
                events,
                badgeRepository);

        promotionController.run();
    }

    private static MenuRepository createMenuRepository() {
        return new DefaultMenuRepository();
    }

    private static List<Event> createEvents(CompositeEventCondition commonEventCondition, YearMonth eventYearMonth,
                                            MenuRepository menuRepository) {
        Event christmasDDayDiscountEvent = createChristmasDDayDiscountEvent(commonEventCondition, eventYearMonth);
        Event weekdayDiscountEvent = createWeekdayDiscountEvent(commonEventCondition);
        Event weekendDiscountEvent = createWeekendDiscountEvent(commonEventCondition);
        Event specialDayDiscountEvent = createSpecialDayDiscountEvent(commonEventCondition, eventYearMonth);
        Event giftEvent = createGiftEvent(commonEventCondition, menuRepository);

        return List.of(christmasDDayDiscountEvent, weekdayDiscountEvent, weekendDiscountEvent,
                specialDayDiscountEvent, giftEvent);
    }

    private static PromotionController createPromotionController(int eventYear, int eventMonth,
                                                                 MenuRepository menuRepository, List<Event> events,
                                                                 BadgeRepository badgeRepository) {
        return new PromotionController(
                new InputConsoleView(eventYear, eventMonth),
                new OutputConsoleView(eventYear, eventMonth),
                new InfiniteRetryExceptionHandler(),
                new PromotionService(events, badgeRepository),
                new ReservationService(menuRepository));
    }

    private static Event createGiftEvent(CompositeEventCondition commonEventCondition, MenuRepository menuRepository) {
        return new Event("증정 이벤트",
                commonEventCondition,
                new GiftEventPolicy(
                        menuRepository,
                        Money.of(120_000),
                        "샴페인",
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
