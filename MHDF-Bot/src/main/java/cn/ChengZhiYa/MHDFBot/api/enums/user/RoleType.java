package cn.ChengZhiYa.MHDFBot.api.enums.user;

public enum RoleType {
    OWNER,
    ADMIN,
    MEMBER;

    public static RoleType getType(String type) {
        return type != null ? switch (type) {
            case "owner" -> OWNER;
            case "admin" -> ADMIN;
            default -> MEMBER;
        } : MEMBER;
    }
}
