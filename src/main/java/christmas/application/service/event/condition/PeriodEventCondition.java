package christmas.application.service.event.condition;

import christmas.application.domain.Reservation;
import christmas.application.service.EventCondition;
import java.time.LocalDate;
import java.time.YearMonth;

public class PeriodEventCondition implements EventCondition {

    private final LocalDate startDate;
    private final LocalDate endDate;

    private PeriodEventCondition(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public static PeriodEventCondition ofRange(LocalDate startDate, LocalDate endDate) {
        return new PeriodEventCondition(startDate, endDate);
    }

    public static PeriodEventCondition wholeMonth(YearMonth yearMonth) {
        LocalDate startDate = yearMonth.atDay(1);
        LocalDate endDate = yearMonth.atEndOfMonth();
        return new PeriodEventCondition(startDate, endDate);
    }

    @Override
    public boolean isSatisfiedBy(Reservation reservation) {
        return reservation.isInRange(startDate, endDate);
    }

}
