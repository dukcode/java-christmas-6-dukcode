package christmas.controller;

import christmas.controller.dto.request.MenuOrdersRequest;
import christmas.controller.dto.request.ReservationDateCreateRequest;
import christmas.controller.dto.response.MenuOrdersResponse;
import christmas.controller.dto.response.MenuQuantityResponse;
import christmas.controller.dto.response.ReservationDateResponse;
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


    public PromotionController(InputView inputView, OutputView outputView, ExceptionHandler exceptionHandler,
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

        outputView.printResultTitle(ReservationDateResponse.from(reservationDate));
        outputView.printMenuOrders(MenuOrdersResponse.from(menuOrders));

        printPreDiscountCharge(menuOrders);
        printGiftEventResult(menuOrders);
    }

    private void printGiftEventResult(MenuOrders menuOrders) {
        MenuQuantity giftMenu = promotionService.applyGiftEvent(menuOrders);
        outputView.printGiftMenu(MenuQuantityResponse.from(giftMenu));
    }

    private void printPreDiscountCharge(MenuOrders menuOrders) {
        Money preDiscountCharge = promotionService.calculatePreDiscountCharge(menuOrders);
        outputView.printPreDiscountCharge(preDiscountCharge);
    }

    private ReservationDate inputReservationDate() {
        return (ReservationDate) exceptionHandler.handle(inputView, outputView, (inputView) -> {
            ReservationDateCreateRequest reservationDateCreateRequest = inputView.inputReservationDate();
            return new ReservationDate(reservationDateCreateRequest.getDayOfMonth());
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
