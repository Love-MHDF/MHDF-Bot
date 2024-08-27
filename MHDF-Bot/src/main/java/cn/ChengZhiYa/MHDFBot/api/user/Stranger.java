package cn.ChengZhiYa.MHDFBot.api.user;

import cn.ChengZhiYa.MHDFBot.api.enums.user.SexType;
import cn.ChengZhiYa.MHDFBot.entity.user.Birthday;
import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

@Data
public abstract class Stranger {
    String qid;
    Long id;
    String nickName;
    String card;
    Birthday birthday;
    SexType sex;
    String country;
    String province;
    String city;
    Long regTime;
    String[] labels;
    int level;

    public Stranger(JSONObject strangerData) {
        this.qid = strangerData.getString("qid");
        this.id = strangerData.getLong("user_id");
        this.nickName = strangerData.getString("nickname");
        this.card = strangerData.getString("longNick");
        this.birthday = new Birthday(
                strangerData.getIntValue("birthday_year"),
                strangerData.getIntValue("birthday_month"),
                strangerData.getIntValue("birthday_day")
        );
        this.sex = SexType.getType(strangerData.getString("sex"));
        this.country = strangerData.getString("country");
        this.province = strangerData.getString("province");
        this.city = strangerData.getString("city");
        this.regTime = strangerData.getLong("regTime");
        this.labels = strangerData.get("labels") != null ? strangerData.getJSONArray("labels").toArray(String.class) : new String[0];
        this.level = strangerData.getIntValue("level");
    }
}
