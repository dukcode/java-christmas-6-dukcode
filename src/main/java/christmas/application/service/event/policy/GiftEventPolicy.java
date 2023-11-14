package christmas.application.service.event.policy;

import christmas.application.domain.Menu;
import christmas.application.domain.MenuQuantity;
import christmas.application.domain.Money;
import christmas.application.domain.Reservation;
import christmas.application.exception.NotFoundException;
import christmas.application.service.MenuRepository;
import christmas.application.service.EventPolicy;
import java.util.Optional;

public class GiftEventPolicy implements EventPolicy {

    private final Money eventMinOrderAmount;
    private final Menu giftMenu;
    private final int quantity;

    public GiftEventPolicy(MenuRepository menuRepository, Money eventMinOrderAmount, String giftMenu, int quantity) {
        validateGiftMenuExist(menuRepository, giftMenu);

        this.eventMinOrderAmount = eventMinOrderAmount;
        this.giftMenu = menuRepository.findByName(giftMenu).get();
        this.quantity = quantity;
    }

    private void validateGiftMenuExist(MenuRepository menuRepository, String giftMenu) {
        if (!menuRepository.existByName(giftMenu)) {
            throw new NotFoundException("존재하지 않는 메뉴 입니다.");
        }
    }

    @Override
    public Money calculateDiscountAmount(Reservation reservation) {
        return Money.ZERO;
    }

    @Override
    public Optional<MenuQuantity> receiveGift(Reservation reservation) {
        Money totalCost = reservation.calculateTotalCost();

        if (totalCost.isGreaterThanOrEqual(eventMinOrderAmount)) {
            return Optional.of(new MenuQuantity(giftMenu, quantity));
        }

        return Optional.empty();
    }
}
