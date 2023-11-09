package christmas.controller;

import christmas.controller.dto.MenuOrdersRequest;
import christmas.controller.dto.ReservationDateCreateRequest;
import christmas.domain.MenuOrder;
import christmas.domain.MenuOrders;
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

            List<MenuOrder> menuOrders = menuOrdersRequest.getMenuOrderRequests()
                    .stream()
                    .map(request -> new MenuOrder(request.getMenuName(), request.getOrderCount()))
                    .toList();

            return new MenuOrders(menuOrders);
        });
    }
}
