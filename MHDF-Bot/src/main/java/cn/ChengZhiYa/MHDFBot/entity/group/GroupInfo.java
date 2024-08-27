package cn.ChengZhiYa.MHDFBot.entity.group;

import com.alibaba.fastjson2.JSONObject;
import lombok.Data;

@Data
public final class GroupInfo {
    Long groupId;
    String groupName;
    int memberCount;
    int maxMemberCount;

    public GroupInfo(JSONObject groupInfoData) {
        this.groupId = groupInfoData.getLong("group_id");
        this.groupName = groupInfoData.getString("group_name");
        this.memberCount = groupInfoData.getIntValue("member_count");
        this.maxMemberCount = groupInfoData.getIntValue("max_member_count");
    }
}
