package christmas.controller;

import christmas.controller.dto.MenuOrdersRequest;
import christmas.controller.dto.ReservationDateCreateRequest;

public interface InputView {

    ReservationDateCreateRequest inputReservationDate();

    MenuOrdersRequest inputMenuOrderRequest();
}
