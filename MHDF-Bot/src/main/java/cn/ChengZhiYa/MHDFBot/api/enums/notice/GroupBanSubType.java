package cn.ChengZhiYa.MHDFBot.api.enums.notice;

public enum GroupBanSubType {
    BAN,
    LIFT_BAN,
    OTHER;

    public static GroupBanSubType getSubType(String subType) {
        return subType != null ? switch (subType) {
            case "ban" -> BAN;
            case "lift_ban" -> LIFT_BAN;
            default -> OTHER;
        } : OTHER;
    }
}
