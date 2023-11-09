package christmas.controller;

import christmas.controller.dto.request.MenuOrdersRequest;
import christmas.controller.dto.request.ReservationDateCreateRequest;
import christmas.controller.dto.response.DiscountAmountsResponse;
import christmas.controller.dto.response.MenuOrdersResponse;
import christmas.controller.dto.response.MenuQuantityResponse;
import christmas.controller.dto.response.ReservationDateResponse;
import christmas.domain.MenuOrders;
import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.ReservationDate;
import christmas.service.GiftEventService;
import christmas.service.PromotionService;
import java.util.List;

public class PromotionController {
    private final InputView inputView;
    private final OutputView outputView;

    private final ExceptionHandler exceptionHandler;

    private final PromotionService promotionService;
    private final GiftEventService giftEventService;


    public PromotionController(InputView inputView, OutputView outputView, ExceptionHandler exceptionHandler,
                               PromotionService promotionService, GiftEventService giftEventService) {

        this.inputView = inputView;
        this.outputView = outputView;
        this.exceptionHandler = exceptionHandler;
        this.promotionService = promotionService;
        this.giftEventService = giftEventService;
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

        Money preDiscountCharge = printPreDiscountCharge(menuOrders);
        MenuQuantity giftMenu = printGiftEventResult(menuOrders);

        outputView.printDiscountAmounts(new DiscountAmountsResponse(
                Money.ZERO,
                Money.ZERO,
                Money.ZERO,
                giftMenu.calculateCost()
        ));
    }

    private MenuQuantity printGiftEventResult(MenuOrders menuOrders) {
        MenuQuantity giftMenu = giftEventService.applyEvent(menuOrders);
        outputView.printGiftMenu(MenuQuantityResponse.from(giftMenu));
        return giftMenu;
    }

    private Money printPreDiscountCharge(MenuOrders menuOrders) {
        Money preDiscountCharge = promotionService.calculatePreDiscountCharge(menuOrders);
        outputView.printPreDiscountCharge(preDiscountCharge);
        return preDiscountCharge;
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
