package cn.ChengZhiYa.MHDFBot.api.enums;

import lombok.Getter;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Getter
public enum Color {
    BLACK('0', new java.awt.Color(0, 0, 0)),
    DARK_BLUE('1', new java.awt.Color(0, 0, 170)),
    DARK_GREEN('2', new java.awt.Color(0, 255, 0)),
    DARK_AQUA('3', new java.awt.Color(0, 170, 170)),
    DARK_RED('4', new java.awt.Color(170, 0, 0)),
    DARK_PURPLE('5', new java.awt.Color(170, 0, 170)),
    GOLD('6', new java.awt.Color(255, 170, 0)),
    GRAY('7', new java.awt.Color(170, 170, 170)),
    DARK_GRAY('8', new java.awt.Color(85, 85, 85)),
    BLUE('9', new java.awt.Color(85, 85, 255)),
    GREEN('a', new java.awt.Color(85, 255, 85)),
    AQUA('b', new java.awt.Color(85, 255, 255)),
    RED('c', new java.awt.Color(255, 85, 85)),
    LIGHT_PURPLE('d', new java.awt.Color(255, 85, 255)),
    YELLOW('e', new java.awt.Color(255, 255, 85)),
    WHITE('f', new java.awt.Color(255, 255, 255));

    private final char code;
    private final java.awt.Color color;

    Color(char code, java.awt.Color color) {
        this.code = code;
        this.color = color;
    }

    public static String rgb(int r, int g, int b) {
        return String.format("\u001B[38;2;%d;%d;%dm", r, g, b);
    }

    public static String translateHexCodes(String message) {
        final String hexPattern = "#([A-Fa-f0-9]{6}|[A-Fa-f0-9]{8})";
        Matcher matcher = Pattern.compile(hexPattern).matcher(message);
        StringBuilder sb = new StringBuilder(message.length());
        while (matcher.find()) {
            String hex = matcher.group(1);
            java.awt.Color color = java.awt.Color.decode("#" + hex);
            matcher.appendReplacement(sb, rgb(color.getRed(), color.getGreen(), color.getBlue()));
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String colorMessage(String message) {
        message = translateHexCodes(message);
        for (Color color : Color.values()) {
            message = message
                    .replace("&r", "\u001B[0m")
                    .replace("&" + color.getCode(), rgb(color.getColor().getRed(), color.getColor().getGreen(), color.getColor().getBlue()));
        }
        return message;
    }
}
