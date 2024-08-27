package cn.ChengZhiYa.MHDFBot.entity.group;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

@Data
public final class File {
    String id;
    String name;
    Long size;
    Long busId;

    public File(JSONObject fileData) {
        this.id = fileData.getString("id");
        this.name = fileData.getString("nickName");
        this.size = fileData.getLong("size");
        this.busId = fileData.getLong("busid");
    }
}
