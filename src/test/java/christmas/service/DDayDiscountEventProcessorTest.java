package christmas.service;

import static org.assertj.core.api.Assertions.assertThat;

import christmas.domain.Menu;
import christmas.domain.MenuOrders;
import christmas.domain.MenuQuantity;
import christmas.domain.Money;
import christmas.domain.ReservationDate;
import java.time.LocalDate;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;

@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class DDayDiscountEventProcessorTest {

    private Money minimumOrderAmountForEvent;

    private Money baseDiscountAmount;
    private Money perDayDiscountAmount;

    private LocalDate eventStartDate;
    private LocalDate dDay;
    private DDayDiscountEventProcessor dDayDiscountEventProcessor;

    @BeforeEach
    public void init() {
        minimumOrderAmountForEvent = Money.of(10_000L);
        baseDiscountAmount = Money.of(1_000L);
        perDayDiscountAmount = Money.of(100L);
        eventStartDate = LocalDate.of(2023, 12, 1);
        dDay = LocalDate.of(2023, 12, 25);

        dDayDiscountEventProcessor = new DDayDiscountEventProcessor(
                minimumOrderAmountForEvent, baseDiscountAmount, perDayDiscountAmount,
                eventStartDate, dDay
        );
    }

    @Test
    public void 예약일이_디데이면_총_할인_금액은_기본_할인_금액이다() throws Exception {
        // given
        ReservationDate reservationDate = new ReservationDate(dDay);
        MenuOrders menuOrders = new MenuOrders(List.of(new MenuQuantity(Menu.BBQ_RIB.getName(), 1),
                new MenuQuantity(Menu.CAESAR_SALAD.getName(), 2),
                new MenuQuantity(Menu.ZERO_COKE.getName(), 3)));

        // when
        Money discountAmount = dDayDiscountEventProcessor.calculateDiscountAmount(reservationDate, menuOrders);

        // then
        assertThat(discountAmount).isEqualTo(baseDiscountAmount);
    }

    @Test
    public void 기본_할인에_예약일과_시작일의_날짜_차이만큼_하루_당_할인을_곱한_것을_더하면_총_할인_금액이다() throws Exception {
        // given
        long dateDiff = 3L;
        ReservationDate reservationDate = new ReservationDate(eventStartDate.plusDays(dateDiff));
        MenuOrders menuOrders = new MenuOrders(List.of(new MenuQuantity(Menu.BBQ_RIB.getName(), 1),
                new MenuQuantity(Menu.CAESAR_SALAD.getName(), 2),
                new MenuQuantity(Menu.ZERO_COKE.getName(), 3)));

        // when
        Money discountAmount = dDayDiscountEventProcessor.calculateDiscountAmount(reservationDate, menuOrders);

        // then
        assertThat(discountAmount).isEqualTo(baseDiscountAmount.add(perDayDiscountAmount.multiply(dateDiff)));
    }

    @Test
    public void 총_주문_금액이_이벤트_적용_최소_금액_미만이면_할인_금액이_0_원이다() throws Exception {
        // given
        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 13));
        MenuOrders menuOrders = new MenuOrders(List.of(new MenuQuantity(Menu.ICE_CREAM.getName(), 1)));

        // when
        Money discountAmount = dDayDiscountEventProcessor.calculateDiscountAmount(reservationDate, menuOrders);

        // then
        assertThat(discountAmount).isEqualTo(Money.ZERO);
    }

    @Test
    public void 총_주문_금액이_이벤트_적용_최소_금액_이상이면_할인이_적용된다() throws Exception {
        // given
        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 13));
        MenuOrders menuOrders = new MenuOrders(List.of(new MenuQuantity(Menu.ICE_CREAM.getName(), 2)));

        // when
        Money discountAmount = dDayDiscountEventProcessor.calculateDiscountAmount(reservationDate, menuOrders);

        // then
        assertThat(discountAmount).isNotEqualTo(Money.ZERO);
    }

    @Test
    public void 예약일이_이벤트_기간_이전이면_할인_금액이_0_원이다() throws Exception {
        // given
        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 11, 30));
        MenuOrders menuOrders = new MenuOrders(List.of(new MenuQuantity(Menu.BBQ_RIB.getName(), 1),
                new MenuQuantity(Menu.CAESAR_SALAD.getName(), 2),
                new MenuQuantity(Menu.ZERO_COKE.getName(), 3)));

        // when
        Money discountAmount = dDayDiscountEventProcessor.calculateDiscountAmount(reservationDate, menuOrders);

        // then
        assertThat(discountAmount).isEqualTo(Money.ZERO);
    }


    @Test
    public void 예약일이_이벤트_기간_이후면_할인_금액이_0_원이다() throws Exception {
        // given
        ReservationDate reservationDate = new ReservationDate(LocalDate.of(2023, 12, 26));
        MenuOrders menuOrders = new MenuOrders(List.of(new MenuQuantity(Menu.BBQ_RIB.getName(), 1),
                new MenuQuantity(Menu.CAESAR_SALAD.getName(), 2),
                new MenuQuantity(Menu.ZERO_COKE.getName(), 3)));

        // when
        Money discountAmount = dDayDiscountEventProcessor.calculateDiscountAmount(reservationDate, menuOrders);

        // then
        assertThat(discountAmount).isEqualTo(Money.ZERO);
    }
}