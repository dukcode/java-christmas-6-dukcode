package christmas.mock;

import christmas.domain.Menu;
import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.service.event.EventPolicy;
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
