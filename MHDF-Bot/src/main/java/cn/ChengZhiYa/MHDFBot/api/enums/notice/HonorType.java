package cn.ChengZhiYa.MHDFBot.api.enums.notice;

public enum HonorType {
    TALKATIVE,
    PERFORMER,
    EMOTION,
    OTHER;

    public static HonorType getType(String type) {
        return type != null ? switch (type) {
            case "talkative" -> TALKATIVE;
            case "performer" -> PERFORMER;
            case "emotion" -> EMOTION;
            default -> OTHER;
        } : OTHER;
    }
}
