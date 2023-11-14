package christmas.controller;

import christmas.application.domain.Badge;
import christmas.application.domain.EventBenefitAmount;
import christmas.application.domain.MenuQuantity;
import christmas.application.domain.Money;
import christmas.application.domain.Order;
import christmas.application.domain.Reservation;
import christmas.application.domain.ReservationDate;
import christmas.application.service.PromotionService;
import christmas.application.service.ReservationService;
import christmas.controller.dto.request.OrderCreateRequest;
import christmas.controller.dto.request.ReservationDateCreateRequest;
import christmas.controller.dto.response.BadgeResponse;
import christmas.controller.dto.response.BenefitAmountsResponse;
import christmas.controller.dto.response.ChargeResponse;
import christmas.controller.dto.response.MenuQuantitiesResponse;
import christmas.controller.dto.response.ReservationDateResponse;
import christmas.controller.dto.response.TotalBenefitAmountResponse;
import java.util.List;
import java.util.Optional;

public class PromotionController {
    private final InputView inputView;
    private final OutputView outputView;

    private final ExceptionHandler exceptionHandler;

    private final PromotionService promotionService;
    private final ReservationService reservationService;


    public PromotionController(InputView inputView, OutputView outputView,
                               ExceptionHandler exceptionHandler,
                               PromotionService promotionService,
                               ReservationService reservationService) {

        this.inputView = inputView;
        this.outputView = outputView;
        this.exceptionHandler = exceptionHandler;
        this.promotionService = promotionService;
        this.reservationService = reservationService;
    }

    public void run() {
        printWelcomeMessage();

        ReservationDate reservationDate = inputReservationDate();
        Order order = inputOrder();

        Reservation reservation = createReservation(order, reservationDate);
        printResult(reservation);
    }

    private void printWelcomeMessage() {
        outputView.printWelcomeMessage();
    }

    private Reservation createReservation(Order order, ReservationDate reservationDate) {
        return reservationService.createReservation(order, reservationDate);
    }

    private Order inputOrder() {
        return (Order) exceptionHandler.handle(inputView, outputView, (inputView) -> {
            OrderCreateRequest orderCreateRequest = inputView.inputMenuOrderRequest();
            return reservationService.createOrder(orderCreateRequest.toOrderCreate());
        });
    }

    private void printResult(Reservation reservation) {
        printResultTitle(reservation);
        printOrder(reservation);

        printPreDiscountCharge(reservation);
        printGifts(reservation);

        printBenefitAmounts(reservation);
        printTotalDiscountAmount(reservation);
        printChargeAfterDiscount(reservation);
        printBadge(reservation);
    }

    private void printResultTitle(Reservation reservation) {
        outputView.printResultTitle(ReservationDateResponse.from(reservation));
    }

    private void printOrder(Reservation reservation) {
        outputView.printMenuOrders(MenuQuantitiesResponse.from(reservation.getMenuQuantities()));
    }

    private void printBadge(Reservation reservation) {
        Optional<Badge> badgeOptional = promotionService.receiveBadge(reservation);
        if (badgeOptional.isEmpty()) {
            outputView.printBadge(null);
            return;
        }
        outputView.printBadge(BadgeResponse.from(badgeOptional.get()));
    }

    private void printChargeAfterDiscount(Reservation reservation) {
        Money chargeAfterDiscount = promotionService.calculateChargeAfterDiscount(reservation);
        outputView.printChargeAfterDiscount(ChargeResponse.from(chargeAfterDiscount));
    }

    private void printTotalDiscountAmount(Reservation reservation) {
        Money totalBenefitAmount = promotionService.calculateTotalBenefitAmount(reservation);
        outputView.printTotalBenefitAmount(TotalBenefitAmountResponse.from(totalBenefitAmount));
    }

    private void printBenefitAmounts(Reservation reservation) {
        List<EventBenefitAmount> eventBenefitAmounts = promotionService.calculateBenefitAmounts(reservation);
        if (eventBenefitAmounts.isEmpty()) {
            outputView.printDiscountAmounts(null);
            return;
        }

        outputView.printDiscountAmounts(BenefitAmountsResponse.from(eventBenefitAmounts));
    }

    private void printGifts(Reservation reservation) {
        List<MenuQuantity> gifts = promotionService.receiveGifts(reservation);

        outputView.printGifts(MenuQuantitiesResponse.from(gifts));
    }

    private void printPreDiscountCharge(Reservation reservation) {
        Money preDiscountCharge = promotionService.calculatePreDiscountCharge(reservation);
        outputView.printPreDiscountCharge(ChargeResponse.from(preDiscountCharge));
    }

    private ReservationDate inputReservationDate() {
        return (ReservationDate) exceptionHandler.handle(inputView, outputView, (inputView) -> {
            ReservationDateCreateRequest reservationDateCreateRequest = inputView.inputReservationDate();
            return new ReservationDate(reservationDateCreateRequest.getReservationDate());
        });
    }
}
