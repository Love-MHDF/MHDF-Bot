package cn.ChengZhiYa.MHDFBot.api.enums.notice;

public enum GroupIncreaseSubType {
    APPROVE,
    INVITE,
    OTHER;

    public static GroupIncreaseSubType getSubType(String subType) {
        return subType != null ? switch (subType) {
            case "approve" -> APPROVE;
            case "invite" -> INVITE;
            default -> OTHER;
        } : OTHER;
    }
}
