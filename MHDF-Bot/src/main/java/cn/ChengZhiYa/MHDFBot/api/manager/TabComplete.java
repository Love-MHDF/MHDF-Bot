package cn.ChengZhiYa.MHDFBot.api.manager;

import java.util.List;

public interface TabComplete {
    List<String> onTabComplete(String command, String[] args);
}
