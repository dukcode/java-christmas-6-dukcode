package christmas.controller.dto.request;

import christmas.controller.dto.exception.ExceptionMessage;
import christmas.controller.dto.validator.NumberFormatValidator;
import java.time.DateTimeException;
import java.time.LocalDate;

public class ReservationDateCreateRequest {

    private final LocalDate reservationDate;

    public ReservationDateCreateRequest(String year, String month, String dayOfMonth) {
        NumberFormatValidator.validate(dayOfMonth, ExceptionMessage.INVALID_DATE_FORMAT);
        NumberFormatValidator.validate(dayOfMonth, ExceptionMessage.INVALID_DATE_FORMAT);
        NumberFormatValidator.validate(dayOfMonth, ExceptionMessage.INVALID_DATE_FORMAT);

        validateDateFormat(year, month, dayOfMonth);

        this.reservationDate = LocalDate.of(
                Integer.parseInt(year),
                Integer.parseInt(month),
                Integer.parseInt(dayOfMonth));
    }

    private static void validateDateFormat(String year, String month, String dayOfMonth) {
        try {
            LocalDate.of(Integer.parseInt(year),
                    Integer.parseInt(month),
                    Integer.parseInt(dayOfMonth));
        } catch (DateTimeException dateTimeException) {
            throw new IllegalArgumentException(ExceptionMessage.INVALID_DATE_FORMAT);
        }
    }

    public LocalDate getReservationDate() {
        return reservationDate;
    }

}
