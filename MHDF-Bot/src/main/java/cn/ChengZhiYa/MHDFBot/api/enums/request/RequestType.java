package cn.ChengZhiYa.MHDFBot.api.enums.request;

public enum RequestType {
    GROUP,
    FRIEND,
    OTHER;

    public static RequestType getType(String type) {
        return type != null ? switch (type) {
            case "group" -> GROUP;
            case "friend" -> FRIEND;
            default -> OTHER;
        } : OTHER;
    }
}
