package cn.ChengZhiYa.MHDFBot.util;

import cn.ChengZhiYa.MHDFBot.api.enums.Color;

public final class LogUtil {
    public static void colorLog(String message, Object... vars) {
        for (Object var : vars) {
            message = message.replaceFirst("\\{}", var.toString());
        }
        System.out.println(Color.colorMessage(message + "\u001B[0m"));
    }
}
