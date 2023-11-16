package christmas.mock;

import christmas.application.domain.Menu;
import christmas.application.domain.MenuQuantity;
import christmas.application.domain.Money;
import christmas.application.domain.Reservation;
import christmas.application.service.EventPolicy;
import java.util.Optional;

public class TestEventPolicy implements EventPolicy {

    private final Money discountAmount;
    private final Menu giftMenu;
    private final int numGiftMenu;

    public TestEventPolicy(Money discountAmount, Menu giftMenu, int numGiftMenu) {
        this.discountAmount = discountAmount;
        this.giftMenu = giftMenu;
        this.numGiftMenu = numGiftMenu;
    }

    @Override
    public Money calculateDiscountAmount(Reservation reservation) {
        return discountAmount;
    }

    @Override
    public Optional<MenuQuantity> receiveGift(Reservation reservation) {
        return Optional.of(new MenuQuantity(giftMenu, numGiftMenu));
    }
}
