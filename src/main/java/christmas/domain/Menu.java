package christmas.domain;

import java.util.Objects;

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

    public boolean isSameMenuType(MenuType menuType) {
        return type.equals(menuType);
    }

    public boolean isSameName(String name) {
        return this.name.equals(name);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Menu menu = (Menu) o;
        return Objects.equals(name, menu.name) &&
                Objects.equals(cost, menu.cost) &&
                type == menu.type;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, cost, type);
    }
}
