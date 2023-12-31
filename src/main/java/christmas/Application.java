package christmas;

import christmas.application.domain.MenuType;
import christmas.application.domain.Money;
import christmas.application.service.BadgeRepository;
import christmas.application.service.Event;
import christmas.application.service.EventCondition;
import christmas.application.service.MenuRepository;
import christmas.application.service.PromotionService;
import christmas.application.service.ReservationService;
import christmas.application.service.event.condition.CompositeEventCondition;
import christmas.application.service.event.condition.MinimumOrderAmountEventCondition;
import christmas.application.service.event.condition.PeriodEventCondition;
import christmas.application.service.event.policy.DDayEventPolicy;
import christmas.application.service.event.policy.GiftEventPolicy;
import christmas.application.service.event.policy.SpecialDayEventPolicy;
import christmas.application.service.event.policy.WeekdayEventPolicy;
import christmas.application.service.event.policy.WeekendEventPolicy;
import christmas.controller.ExceptionHandler;
import christmas.controller.InputView;
import christmas.controller.OutputView;
import christmas.controller.PromotionController;
import christmas.handler.InfiniteRetryExceptionHandler;
import christmas.repository.DefaultBadgeRepository;
import christmas.repository.DefaultMenuRepository;
import christmas.view.InputConsoleView;
import christmas.view.OutputConsoleView;
import java.time.YearMonth;
import java.util.Set;

public class Application {
    private static final int EVENT_YEAR = 2023;
    private static final int EVENT_MONTH = 12;

    private static final YearMonth eventYearMonth = YearMonth.of(EVENT_YEAR, EVENT_MONTH);

    public static void main(String[] args) {

        MenuRepository menuRepository = menuRepository();
        BadgeRepository badgeRepository = badgeRepository();

        PromotionService promotionService = createPromotionService(menuRepository, badgeRepository);
        ReservationService reservationService = createReservationService(menuRepository);

        PromotionController promotionController = createPromotionController(promotionService, reservationService);

        promotionController.run();
    }

    private static PromotionController createPromotionController(PromotionService promotionService,
                                                                 ReservationService reservationService) {
        InputView inputView = inputView();
        OutputView outputView = outputView();
        ExceptionHandler exceptionHandler = exceptionHandler();

        return promotionController(inputView, outputView,
                exceptionHandler, promotionService, reservationService);
    }

    private static ReservationService createReservationService(MenuRepository menuRepository) {
        return reservationService(menuRepository);
    }

    private static PromotionService createPromotionService(MenuRepository menuRepository,
                                                           BadgeRepository badgeRepository) {

        EventCondition periodEventCondition = periodEventCondition();
        EventCondition minimumOrderAmountEventCondition = minimumOrderAmountCondition();
        EventCondition compositeEventCondition =
                compositEventCondition(periodEventCondition, minimumOrderAmountEventCondition);

        Event christmasDDayDiscountEvent = christmasDDayDiscountEvent(periodEventCondition);
        Event weekdayDiscountEvent = weekdayDiscountEvent(compositeEventCondition);
        Event weekendDiscountEvent = weekendDiscountEvent(compositeEventCondition);
        Event giftEvent = giftEvent(periodEventCondition, menuRepository);
        Event specialDayDiscountEvent = specialDayDiscountEvent(compositeEventCondition);

        return promotionService(badgeRepository,
                christmasDDayDiscountEvent,
                weekdayDiscountEvent,
                weekendDiscountEvent,
                giftEvent,
                specialDayDiscountEvent);
    }

    private static EventCondition compositEventCondition(EventCondition... eventConditions) {
        return new CompositeEventCondition(eventConditions);
    }

    private static MinimumOrderAmountEventCondition minimumOrderAmountCondition() {
        return new MinimumOrderAmountEventCondition(Money.of(10_000L));
    }

    private static EventCondition periodEventCondition() {
        return PeriodEventCondition.wholeMonth(eventYearMonth);
    }

    private static DefaultBadgeRepository badgeRepository() {
        return new DefaultBadgeRepository();
    }

    private static MenuRepository menuRepository() {
        return new DefaultMenuRepository();
    }

    private static PromotionController promotionController(InputView inputView, OutputView outputView,
                                                           ExceptionHandler exceptionHandler,
                                                           PromotionService promotionService,
                                                           ReservationService reservationService) {
        return new PromotionController(inputView, outputView,
                exceptionHandler, promotionService, reservationService);
    }

    private static ReservationService reservationService(MenuRepository menuRepository) {
        return new ReservationService(menuRepository);
    }

    private static PromotionService promotionService(BadgeRepository badgeRepository, Event... events) {
        return new PromotionService(badgeRepository, events);
    }

    private static ExceptionHandler exceptionHandler() {
        return new InfiniteRetryExceptionHandler();
    }

    private static OutputConsoleView outputView() {
        return new OutputConsoleView(eventYearMonth.getYear(), eventYearMonth.getMonthValue());
    }

    private static InputView inputView() {
        return new InputConsoleView(eventYearMonth.getYear(), eventYearMonth.getMonthValue());
    }

    private static Event giftEvent(EventCondition eventCondition, MenuRepository menuRepository) {
        return new Event("증정 이벤트",
                eventCondition,
                new GiftEventPolicy(
                        menuRepository,
                        Money.of(120_000),
                        "샴페인",
                        1
                )
        );
    }

    private static Event specialDayDiscountEvent(EventCondition eventCondition) {
        return new Event(
                "특별 할인",
                eventCondition,
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

    private static Event weekendDiscountEvent(EventCondition eventCondition) {
        return new Event(
                "주말 할인",
                eventCondition,
                new WeekendEventPolicy(MenuType.MAIN_DISH,
                        Money.of(2_023L))
        );
    }

    private static Event weekdayDiscountEvent(EventCondition eventCondition) {
        return new Event(
                "평일 할인",
                eventCondition,
                new WeekdayEventPolicy(MenuType.DESSERT,
                        Money.of(2_023L))
        );
    }

    private static Event christmasDDayDiscountEvent(EventCondition eventCondition) {
        return new Event(
                "크리스마스 디데이 할인",
                eventCondition,
                new DDayEventPolicy(
                        eventYearMonth.atDay(1),
                        eventYearMonth.atDay(25),
                        Money.of(1_000L),
                        Money.of(100L))
        );
    }
}
