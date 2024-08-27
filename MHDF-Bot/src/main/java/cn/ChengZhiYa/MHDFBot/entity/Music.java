package cn.ChengZhiYa.MHDFBot.entity;

import lombok.Data;

@Data
public final class Music {
    String url;
    String title;
    String content;
    String image;
    String audio;

    public Music(String url, String title, String content, String image, String audio) {
        this.url = url;
        this.title = title;
        this.content = content;
        this.image = image;
        this.audio = audio;
    }
}
