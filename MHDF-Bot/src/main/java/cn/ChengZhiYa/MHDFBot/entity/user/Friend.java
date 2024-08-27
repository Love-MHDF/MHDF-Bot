package cn.ChengZhiYa.MHDFBot.entity.user;

import cn.ChengZhiYa.MHDFBot.api.enums.user.SexType;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

@Data
public final class Friend {
    Long userId;
    String nickName;
    String remark;
    SexType sex;
    int level;

    public Friend(JSONObject friendData) {
        this.userId = friendData.getLong("user_id");
        this.nickName = friendData.getString("nickname");
        this.remark = friendData.getString("remark");
        this.sex = SexType.getType(friendData.getString("sex"));
        this.level = friendData.getIntValue("level");
    }
}
