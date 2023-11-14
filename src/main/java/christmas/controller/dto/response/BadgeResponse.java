package christmas.controller.dto.response;

import christmas.domain.Badge;

public class BadgeResponse {

    private final String badgeName;

    private BadgeResponse(String badgeName) {
        this.badgeName = badgeName;
    }

    public static BadgeResponse from(Badge badge) {
        return new BadgeResponse(badge.getName());
    }

    @Override
    public String toString() {
        return badgeName;
    }
}
