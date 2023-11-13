package christmas.controller.dto.response;

import christmas.domain.Badge;

public class BadgeResponse {

    private final Badge badge;

    public BadgeResponse(Badge badge) {
        this.badge = badge;
    }

    @Override
    public String toString() {
        if (badge.equals(Badge.NONE)) {
            return "없음";
        }

        return badge.toString();
    }
}
