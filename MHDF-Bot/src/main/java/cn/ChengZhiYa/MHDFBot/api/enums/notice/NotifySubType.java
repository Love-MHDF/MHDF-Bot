package cn.ChengZhiYa.MHDFBot.api.enums.notice;


public enum NotifySubType {
    POKE,
    LUCKY_KING,
    HONOR,
    OTHER;

    public static NotifySubType getSubType(String subType) {
        return subType != null ? switch (subType) {
            case "poke" -> POKE;
            case "lucky_king" -> LUCKY_KING;
            case "honor" -> HONOR;
            default -> OTHER;
        } : OTHER;
    }
}
