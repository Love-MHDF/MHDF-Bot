package cn.ChengZhiYa.MHDFBot.entity;

import lombok.Data;

@Data
public final class Bot {
    long selfId;

    public Bot(long selfQQ) {
        this.selfId = selfQQ;
    }
}
