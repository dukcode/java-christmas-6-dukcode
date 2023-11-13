package christmas.controller;

import christmas.controller.dto.request.OrderCreateRequest;
import christmas.controller.dto.request.ReservationDateCreateRequest;
import christmas.controller.dto.response.BadgeResponse;
import christmas.controller.dto.response.BenefitAmountsResponse;
import christmas.controller.dto.response.ChargeResponse;
import christmas.controller.dto.response.DiscountAmountResponse;
import christmas.controller.dto.response.MenuQuantitiesResponse;
import christmas.controller.dto.response.ReservationDateResponse;
import christmas.domain.Badge;
import christmas.domain.Menu;
import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.Order;
import christmas.domain.Reservation;
import christmas.domain.ReservationDate;
import christmas.service.PromotionService;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        Order order = inputMenuOrders();

        Reservation reservation = new Reservation(order, reservationDate);

        printResult(reservation);
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
        Badge badge = promotionService.recieveBadge(reservation);
        outputView.printBadge(new BadgeResponse(badge));
    }

    private void printChargeAfterDiscount(Reservation reservation) {
        Money chargeAfterDiscount = promotionService.calculateChargeAfterDiscount(reservation);
        outputView.printChargeAfterDiscount(new ChargeResponse(chargeAfterDiscount));
    }

    private void printTotalDiscountAmount(Reservation reservation) {
        Money totalBenefitAmount = promotionService.calculateTotalBenefitAmount(reservation);
        outputView.printTotalBenefitAmount(new DiscountAmountResponse(totalBenefitAmount));
    }

    private void printBenefitAmounts(Reservation reservation) {
        Map<String, Money> benefitAmounts = promotionService.calculateBenefitAmounts(reservation);
        outputView.printDiscountAmounts(new BenefitAmountsResponse(benefitAmounts));
    }

    private void printGifts(Reservation reservation) {
        Map<Menu, Integer> gifts = promotionService.receiveGifts(reservation);

        List<MenuQuantity> menuQuantities = new ArrayList<>();
        for (Menu menu : gifts.keySet()) {
            menuQuantities.add(new MenuQuantity(menu, gifts.get(menu)));
        }
        outputView.printGifts(MenuQuantitiesResponse.from(menuQuantities));
    }

    private void printPreDiscountCharge(Reservation reservation) {
        Money preDiscountCharge = promotionService.calculatePreDiscountCharge(reservation);
        outputView.printPreDiscountCharge(new ChargeResponse(preDiscountCharge));
    }

    private ReservationDate inputReservationDate() {
        return (ReservationDate) exceptionHandler.handle(inputView, outputView, (inputView) -> {
            ReservationDateCreateRequest reservationDateCreateRequest = inputView.inputReservationDate();
            return new ReservationDate(reservationDateCreateRequest.getReservationDate());
        });
    }

    private Order inputMenuOrders() {
        return (Order) exceptionHandler.handle(inputView, outputView, (inputView) -> {
            OrderCreateRequest orderCreateRequest = inputView.inputMenuOrderRequest();

            List<MenuQuantity> menuQuantities = orderCreateRequest.getMenuOrderRequests()
                    .stream()
                    .map(request -> new MenuQuantity(request.getMenuName(), request.getOrderCount()))
                    .toList();

            return new Order(menuQuantities);
        });
    }
}
