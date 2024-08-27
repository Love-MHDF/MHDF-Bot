package cn.ChengZhiYa.MHDFBotBukkitHook.util;

import java.io.File;
import java.io.IOException;

public final class FileUtil {
    public static void createDir(File directory) {
        if (!directory.exists()) {
            directory.mkdirs();
        }
    }

    public static void createDir(String path) {
        File directory = new File(path);
        createDir(directory);
    }

    public static void createFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void createFile(File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}