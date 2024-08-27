package cn.ChengZhiYa.MHDFBot.api.manager;

public interface CommandExecutor {
    void onCommand(String command, String[] args);
}
