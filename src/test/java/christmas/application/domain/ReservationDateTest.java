package christmas.application.domain;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;

import christmas.application.domain.ReservationDate;
import java.time.LocalDate;
import java.util.Set;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;


@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class ReservationDateTest {

    @Test
    public void LocalDate_로_생성할_수_있다() throws Exception {
        // given
        LocalDate date = LocalDate.of(2023, 12, 13);

        // when
        // then
        assertThatCode(() -> {
            new ReservationDate(date);
        }).doesNotThrowAnyException();
    }


    @Test
    public void 예약일이_범위_안에_있는지_판단할_수_있다() throws Exception {
        // given
        LocalDate date = LocalDate.of(2023, 12, 13);
        ReservationDate reservationDate = new ReservationDate(date);

        // when
        boolean isInRange1 = reservationDate.isInRange(LocalDate.of(2023, 12, 13), (
                LocalDate.of(2023, 12, 14)));
        boolean isInRange2 = reservationDate.isInRange(LocalDate.of(2023, 12, 12), (
                LocalDate.of(2023, 12, 13)));
        boolean isInRange3 = reservationDate.isInRange(LocalDate.of(2023, 12, 11), (
                LocalDate.of(2023, 12, 12)));
        boolean isInRange4 = reservationDate.isInRange(LocalDate.of(2023, 12, 14), (
                LocalDate.of(2023, 12, 15)));

        // then
        assertThat(isInRange1).isEqualTo(true);
        assertThat(isInRange2).isEqualTo(true);
        assertThat(isInRange3).isEqualTo(false);
        assertThat(isInRange4).isEqualTo(false);
    }

    @Test
    public void 예약일이_기준일보다_몇일_뒤인지_계산할_수_있다() throws Exception {
        // given
        LocalDate date = LocalDate.of(2023, 12, 13);
        ReservationDate reservationDate = new ReservationDate(date);

        // when
        long daysDifference1 = reservationDate.daysAfter(LocalDate.of(2023, 12, 12));
        long daysDifference2 = reservationDate.daysAfter(LocalDate.of(2023, 12, 13));
        long daysDifference3 = reservationDate.daysAfter(LocalDate.of(2023, 12, 14));

        // then
        assertThat(daysDifference1).isEqualTo(1L);
        assertThat(daysDifference2).isEqualTo(0L);
        assertThat(daysDifference3).isEqualTo(-1L);
    }

    @Test
    public void 예약일이_주말인지_판단할_수_있다() throws Exception {
        // given
        ReservationDate reservationDate1 = new ReservationDate(LocalDate.of(2023, 12, 8)); // 목요일
        ReservationDate reservationDate2 = new ReservationDate(LocalDate.of(2023, 12, 9)); // 금요일
        ReservationDate reservationDate3 = new ReservationDate(LocalDate.of(2023, 12, 10)); // 일요일

        // when
        // then
        assertThat(reservationDate1.isWeekend()).isEqualTo(true);
        assertThat(reservationDate2.isWeekend()).isEqualTo(true);
        assertThat(reservationDate3.isWeekend()).isEqualTo(false);
    }

    @Test
    public void 예약일이_주어진_날들_중에_존재하는지_판단할_수_있다() throws Exception {
        // given
        LocalDate date = LocalDate.of(2023, 12, 13);
        ReservationDate reservationDate = new ReservationDate(date);

        // when
        // then
        assertThat(reservationDate.isInDates(Set.of(
                LocalDate.of(2023, 12, 13)
        ))).isEqualTo(true);
        assertThat(reservationDate.isInDates(Set.of(
                LocalDate.of(2023, 12, 13),
                LocalDate.of(2023, 12, 14)
        ))).isEqualTo(true);

        assertThat(reservationDate.isInDates(Set.of(
                LocalDate.of(2023, 12, 15)
        ))).isEqualTo(false);
        assertThat(reservationDate.isInDates(Set.of(
                LocalDate.of(2023, 12, 14),
                LocalDate.of(2023, 12, 15)
        ))).isEqualTo(false);
    }
}