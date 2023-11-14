package christmas.domain;

public class MenuQuantityCreate {

    private final String menuName;
    private final int orderCount;

    public MenuQuantityCreate(String menuName, int orderCount) {
        this.menuName = menuName;
        this.orderCount = orderCount;
    }

    public String getMenuName() {
        return menuName;
    }

    public int getOrderCount() {
        return orderCount;
    }
}
