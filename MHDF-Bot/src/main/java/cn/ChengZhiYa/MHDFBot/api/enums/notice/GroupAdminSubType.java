package cn.ChengZhiYa.MHDFBot.api.enums.notice;

public enum GroupAdminSubType {
    SET,
    UNSET,
    OTHER;

    public static GroupAdminSubType getSubType(String subType) {
        return subType != null ? switch (subType) {
            case "set" -> SET;
            case "unset" -> UNSET;
            default -> OTHER;
        } : OTHER;
    }
}
