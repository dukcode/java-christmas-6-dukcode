package christmas.controller.dto.response;

import christmas.domain.Badge;

public class BadgeAwardResponse {

    private final Badge badgeAward;

    public BadgeAwardResponse(Badge badgeAward) {
        this.badgeAward = badgeAward;
    }

    @Override
    public String toString() {
        if (badgeAward.equals(Badge.NONE)) {
            return "없음";
        }

        return badgeAward.toString();
    }
}
