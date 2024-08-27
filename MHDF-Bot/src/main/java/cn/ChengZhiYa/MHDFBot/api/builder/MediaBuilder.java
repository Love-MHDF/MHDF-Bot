package cn.ChengZhiYa.MHDFBot.api.builder;

import cn.ChengZhiYa.MHDFBot.api.util.MessageUtil;

public final class MediaBuilder {
    private String file;
    private Boolean cache;
    private Boolean proxy;
    private Integer timeout;
    private String cover;
    private String magic;

    public static MediaBuilder builder() {
        return new MediaBuilder();
    }

    public MediaBuilder file(String file) {
        this.file = file;
        return this;
    }

    public MediaBuilder cache(boolean cache) {
        this.cache = cache;
        return this;
    }

    public MediaBuilder proxy(boolean proxy) {
        this.proxy = proxy;
        return this;
    }

    public MediaBuilder timeout(int timeout) {
        this.timeout = timeout;
        return this;
    }

    public MediaBuilder cover(String cover) {
        this.cover = cover;
        return this;
    }

    public MediaBuilder magic(String magic) {
        this.magic = magic;
        return this;
    }

    public String build() {
        StringBuilder builder = new StringBuilder();
        builder.append("file=").append(MessageUtil.escape(this.file));
        if (this.cache != null) {
            builder.append(",cache=").append(this.cache);
        }
        if (this.proxy != null) {
            builder.append(",proxy=").append(this.proxy);
        }
        if (this.timeout != null) {
            builder.append(",timeout=").append(this.timeout);
        }
        if (this.magic != null) {
            builder.append(",magic=").append(this.magic);
        }
        return builder.toString();
    }
}
