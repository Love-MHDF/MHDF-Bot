package cn.ChengZhiYa.MHDFBot.api.enums.notice;

public enum GroupDecreaseSubType {
    LEAVE,
    KICK,
    KICK_ME,
    OTHER;

    public static GroupDecreaseSubType getSubType(String subType) {
        return subType != null ? switch (subType) {
            case "leave" -> LEAVE;
            case "kick" -> KICK;
            case "kick_me" -> KICK_ME;
            default -> OTHER;
        } : OTHER;
    }
}

