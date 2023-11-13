package christmas.controller.dto.response;

import christmas.domain.MenuQuantity;
import java.util.List;

public class MenuQuantitiesResponse {

    private final List<MenuQuantityResponse> menuQuantityResponses;

    private MenuQuantitiesResponse(List<MenuQuantityResponse> menuQuantityResponses) {
        this.menuQuantityResponses = menuQuantityResponses;
    }

    public static MenuQuantitiesResponse from(List<MenuQuantity> menuQuantities) {
        return new MenuQuantitiesResponse(
                menuQuantities.stream()
                        .map(MenuQuantityResponse::from)
                        .toList());
    }

    @Override
    public String toString() {
        if (menuQuantityResponses.isEmpty()) {
            return "없음\n";
        }

        return createMenuQuantiesString();
    }

    private String createMenuQuantiesString() {
        StringBuilder sb = new StringBuilder();
        for (MenuQuantityResponse menuQuantityResponse : menuQuantityResponses) {
            sb.append(menuQuantityResponse.toString());
            sb.append("\n");
        }

        return sb.toString();
    }
}
