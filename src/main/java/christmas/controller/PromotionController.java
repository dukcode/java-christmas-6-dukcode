package christmas.controller;

import christmas.controller.dto.request.OrderCreateRequest;
import christmas.controller.dto.request.ReservationDateCreateRequest;
import christmas.controller.dto.response.BadgeResponse;
import christmas.controller.dto.response.BenefitAmountsResponse;
import christmas.controller.dto.response.ChargeResponse;
import christmas.controller.dto.response.MenuQuantitiesResponse;
import christmas.controller.dto.response.ReservationDateResponse;
import christmas.controller.dto.response.TotalBenefitAmountResponse;
import christmas.domain.Badge;
import christmas.domain.Menu;
import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.Order;
import christmas.domain.Reservation;
import christmas.domain.ReservationDate;
import christmas.service.PromotionService;
import christmas.service.ReservationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
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
            outputView.printBadge(Optional.empty());
            return;
        }
        outputView.printBadge(Optional.of(BadgeResponse.from(badgeOptional.get())));
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
        Map<String, Money> benefitAmounts = promotionService.calculateBenefitAmounts(reservation);
        if (benefitAmounts.isEmpty()) {
            outputView.printDiscountAmounts(Optional.empty());
            return;
        }

        outputView.printDiscountAmounts(Optional.of(new BenefitAmountsResponse(benefitAmounts)));
    }

    private void printGifts(Reservation reservation) {
        Map<Menu, Integer> gifts = promotionService.receiveGifts(reservation);

        List<MenuQuantity> menuQuantities = new ArrayList<>();
        for (Menu menu : gifts.keySet()) {
            menuQuantities.add(new MenuQuantity(menu, gifts.get(menu)));
        }

        if (menuQuantities.isEmpty()) {
            outputView.printGifts(Optional.empty());
            return;
        }

        outputView.printGifts(Optional.of(MenuQuantitiesResponse.from(menuQuantities)));
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
