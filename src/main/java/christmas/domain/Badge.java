package christmas.domain;

public enum Badge {

    SANTA("산타", Money.of(20_000L)),
    TREE("트리", Money.of(10_000L)),
    STAR("별", Money.of(5_000L)),
    NONE("NONE", Money.of(0L));

    private final String name;
    private final Money minBadgeAwardAmount;

    Badge(String name, Money minBadgeAwardAmount) {
        this.name = name;
        this.minBadgeAwardAmount = minBadgeAwardAmount;
    }

    @Override
    public String toString() {
        return this.name;
    }

    public boolean canBeAward(Money orderAmount) {
        return orderAmount.isGreaterThanOrEqual(minBadgeAwardAmount);
    }
}
