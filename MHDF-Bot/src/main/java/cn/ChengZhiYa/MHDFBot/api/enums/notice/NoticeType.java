package cn.ChengZhiYa.MHDFBot.api.enums.notice;


public enum NoticeType {
    GROUP_UPLOAD,
    GROUP_ADMIN,
    GROUP_DECREASE,
    GROUP_INCREASE,
    GROUP_BAN,
    FRIEND_ADD,
    GROUP_RECALL,
    FRIEND_RECALL,
    NOTIFY;

    public static NoticeType getType(String type) {
        return type != null ? switch (type) {
            case "group_upload" -> GROUP_UPLOAD;
            case "group_admin" -> GROUP_ADMIN;
            case "group_decrease" -> GROUP_DECREASE;
            case "group_increase" -> GROUP_INCREASE;
            case "group_ban" -> GROUP_BAN;
            case "friend_add" -> FRIEND_ADD;
            case "friend_recall" -> FRIEND_RECALL;
            default -> NOTIFY;
        } : NOTIFY;
    }
}
