package cn.ChengZhiYa.MHDFBot.entity.user;

import cn.ChengZhiYa.MHDFBot.api.enums.user.RoleType;
import cn.ChengZhiYa.MHDFBot.api.enums.user.SexType;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

@Data
public final class Sender {
    Long userId;
    String nickName;
    String card;
    String longNick;
    SexType sex;
    Integer age;
    String area;
    String level;
    RoleType role;
    String title;

    public Sender(JSONObject senderData) {
        this.userId = senderData.getLong("user_id");
        this.nickName = senderData.getString("nickname");
        this.card = senderData.getString("card");
        this.longNick = senderData.getString("longNick");
        this.sex = SexType.getType(senderData.getString("sex"));
        this.age = senderData.getIntValue("age");
        this.area = senderData.getString("area");
        this.level = senderData.getString("level");
        this.role = RoleType.getType(senderData.getString("role"));
        this.title = senderData.getString("title");
    }
}
