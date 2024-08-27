package cn.ChengZhiYa.MHDFBot.api.enums.request;

public enum RequestSubType {
    ADD,
    INVITE,
    OTHER;

    public static RequestSubType getSubType(String subType) {
        return subType != null ? switch (subType) {
            case "add" -> ADD;
            case "invite" -> INVITE;
            default -> OTHER;
        } : OTHER;
    }
}
