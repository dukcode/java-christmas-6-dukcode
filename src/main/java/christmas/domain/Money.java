package christmas.domain;

public class Money {

    public static final Money ZERO = Money.of(0L);
    private final long amount;

    private Money(long amount) {
        this.amount = amount;
    }

    public static Money of(long amount) {
        return new Money(amount);
    }
}
