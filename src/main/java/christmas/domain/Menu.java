package christmas.domain;

public enum Menu {

    // APPETIZER
    MUSHROOM_SOUP("양송이수프", Money.of(6000L), MenuType.APPETIZER),
    TAPAS("타파스", Money.of(5500L), MenuType.APPETIZER),
    CAESAR_SALAD("시저샐러드", Money.of(8000L), MenuType.APPETIZER),

    // MAIN_DISH
    T_BONE_STEAK("티본스테이크", Money.of(55000L), MenuType.MAIN_DISH),
    BBQ_RIB("바비큐립", Money.of(54000L), MenuType.MAIN_DISH),
    SEAFOOD_PASTA("해산물파스타", Money.of(35000L), MenuType.MAIN_DISH),
    CHRISTMAS_PASTA("크리스마스파스타", Money.of(25000L), MenuType.MAIN_DISH),

    // DESSERT
    CHOCOLATE_CAKE("초코케이크", Money.of(15000L), MenuType.DESSERT),
    ICE_CREAM("아이스크림", Money.of(5000L), MenuType.DESSERT),

    // BEVERAGE
    ZERO_COKE("제로콜라", Money.of(3000L), MenuType.BEVERAGE),
    RED_WINE("레드와인", Money.of(60000L), MenuType.BEVERAGE),
    CHAMPAGNE("샴페인", Money.of(25000L), MenuType.BEVERAGE),

    // NONE
    NONE("NONE", Money.ZERO, MenuType.NONE);


    private final String name;
    private final Money cost;
    private final MenuType type;

    Menu(String name, Money cost, MenuType type) {
        this.name = name;
        this.cost = cost;
        this.type = type;
    }

    public static boolean contains(String menuName) {
        for (Menu menu : Menu.values()) {
            if (menu.name.equals(menuName)) {
                return true;
            }
        }

        return false;
    }

    public static Menu withName(String menuName) {
        for (Menu menu : Menu.values()) {
            if (menu.name.equals(menuName)) {
                return menu;
            }
        }

        return NONE;
    }

    public String getName() {
        return name;
    }

    public Money getCost() {
        return cost;
    }

    public boolean isBeverage() {
        return this.type.equals(MenuType.BEVERAGE);
    }
}
