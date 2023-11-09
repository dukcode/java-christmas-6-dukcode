package christmas.service;

import christmas.domain.MenuOrders;
import christmas.domain.Money;

public class PromotionService {

    public Money calculatePreDiscountCharge(MenuOrders menuOrders) {
        return menuOrders.calculateTotalCost();
    }
}
