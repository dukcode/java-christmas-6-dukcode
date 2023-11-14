package christmas.domain;

public class Menu {

    private final String name;
    private final Money cost;
    private final MenuType type;

    public Menu(String name, Money cost, MenuType type) {
        this.name = name;
        this.cost = cost;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public Money getCost() {
        return cost;
    }

    public boolean isMenuTypeEquals(MenuType menuType) {
        return type.equals(menuType);
    }

    public boolean isNameEquals(String name) {
        return this.name.equals(name);
    }
}
