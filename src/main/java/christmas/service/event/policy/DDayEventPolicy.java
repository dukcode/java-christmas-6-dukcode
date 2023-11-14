package christmas.service.event.policy;

import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.Reservation;
import christmas.service.event.EventPolicy;
import java.time.LocalDate;
import java.util.Optional;

public class DDayEventPolicy implements EventPolicy {

    private final LocalDate startDate;
    private final LocalDate dDay;

    private final Money baseDiscountAmount;
    private final Money perDayDiscountAmount;

    public DDayEventPolicy(LocalDate startDate, LocalDate dDay, Money baseDiscountAmount,
                           Money perDayDiscountAmount) {
        this.startDate = startDate;
        this.dDay = dDay;
        this.baseDiscountAmount = baseDiscountAmount;
        this.perDayDiscountAmount = perDayDiscountAmount;
    }

    @Override
    public Money calculateDiscountAmount(Reservation reservation) {
        if (!reservation.isInRange(startDate, dDay)) {
            return Money.ZERO;
        }

        long daysDifference = reservation.daysAfter(startDate);
        return baseDiscountAmount.add(perDayDiscountAmount.multiply(daysDifference));
    }

    @Override
    public Optional<MenuQuantity> receiveGift(Reservation reservation) {
        return Optional.empty();
    }

}
