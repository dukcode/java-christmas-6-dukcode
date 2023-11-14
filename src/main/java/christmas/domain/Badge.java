package christmas.domain;

public class Badge {

    private final String name;
    private final Money minBadgeAwardAmount;

    public Badge(String name, Money minBadgeAwardAmount) {
        this.name = name;
        this.minBadgeAwardAmount = minBadgeAwardAmount;
    }

    public String getName() {
        return name;
    }

    public Money getMinBadgeAwardAmount() {
        return minBadgeAwardAmount;
    }
}
