package cn.chengzhiya.mhdfbot.listener;

import cn.chengzhiya.mhdfbot.util.Util;
import cn.chengzhiya.mhdfbotapi.entity.Marry;
import cn.chengzhiya.mhdfbotapi.entity.PlayerData;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.mikuac.shiro.annotation.GroupMessageHandler;
import com.mikuac.shiro.annotation.common.Shiro;
import com.mikuac.shiro.common.utils.MsgUtils;
import com.mikuac.shiro.core.Bot;
import com.mikuac.shiro.dto.action.common.ActionData;
import com.mikuac.shiro.dto.action.response.GroupMemberInfoResp;
import com.mikuac.shiro.dto.action.response.StrangerInfoResp;
import com.mikuac.shiro.dto.event.message.GroupMessageEvent;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.*;

import static cn.chengzhiya.mhdfbot.server.webSocket.send;
import static cn.chengzhiya.mhdfbot.util.Util.*;
import static cn.chengzhiya.mhdfbotapi.util.DatabaseUtil.*;

@Shiro
@Component
public final class GroupMessage {
    @GroupMessageHandler
    public void onGroupMessage(Bot bot, GroupMessageEvent event) {
        if (!ifContainsBlackWord(event.getMessage())) {
            if (Objects.requireNonNull(Util.getConfig().getStringList("AllowUseBotList")).contains(event.getGroupId().toString()) && event.getMessage().startsWith("#")) {
                MsgUtils Message = MsgUtils
                        .builder()
                        .reply(event.getMessageId());
                if (Util.getConfig().getBoolean("ReplayAt")) {
                    Message.at(event.getUserId());
                }
                Message.text("\n");

                String[] args = event.getMessage().split(" ");
                PlayerData playerData;
                switch (args[0]) {
                    case "#菜单": {
                        Message.img(getConfig().getString("ChengBotSettings.APIUrl") + getConfig().getString("ChengBotSettings.Menu"));
                        break;
                    }
                    case "#运行状态": {
                        Message.img(getConfig().getString("ChengBotSettings.APIUrl") + getConfig().getString("ChengBotSettings.SystemInfo"));
                        break;
                    }
                    case "#服务器状态": {
                        Message.img(getConfig().getString("ChengBotSettings.APIUrl") + getConfig().getString("ChengBotSettings.ServerInfo"));
                        break;
                    }
                    case "#绑定": {
                        playerData = getPlayerData(args[1]);

                        ActionData<StrangerInfoResp> info = bot.getStrangerInfo(event.getUserId(), true);
                        Integer QQLevel = null;

                        if (info != null) {
                            QQLevel = info.getData().getLevel();
                        }
                        int tryTimes = 0;
                        while ((info == null || QQLevel == null || QQLevel == 0) && tryTimes < getConfig().getInt("KickSettings.GetQQLevelMaxTryTimes")) {
                            tryTimes++;
                            info = bot.getStrangerInfo(event.getUserId(), true);
                            QQLevel = info.getData().getLevel();
                        }

                        if (QQLevel != null && QQLevel >= Util.getConfig().getInt("BindSettings.MinQQLevel")) {
                            if (args[1].matches(getConfig().getString("BindSettings.BindNameRegex"))) {
                                if (args[1].length() >= getConfig().getInt("BindSettings.BindNameMinLength")) {
                                    if (args[1].length() < getConfig().getInt("BindSettings.BindNameMaxLength")) {
                                        int inputVerifyCode = Integer.MIN_VALUE;
                                        try {
                                            inputVerifyCode = Integer.parseInt(args[2]);
                                        } catch (Exception ignored) {
                                        }
                                        if (Util.getConfig().getBoolean("BindSettings.Verify") && getVerifyCode(args[1]) != inputVerifyCode) {
                                            Message.text(i18n("Messages.Bind.VerifyCodeError"));
                                            break;
                                        }
                                        if (getPlayerDataList(event.getUserId()).size() < Util.getConfig().getInt("BindSettings.MaxBind")) {
                                            if (playerData == null) {
                                                if (Util.getConfig().getBoolean("BindSettings.Verify")) {
                                                    removeVerifyCode(args[1]);
                                                }
                                                bot.setGroupCard(event.getGroupId(), event.getUserId(), args[1]);
                                                bind(new PlayerData(args[1], event.getUserId()));
                                                {
                                                    JSONObject data = new JSONObject();
                                                    data.put("action", "bind");

                                                    JSONObject params = new JSONObject();
                                                    params.put("playerName", args[1]);

                                                    data.put("params", params);

                                                    send(data.toJSONString());
                                                }
                                                Message.text(i18n("Messages.Bind.BindDone").replaceAll("\\{Player}", args[1]).replaceAll("\\{QQ}", String.valueOf(event.getUserId())));
                                            } else {
                                                Message.text(i18n("Messages.Bind.AlwaysBind").replaceAll("\\{Player}", args[1]).replaceAll("\\{QQ}", String.valueOf(playerData.getQQ())));
                                            }
                                        } else {
                                            Message.text(i18n("Messages.Bind.MaxBind").replaceAll("\\{Size}", String.valueOf(Util.getConfig().getInt("BindSettings.MaxBind"))));
                                        }
                                    } else {
                                        Message.text(i18n("Messages.Bind.BindNameMaxLength").replaceAll("\\{Length}", String.valueOf(Util.getConfig().getInt("BindSettings.BindNameMaxLength"))));
                                    }
                                } else {
                                    Message.text(i18n("Messages.Bind.BindNameMinLength").replaceAll("\\{Length}", String.valueOf(Util.getConfig().getInt("BindSettings.BindNameMinLength"))));
                                }
                            } else {
                                Message.text(i18n("Messages.Bind.BindNameRegex"));
                            }
                        } else {
                            Message.text(i18n("Messages.Bind.MinQQLevel").replaceAll("\\{Size}", String.valueOf(Util.getConfig().getInt("BindSettings.MinQQLevel"))));
                        }
                        break;
                    }
                    case "#解除绑定": {
                        if (getConfig().getBoolean("BindSettings.AllowUnBind")) {
                            playerData = getPlayerData(args[1]);
                            if (ifPlayerDataExist(args[1]) && playerData != null) {
                                unbind(playerData);
                                {
                                    JSONObject data = new JSONObject();
                                    data.put("action", "unBind");

                                    JSONObject params = new JSONObject();
                                    params.put("playerName", args[1]);

                                    data.put("params", params);

                                    send(data.toJSONString());
                                }
                                Message.text(i18n("Messages.UnBind.UnBindDone").replaceAll("\\{Player}", args[1]).replaceAll("\\{QQ}", String.valueOf(event.getUserId())));
                            } else {
                                Message.text(i18n("Messages.UnBind.DontBind").replaceAll("\\{Player}", args[1]).replaceAll("\\{QQ}", String.valueOf(event.getUserId())));
                            }
                        }
                        break;
                    }
                    case "#重置密码": {
                        if (getConfig().getBoolean("LoginSystemDatabaseSettings.isMHDFLogin")) {
                            playerData = getPlayerData(args[1]);
                            if (playerData != null && Objects.equals(playerData.getQQ(), event.getUserId())) {
                                if (ifPlayerDataExist(args[1])) {
                                    Util.getChangePasswordHashMap().put(event.getUserId(), args[1]);
                                    Message.text(i18n("Messages.ChangePassword.ChangeInfo").replaceAll("\\{Player}", args[1]).replaceAll("\\{QQ}", String.valueOf(event.getUserId())));
                                } else {
                                    Message.text(i18n("Messages.ChangePassword.ChangeInfo").replaceAll("\\{Player}", args[1]).replaceAll("\\{QQ}", String.valueOf(event.getUserId())));
                                }
                            } else {
                                Message.text(i18n("Messages.ChangePassword.DontBind").replaceAll("\\{Player}", args[1]).replaceAll("\\{QQ}", String.valueOf(event.getUserId())));
                            }
                        } else {
                            return;
                        }
                        break;
                    }
                    case "#群老婆": {
                        if (getConfig().getBoolean("MarryEnable")) {
                            if (!ifMarryDataExist(event.getUserId())) {
                                List<Long> allowMarryList = getMemberList(bot, event.getGroupId());
                                allowMarryList.removeAll(getMarryList());

                                Random random = new Random();
                                Long MrsQQ = allowMarryList.get(random.nextInt(allowMarryList.size()));

                                marry(new Marry(event.getUserId(), MrsQQ));
                                Message.text(i18n("Messages.Marry.IsMr").replaceAll("\\{AtMrs}", MsgUtils.builder().at(MrsQQ).build()));
                            } else {
                                if (isMr(event.getUserId())) {
                                    Message.text(i18n("Messages.Marry.IsMr").replaceAll("\\{AtMrs}", MsgUtils.builder().at(Objects.requireNonNull(getMarryData(event.getUserId())).getMrsQQ()).build()));
                                } else {
                                    Message.text(i18n("Messages.Marry.IsMrs").replaceAll("\\{AtMr}", MsgUtils.builder().at(Objects.requireNonNull(getMarryData(event.getUserId())).getMrQQ()).build()));
                                }
                            }
                        } else {
                            return;
                        }
                        break;
                    }
                    case "#说": {
                        Message.text(i18n("Messages.Say.SayWait"));
                        bot.sendGroupMsg(event.getGroupId(), Message.build(), false);

                        RestTemplate restTemplate = new RestTemplate();
                        HttpHeaders headers = new HttpHeaders();
                        headers.setAccept(List.of(MediaType.APPLICATION_JSON));
                        headers.setContentType(org.springframework.http.MediaType.APPLICATION_JSON);
                        Map<String, Object> map = new HashMap<>();
                        map.put("data", Arrays.asList(args[2], args[1], 0.5, 0.6, 0.9, 1, "ZH", false, 1, 0.2, null, "Happy", "", 0.7));
                        map.put("event_data", null);
                        map.put("fn_index", 0);
                        HttpEntity<Map<String, Object>> entity = new HttpEntity<>(map, headers);
                        ResponseEntity<String> response = restTemplate.exchange("https://bv2.firefly.matce.cn/run/predict", HttpMethod.POST, entity, String.class);

                        bot.sendGroupMsg(event.getGroupId(), MsgUtils.builder().voice("https://bv2.firefly.matce.cn/file=" + Objects.requireNonNull(JSON.parseObject(response.getBody())).getJSONArray("data").getJSONObject(1).getString("name")).build(), false);
                        return;
                    }
                    case "#移除小号": {
                        if (Util.getConfig().getStringList("AllowUseAdminCommandList").contains(event.getUserId().toString())) {
                            List<Long> kickList = new ArrayList<>();

                            Message.text(i18n("Messages.Kick.KickStart"));
                            bot.sendGroupMsg(event.getGroupId(), Message.build(), false);

                            List<GroupMemberInfoResp> memberInfoRespList = bot.getGroupMemberList(event.getGroupId()).getData();

                            for (GroupMemberInfoResp memberInfo : memberInfoRespList) {
                                int tryTimes = 0;
                                Integer QQLevel = bot.getStrangerInfo(memberInfo.getUserId(), false).getData().getLevel();
                                while ((QQLevel == null || QQLevel == 0) && tryTimes < getConfig().getInt("KickSettings.GetQQLevelMaxTryTimes")) {
                                    tryTimes++;
                                    QQLevel = bot.getStrangerInfo(memberInfo.getUserId(), true).getData().getLevel();
                                }
                                if (QQLevel != null && QQLevel != 0) {
                                    if (QQLevel < Integer.parseInt(args[1])) {
                                        bot.setGroupKick(event.getGroupId(), memberInfo.getUserId(), false);
                                        kickList.add(memberInfo.getUserId());
                                    }
                                }
                            }

                            Message = MsgUtils
                                    .builder()
                                    .reply(event.getMessageId());
                            if (Util.getConfig().getBoolean("ReplayAt")) {
                                Message.at(event.getUserId());
                            }
                            Message.text("\n");
                            Message.text(i18n("Messages.Kick.KickDone").replaceAll("\\{KickList}", kickList.toString()));
                            bot.sendGroupMsg(event.getGroupId(), Message.build(), false);
                        }
                        return;
                    }
                    default:
                        return;
                }
                bot.sendGroupMsg(event.getGroupId(), Message.build(), false);
            }
        }
    }
}
