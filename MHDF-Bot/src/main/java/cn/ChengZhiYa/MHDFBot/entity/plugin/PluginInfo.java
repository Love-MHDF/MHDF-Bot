package cn.ChengZhiYa.MHDFBot.entity.plugin;

import lombok.Data;

import java.util.List;

@Data
public final class PluginInfo {
    String name;
    String version;
    List<String> author;
    String main;

    public PluginInfo(String name, String version, List<String> author, String main) {
        this.name = name;
        this.version = version;
        this.author = author;
        this.main = main;
    }
}