package christmas.controller;

import christmas.controller.dto.request.OrderCreateRequest;
import christmas.controller.dto.request.ReservationDateCreateRequest;

public interface InputView {

    ReservationDateCreateRequest inputReservationDate();

    OrderCreateRequest inputMenuOrderRequest();
}
