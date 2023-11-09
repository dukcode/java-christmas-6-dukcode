package christmas.controller;

import christmas.controller.dto.request.MenuOrdersRequest;
import christmas.controller.dto.request.ReservationDateCreateRequest;

public interface InputView {

    ReservationDateCreateRequest inputReservationDate();

    MenuOrdersRequest inputMenuOrderRequest();
}
