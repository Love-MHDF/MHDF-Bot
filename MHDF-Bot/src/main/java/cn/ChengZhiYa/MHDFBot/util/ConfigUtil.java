package cn.ChengZhiYa.MHDFBot.util;

import cn.ChengZhiYa.MHDFBot.entity.YamlConfiguration;
import lombok.Getter;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import static cn.ChengZhiYa.MHDFBot.util.FileUtil.createDir;

public final class ConfigUtil {
    @Getter
    private static YamlConfiguration Config;

    public static void saveDefaultConfig() {
        saveResource("./", "config.yml", "config.yml", false);

        createDir("./plugins");

        reloadConfig();
    }

    public static void reloadConfig() {
        Config = YamlConfiguration.loadConfiguration(new File("config.yml"));
    }

    public static InputStream getResource(String filename) {
        try {
            URL URL2 = ConfigUtil.class.getClassLoader().getResource(filename);
            if (URL2 == null) {
                return null;
            }
            URLConnection Connection = URL2.openConnection();
            Connection.setUseCaches(false);
            return Connection.getInputStream();
        } catch (IOException ex) {
            return null;
        }
    }

    public static void saveResource(String FilePath, String OutFileName, String ResourcePath, boolean Replace) {
        if (ResourcePath.isEmpty()) {
            throw new IllegalArgumentException("ResourcePath cannot be null or empty");
        }
        InputStream in = getResource(ResourcePath = ResourcePath.replace('\\', '/'));
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + ResourcePath + "' cannot be found in " + ResourcePath);
        }
        File OutFile = new File(FilePath, OutFileName);
        try {
            if (!OutFile.exists() || Replace) {
                int Len;
                FileOutputStream out = new FileOutputStream(OutFile);
                byte[] buf = new byte[1024];
                while ((Len = in.read(buf)) > 0) {
                    out.write(buf, 0, Len);
                }
                out.close();
                in.close();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
