package cn.ChengZhiYa.MHDFBot.api.builder;

import cn.ChengZhiYa.MHDFBot.api.util.MessageUtil;
import cn.ChengZhiYa.MHDFBot.entity.Music;

public final class MessageBuilder {
    private final StringBuilder builder = new StringBuilder();

    public static MessageBuilder builder() {
        return new MessageBuilder();
    }

    public MessageBuilder text(String text) {
        builder.append(text);
        return this;
    }

    public MessageBuilder face(int id) {
        builder.append("[CQ:face,id=").append(id).append("]");
        return this;
    }

    public MessageBuilder at(long qq) {
        builder.append("[CQ:at,qq=").append(qq).append("]");
        return this;
    }

    public MessageBuilder atAll() {
        builder.append("[CQ:at,qq=all]");
        return this;
    }

    public MessageBuilder image(String image) {
        builder.append("[CQ:image,").append(MediaBuilder.builder().file(image).build()).append("]");
        return this;
    }

    public MessageBuilder flashImage(String image) {
        builder.append("[CQ:image,type=flash,").append(MediaBuilder.builder().file(image).build()).append("]");
        return this;
    }

    public MessageBuilder video(String video, String cover) {
        builder.append("[CQ:video,").append(MediaBuilder.builder().file(video).cover(cover).build()).append("]");
        return this;
    }

    public MessageBuilder audio(String audio) {
        builder.append("[CQ:record,").append(MediaBuilder.builder().file(audio).build()).append("]");
        return this;
    }

    public MessageBuilder tts(String message) {
        builder.append("[CQ:tts,text=").append(message).append("]");
        return this;
    }

    public MessageBuilder reply(int messageId) {
        builder.append("[CQ:reply,id=").append(messageId).append("]");
        return this;
    }

    public MessageBuilder music(String type, long musicId) {
        builder.append("[CQ:music,type=").append(type).append(",id=").append(musicId).append("]");
        return this;
    }

    public MessageBuilder customMusic(Music music) {
        builder.append("[CQ:music,type=custom,url=").append(MessageUtil.escape(music.getUrl())).append(",title=").append(MessageUtil.escape(music.getTitle())).append(",content=")
                .append(MessageUtil.escape(music.getContent())).append(",image=").append(MessageUtil.escape(music.getImage())).append(",audio=").append(MessageUtil.escape(music.getAudio())).append("]");
        return this;
    }

    public MessageBuilder forward(String forwardId) {
        builder.append("[CQ:forward,id=").append(forwardId).append("]");
        return this;
    }

    public String build() {
        return builder.toString();
    }
}
