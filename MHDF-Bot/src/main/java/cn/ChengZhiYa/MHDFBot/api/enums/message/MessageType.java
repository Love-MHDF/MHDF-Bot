package cn.ChengZhiYa.MHDFBot.api.enums.message;

public enum MessageType {
    GROUP,
    PRIVATE,
    OTHER;

    public static MessageType getType(String type) {
        return type != null ? switch (type) {
            case "group" -> GROUP;
            case "private" -> PRIVATE;
            default -> OTHER;
        } : OTHER;
    }
}
