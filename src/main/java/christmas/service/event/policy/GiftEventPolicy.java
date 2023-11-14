package christmas.service.event.policy;

import christmas.domain.Menu;
import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.domain.exception.NotFoundException;
import christmas.repository.MenuRepository;
import christmas.service.event.EventPolicy;
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
