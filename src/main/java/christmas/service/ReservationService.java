package christmas.service;

import christmas.domain.Menu;
import christmas.domain.MenuQuantity;
import christmas.domain.MenuQuantityCreate;
import christmas.domain.Order;
import christmas.domain.OrderCreate;
import christmas.domain.Reservation;
import christmas.domain.ReservationDate;
import christmas.domain.exception.ExceptionMessage;
import christmas.repository.MenuRepository;
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
