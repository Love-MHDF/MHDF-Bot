package cn.ChengZhiYa.MHDFBot.api.enums;

import lombok.Getter;

@Getter
public enum PluginStatus {
    Load_Error(0),
    Load_Done(1);

    private final int code;

    PluginStatus(int code) {
        this.code = code;
    }
}
