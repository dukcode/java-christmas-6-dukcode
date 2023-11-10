package christmas.domain;

import java.time.LocalDate;
import java.time.YearMonth;

public class EventMonth {

    private final YearMonth eventMonth;

    public EventMonth(YearMonth eventMonth) {
        this.eventMonth = eventMonth;
    }

    public boolean isDateInEventMonth(LocalDate date) {
        YearMonth dateYearMonth = YearMonth.from(date);
        return dateYearMonth.equals(eventMonth);
    }
}
