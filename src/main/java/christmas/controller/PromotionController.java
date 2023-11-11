package christmas.controller;

import christmas.controller.dto.request.MenuOrdersRequest;
import christmas.controller.dto.request.ReservationDateCreateRequest;
import christmas.controller.dto.response.BadgeAwardResponse;
import christmas.controller.dto.response.BenefitsResponse;
import christmas.controller.dto.response.ChargeResponse;
import christmas.controller.dto.response.DiscountAmountResponse;
import christmas.controller.dto.response.MenuOrdersResponse;
import christmas.controller.dto.response.MenuQuantityResponse;
import christmas.controller.dto.response.ReservationDateResponse;
import christmas.domain.Badge;
import christmas.domain.MenuOrders;
import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.ReservationDate;
import christmas.service.PromotionService;
import java.util.List;

public class PromotionController {
    private final InputView inputView;
    private final OutputView outputView;

    private final ExceptionHandler exceptionHandler;

    private final PromotionService promotionService;


    public PromotionController(InputView inputView, OutputView outputView,
                               ExceptionHandler exceptionHandler,
                               PromotionService promotionService) {

        this.inputView = inputView;
        this.outputView = outputView;
        this.exceptionHandler = exceptionHandler;
        this.promotionService = promotionService;
    }

    public void run() {
        outputView.printWelcomeMessage();

        ReservationDate reservationDate = inputReservationDate();
        MenuOrders menuOrders = inputMenuOrders();

        printResult(reservationDate, menuOrders);
    }

    private void printResult(ReservationDate reservationDate, MenuOrders menuOrders) {
        outputView.printResultTitle(ReservationDateResponse.from(reservationDate));
        outputView.printMenuOrders(MenuOrdersResponse.from(menuOrders));

        printPreDiscountCharge(menuOrders);
        printGiftEventResult(menuOrders);

        printBenefits(reservationDate, menuOrders);
        printTotalDiscountAmount(reservationDate, menuOrders);
        printChargeAfterDiscount(reservationDate, menuOrders);
        printBadgeAward(reservationDate, menuOrders);
    }

    private void printBadgeAward(ReservationDate reservationDate, MenuOrders menuOrders) {
        Badge badgeAward = promotionService.applyBadgeAwardEvent(reservationDate, menuOrders);
        outputView.printBadgeAward(new BadgeAwardResponse(badgeAward));
    }

    private void printChargeAfterDiscount(ReservationDate reservationDate, MenuOrders menuOrders) {
        Money chargeAfterDiscount = promotionService.calculateChargeAfterDiscount(reservationDate, menuOrders);
        outputView.printChargeAfterDiscount(new ChargeResponse(chargeAfterDiscount));
    }

    private void printTotalDiscountAmount(ReservationDate reservationDate, MenuOrders menuOrders) {
        Money totalDiscountAmount = promotionService.calculateTotalBenefitAmount(reservationDate, menuOrders);
        outputView.printTotalBenefitAmount(new DiscountAmountResponse(totalDiscountAmount));
    }

    private void printBenefits(ReservationDate reservationDate, MenuOrders menuOrders) {
        Money dDayEventDiscountAmount = promotionService.calculateDDayEventDiscountAmount(reservationDate, menuOrders);
        Money giftEventDiscountAmount = promotionService.calculateGiftEventDiscountAmount(menuOrders);
        Money weekdayEventDiscountAmount = promotionService.calculateWeekdayEventDiscountAmount(reservationDate,
                menuOrders);
        Money weekendEventDiscountAmount = promotionService.calculateWeekendEventDiscountAmount(reservationDate,
                menuOrders);
        Money specialEventDiscountAmount = promotionService.calculateSpecialEventDiscountAmount(reservationDate,
                menuOrders);

        outputView.printDiscountAmounts(new BenefitsResponse(
                dDayEventDiscountAmount,
                weekdayEventDiscountAmount,
                weekendEventDiscountAmount,
                specialEventDiscountAmount,
                giftEventDiscountAmount
        ));
    }

    private void printGiftEventResult(MenuOrders menuOrders) {
        MenuQuantity giftMenu = promotionService.applyGiftEvent(menuOrders);
        outputView.printGiftMenu(MenuQuantityResponse.from(giftMenu));
    }

    private void printPreDiscountCharge(MenuOrders menuOrders) {
        Money preDiscountCharge = promotionService.calculatePreDiscountCharge(menuOrders);
        outputView.printPreDiscountCharge(new ChargeResponse(preDiscountCharge));
    }

    private ReservationDate inputReservationDate() {
        return (ReservationDate) exceptionHandler.handle(inputView, outputView, (inputView) -> {
            ReservationDateCreateRequest reservationDateCreateRequest = inputView.inputReservationDate();
            return new ReservationDate(reservationDateCreateRequest.getReservationDate());
        });
    }

    private MenuOrders inputMenuOrders() {
        return (MenuOrders) exceptionHandler.handle(inputView, outputView, (inputView) -> {
            MenuOrdersRequest menuOrdersRequest = inputView.inputMenuOrderRequest();

            List<MenuQuantity> menuQuantities = menuOrdersRequest.getMenuOrderRequests()
                    .stream()
                    .map(request -> new MenuQuantity(request.getMenuName(), request.getOrderCount()))
                    .toList();

            return new MenuOrders(menuQuantities);
        });
    }
}
