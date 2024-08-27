package cn.ChengZhiYa.MHDFBot.entity.user;

import cn.ChengZhiYa.MHDFBot.api.enums.user.RoleType;
import cn.ChengZhiYa.MHDFBot.api.user.Stranger;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;

@Getter
public final class Member extends Stranger {
    Long groupId;
    String card;
    RoleType role;
    String title;

    public Member(JSONObject strangerData) {
        super(strangerData);
        this.setLevel(strangerData.getIntValue("qq_level"));
        this.groupId = strangerData.getLong("group_id");
        this.card = strangerData.getString("card");
        this.role = RoleType.getType(strangerData.getString("role"));
        this.title = strangerData.getString("title");
    }
}
