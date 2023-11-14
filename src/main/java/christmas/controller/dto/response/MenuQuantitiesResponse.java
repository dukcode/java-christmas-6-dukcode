package christmas.controller.dto.response;

import christmas.application.domain.MenuQuantity;
import java.util.List;
import java.util.StringJoiner;

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
        StringJoiner stringJoiner = new StringJoiner("\n");
        for (MenuQuantityResponse menuQuantityResponse : menuQuantityResponses) {
            stringJoiner.add(menuQuantityResponse.toString());
        }

        return stringJoiner.toString();
    }

}
