package cn.ChengZhiYa.MHDFBot.api;

import cn.ChengZhiYa.MHDFBot.api.manager.Listener;
import cn.ChengZhiYa.MHDFBot.api.manager.Plugin;
import cn.ChengZhiYa.MHDFBot.entity.YamlConfiguration;
import cn.ChengZhiYa.MHDFBot.entity.plugin.Command;
import cn.ChengZhiYa.MHDFBot.entity.plugin.PluginInfo;
import cn.ChengZhiYa.MHDFBot.util.LogUtil;
import lombok.Data;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Data
public abstract class MHDFBotPlugin implements Plugin {
    private PluginInfo pluginInfo;
    private JarFile pluginJar;
    private YamlConfiguration config;

    public void onEnable() {
    }

    public void onDisable() {
    }

    public void colorLog(String message) {
        LogUtil.colorLog(message);
    }

    public Command getCommand(String command) {
        return MHDFBot.getCommand(command).setPluginInfo(this.pluginInfo);
    }

    public void registerListener(Listener listener) {
        MHDFBot.registerListener(getPluginInfo(), listener);
    }

    public File getDataFolder() {
        return new File("./plugins/" + getPluginInfo().getName());
    }

    public void saveDefaultConfig() {
        if (!getDataFolder().exists()) {
            getDataFolder().mkdir();
        }
        saveResource(getDataFolder().getPath(), "config.yml", "config.yml", false);
    }

    public void reloadConfig() {
        config = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "config.yml"));
    }

    public YamlConfiguration getConfig() {
        if (config == null) {
            reloadConfig();
        }
        return config;
    }

    public InputStream getResource(String fileName) {
        try {
            JarEntry jarEntry = getPluginJar().getJarEntry(fileName);
            if (jarEntry != null) {
                return getPluginJar().getInputStream(jarEntry);
            }
        } catch (IOException ignored) {
        }
        return null;
    }

    public void saveResource(String filePath, String outFileName, String resourcePath, boolean replace) {
        InputStream in = this.getResource(resourcePath.replace('\\', '/'));
        if (in == null) {
            throw new IllegalArgumentException("The embedded resource '" + resourcePath + "' cannot be found in " + resourcePath);
        }
        File OutFile = new File(filePath, outFileName);
        try {
            if (!OutFile.exists() || replace) {
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
