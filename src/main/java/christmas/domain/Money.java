package christmas.domain;

import java.text.DecimalFormat;
import java.util.Objects;

public class Money {

    public static final String CURRENCY_UNIT = "원";

    public static final Money ZERO = Money.of(0L);
    private static final DecimalFormat decimalFormatter = new DecimalFormat("###,###");
    private final long amount;

    private Money(long amount) {
        this.amount = amount;
    }

    public static Money of(long amount) {
        return new Money(amount);
    }

    public Money add(Money money) {
        return new Money(amount + money.amount);
    }

    public Money multiply(double factor) {
        double newAmount = this.amount * factor;
        long roundedAmount = Math.round(newAmount);
        return Money.of(roundedAmount);
    }

    @Override
    public String toString() {
        return decimalFormatter.format(amount) + CURRENCY_UNIT;
    }

    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other == null || getClass() != other.getClass()) {
            return false;
        }
        Money money = (Money) other;
        return amount == money.amount;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount);
    }

    public boolean isGreaterThanOrEqual(Money money) {
        return this.amount >= money.amount;
    }
}
