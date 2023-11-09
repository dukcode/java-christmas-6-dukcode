package christmas.domain;

import christmas.domain.exception.ExceptionMessage;

public class ReservationDate {

    private final int dayOfMonth;

    public ReservationDate(int dayOfMonth) {
        validateDayOfMonth(dayOfMonth);
        this.dayOfMonth = dayOfMonth;
    }

    private void validateDayOfMonth(int dayOfMonth) {
        if (dayOfMonth < December.START_DAY_OF_MONTH || dayOfMonth > December.LAST_DAY_OF_MONTH) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_DATE_RANGE);
        }
    }

    public int getDayOfMonth() {
        return dayOfMonth;
    }
}
