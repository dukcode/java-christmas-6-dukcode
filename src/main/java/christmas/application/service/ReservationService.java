package christmas.application.service;

import christmas.application.domain.Menu;
import christmas.application.domain.MenuQuantity;
import christmas.application.domain.MenuQuantityCreate;
import christmas.application.domain.Order;
import christmas.application.domain.OrderCreate;
import christmas.application.domain.Reservation;
import christmas.application.domain.ReservationDate;
import christmas.application.exception.ExceptionMessage;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class ReservationService {
    private final MenuRepository menuRepository;

    public ReservationService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    public Order createOrder(OrderCreate orderCreate) {
        List<MenuQuantity> menuQuantities = new ArrayList<>();
        List<MenuQuantityCreate> menuQuantityCreates = orderCreate.getMenuQuantityCreates();
        for (MenuQuantityCreate menuQuantityCreate : menuQuantityCreates) {
            String menuName = menuQuantityCreate.getMenuName();
            Optional<Menu> menu = menuRepository.findByName(menuName);

            if (menu.isEmpty()) {
                throw new IllegalArgumentException(ExceptionMessage.INVALID_ORDER);
            }
            int orderCount = menuQuantityCreate.getOrderCount();

            menuQuantities.add(new MenuQuantity(menu.get(), orderCount));
        }

        return new Order(menuQuantities);
    }

    public Reservation createReservation(Order order, ReservationDate reservationDate) {
        return new Reservation(order, reservationDate);
    }
}
