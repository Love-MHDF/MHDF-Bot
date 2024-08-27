package cn.ChengZhiYa.MHDFBot.api.enums.user;

public enum SexType {
    MALE,
    FEMALE,
    UNKNOWN;

    public static SexType getType(String type) {
        return type != null ? switch (type) {
            case "male" -> MALE;
            case "female" -> FEMALE;
            default -> UNKNOWN;
        } : UNKNOWN;
    }
}
