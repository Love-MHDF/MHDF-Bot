package cn.ChengZhiYa.MHDFBot.api.enums.minecraft;

public enum ServerType {
    BUNGEE,
    BUKKIT,
    OTHER;

    public static ServerType getSubType(String subType) {
        return subType != null ? switch (subType) {
            case "bungee" -> BUNGEE;
            case "bukkit" -> BUKKIT;
            default -> OTHER;
        } : OTHER;
    }
}
