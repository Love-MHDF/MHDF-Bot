package cn.ChengZhiYa.MHDFBot.api;

import cn.ChengZhiYa.MHDFBot.api.enums.message.MessageType;
import cn.ChengZhiYa.MHDFBot.api.enums.request.RequestSubType;
import cn.ChengZhiYa.MHDFBot.api.event.MessageEvent;
import cn.ChengZhiYa.MHDFBot.api.manager.Listener;
import cn.ChengZhiYa.MHDFBot.client.WebSocket;
import cn.ChengZhiYa.MHDFBot.entity.Bot;
import cn.ChengZhiYa.MHDFBot.entity.group.GroupInfo;
import cn.ChengZhiYa.MHDFBot.entity.plugin.Command;
import cn.ChengZhiYa.MHDFBot.entity.plugin.PluginInfo;
import cn.ChengZhiYa.MHDFBot.entity.user.Friend;
import cn.ChengZhiYa.MHDFBot.entity.user.Member;
import cn.ChengZhiYa.MHDFBot.entity.user.Stranger;
import cn.ChengZhiYa.MHDFBot.event.message.GroupMessageEvent;
import cn.ChengZhiYa.MHDFBot.util.CommandUtil;
import cn.ChengZhiYa.MHDFBot.util.ConfigUtil;
import cn.ChengZhiYa.MHDFBot.util.ListenerUtil;
import cn.ChengZhiYa.MHDFBot.util.PluginUtil;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public final class MHDFBot {
    @Getter
    @Setter
    private static Bot bot;

    public static Scheduler getScheduler() {
        return new Scheduler();
    }

    public static void sendPrivateMessage(Long targetId, String message) {
        sendMessage(MessageType.PRIVATE, targetId, message);
    }

    public static void sendGroupMessage(Long targetId, String message) {
        sendMessage(MessageType.GROUP, targetId, message);
    }

    public static void sendMessage(MessageEvent event, String message) {
        if (event instanceof GroupMessageEvent) {
            sendMessage(MessageType.GROUP, ((GroupMessageEvent) event).getGroupId(), message);
        } else {
            sendMessage(MessageType.PRIVATE, event.getSender().getUserId(), message);
        }
    }

    public static void sendMessage(MessageType type, Long targetId, String message) {
        JSONObject data = new JSONObject();
        data.put("action", "send_msg");
        JSONObject params = new JSONObject();
        params.put("message", message);
        switch (type) {
            case GROUP -> {
                params.put("message_type", "group");
                params.put("group_id", targetId);
            }
            case PRIVATE -> {
                params.put("message_type", "private");
                params.put("user_id", targetId);
            }
        }
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    public static void recallMessage(Long messageId) {
        JSONObject data = new JSONObject();
        data.put("action", "delete_msg");
        JSONObject params = new JSONObject();
        params.put("message_id", messageId);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    public static void sendLike(Long targetId) {
        JSONObject data = new JSONObject();
        data.put("action", "send_like");
        JSONObject params = new JSONObject();
        params.put("user_id", targetId);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    public static void sendLike(Long targetId, short times) {
        JSONObject data = new JSONObject();
        data.put("action", "send_like");
        JSONObject params = new JSONObject();
        params.put("user_id", targetId);
        params.put("times", times);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    public static void groupKick(Long groupId, Long targetId) {
        JSONObject data = new JSONObject();
        data.put("action", "set_group_kick");
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("user_id", targetId);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    public static void groupKick(Long groupId, Long targetId, boolean rejectRequest) {
        JSONObject data = new JSONObject();
        data.put("action", "set_group_kick");
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("user_id", targetId);
        params.put("reject_add_request", rejectRequest);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    public static void groupMute(Long groupId, Long targetId, Long duration) {
        JSONObject data = new JSONObject();
        data.put("action", "set_group_ban");
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("user_id", targetId);
        params.put("duration", duration);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    public static void leaveGroup(Long groupId, boolean dismiss) {
        JSONObject data = new JSONObject();
        data.put("action", "set_group_leave");
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("is_dismiss", dismiss);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    public static void setGroupAllMute(Long groupId, boolean enable) {
        JSONObject data = new JSONObject();
        data.put("action", "set_group_whole_ban");
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("enable", enable);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    public static void setGroupAdmin(Long groupId, Long targetId, boolean enable) {
        JSONObject data = new JSONObject();
        data.put("action", "set_group_admin");
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("user_id", targetId);
        params.put("enable", enable);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    public static void setGroupCard(Long groupId, Long targetId, String newName) {
        JSONObject data = new JSONObject();
        data.put("action", "set_group_card");
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("user_id", targetId);
        params.put("card", newName);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    public static void setGroupName(Long groupId, String newName) {
        JSONObject data = new JSONObject();
        data.put("action", "set_group_name");
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("group_name", newName);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    public static void setGroupTitle(Long groupId, Long targetId, String newTitle) {
        JSONObject data = new JSONObject();
        data.put("action", "set_group_special_title");
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("user_id", targetId);
        params.put("special_title", newTitle);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    public static void handlingFriendRequest(String flag, boolean accept) {
        JSONObject data = new JSONObject();
        data.put("action", "set_friend_add_request");
        JSONObject params = new JSONObject();
        params.put("flag", flag);
        params.put("approve", accept);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    public static void handlingGroupRequest(String flag, RequestSubType subType, boolean accept) {
        JSONObject data = new JSONObject();
        data.put("action", "set_group_add_request");
        JSONObject params = new JSONObject();
        params.put("flag", flag);
        switch (subType) {
            case ADD -> params.put("sub_type", "add");
            case INVITE -> params.put("sub_type", "invite");
        }
        params.put("approve", accept);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    private static void updateStrangerInfo(Long targetId) {
        JSONObject data = new JSONObject();
        data.put("action", "get_stranger_info");
        data.put("echo", "get_stranger_info|" + targetId);
        JSONObject params = new JSONObject();
        params.put("user_id", targetId);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    private static void updateFriendList() {
        JSONObject data = new JSONObject();
        data.put("action", "get_friend_list");
        data.put("echo", "get_friend_list");
        WebSocket.send(data.toJSONString());
    }

    private static void updateGroupInfo(Long targetId) {
        JSONObject data = new JSONObject();
        data.put("action", "get_group_info");
        data.put("echo", "get_group_info|" + targetId);
        JSONObject params = new JSONObject();
        params.put("group_id", targetId);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    private static void updateGroupList() {
        JSONObject data = new JSONObject();
        data.put("action", "get_group_list");
        data.put("echo", "get_group_list");
        WebSocket.send(data.toJSONString());
    }

    private static void updateGroupMemberInfo(Long groupId, Long targetId) {
        JSONObject data = new JSONObject();
        data.put("action", "get_group_member_info");
        data.put("echo", "get_group_member_info|" + groupId + "|" + targetId);
        JSONObject params = new JSONObject();
        params.put("group_id", groupId);
        params.put("user_id", targetId);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    private static void updateGroupMemberList(Long targetId) {
        JSONObject data = new JSONObject();
        data.put("action", "get_group_member_list");
        data.put("echo", "get_group_member_list|" + targetId);
        JSONObject params = new JSONObject();
        params.put("group_id", targetId);
        data.put("params", params);
        WebSocket.send(data.toJSONString());
    }

    public static Stranger getStrangerInfo(Long targetId) {
        JSONObject data;
        if (!ConfigUtil.getConfig().getBoolean("OneBotSettings.HttpSettings.Enable")) {
            updateStrangerInfo(targetId);
            data = WebSocket.getEchoHashMap().get("get_stranger_info|" + targetId);
        } else {
            try {
                URL url = new URL(ConfigUtil.getConfig().getString("OneBotSettings.HttpSettings.Host") + "get_stranger_info?user_id=" + targetId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String response = in.readLine();
                    data = JSON.parseObject(response);
                } finally {
                    conn.disconnect();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new Stranger(data.getJSONObject("data"));
    }

    public static List<Friend> getFriendList() {
        JSONObject data;
        if (!ConfigUtil.getConfig().getBoolean("OneBotSettings.HttpSettings.Enable")) {
            updateFriendList();
            data = WebSocket.getEchoHashMap().get("get_friend_list");
        } else {
            try {
                URL url = new URL(ConfigUtil.getConfig().getString("OneBotSettings.HttpSettings.Host") + "get_friend_list");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String response = in.readLine();
                    data = JSON.parseObject(response);
                } finally {
                    conn.disconnect();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        List<Friend> friendList = new ArrayList<>();
        for (JSONObject friend : data.getJSONArray("data").toList(JSONObject.class)) {
            friendList.add(new Friend(friend));
        }
        return friendList;
    }

    public static GroupInfo getGroupInfo(Long targetId) {
        JSONObject data;
        if (!ConfigUtil.getConfig().getBoolean("OneBotSettings.HttpSettings.Enable")) {
            updateGroupInfo(targetId);
            data = WebSocket.getEchoHashMap().get("get_group_info|" + targetId);
        } else {
            try {
                URL url = new URL(ConfigUtil.getConfig().getString("OneBotSettings.HttpSettings.Host") + "get_group_info?group_id=" + targetId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String response = in.readLine();
                    data = JSON.parseObject(response);
                } finally {
                    conn.disconnect();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new GroupInfo(data.getJSONObject("data"));
    }

    public static List<GroupInfo> getGroupList() {
        JSONObject data;
        if (!ConfigUtil.getConfig().getBoolean("OneBotSettings.HttpSettings.Enable")) {
            updateGroupList();
            data = WebSocket.getEchoHashMap().get("get_group_list");
        } else {
            try {
                URL url = new URL(ConfigUtil.getConfig().getString("OneBotSettings.HttpSettings.Host") + "get_group_list");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String response = in.readLine();
                    data = JSON.parseObject(response);
                } finally {
                    conn.disconnect();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        List<GroupInfo> groupList = new ArrayList<>();
        for (JSONObject group : data.getJSONArray("data").toList(JSONObject.class)) {
            groupList.add(new GroupInfo(group));
        }
        return groupList;
    }

    public static Member getGroupMemberInfo(Long groupId, Long targetId) {
        JSONObject data;
        if (!ConfigUtil.getConfig().getBoolean("OneBotSettings.HttpSettings.Enable")) {
            updateGroupMemberInfo(groupId, targetId);
            data = WebSocket.getEchoHashMap().get("get_group_member_info|" + groupId + "|" + targetId);
        } else {
            try {
                URL url = new URL(ConfigUtil.getConfig().getString("OneBotSettings.HttpSettings.Host") + "get_group_member_info?group_id=" + groupId + "&user_id=" + targetId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String response = in.readLine();
                    data = JSON.parseObject(response);
                } finally {
                    conn.disconnect();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return new Member(data.getJSONObject("data"));
    }

    public static List<Member> getGroupMemberList(Long targetId) {
        JSONObject data;
        if (!ConfigUtil.getConfig().getBoolean("OneBotSettings.HttpSettings.Enable")) {
            updateGroupMemberList(targetId);
            data = WebSocket.getEchoHashMap().get("get_group_member_list|" + targetId);
        } else {
            try {
                URL url = new URL(ConfigUtil.getConfig().getString("OneBotSettings.HttpSettings.Host") + "get_group_member_list?group_id=" + targetId);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                conn.setConnectTimeout(5000);
                conn.setReadTimeout(5000);

                try (BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()))) {
                    String response = in.readLine();
                    data = JSON.parseObject(response);
                } finally {
                    conn.disconnect();
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        List<Member> memberList = new ArrayList<>();
        for (JSONObject member : data.getJSONArray("data").toList(JSONObject.class)) {
            memberList.add(new Member(member));
        }
        return memberList;
    }

    public static Command getCommand(String command) {
        return CommandUtil.getCommand(command).addAlias(command);
    }

    public static void registerListener(PluginInfo pluginInfo, Listener listener) {
        ListenerUtil.registerListener(pluginInfo, listener);
    }

    public static void exit() {
        for (MHDFBotPlugin botPlugin : PluginUtil.getBotPluginList()) {
            botPlugin.onDisable();
        }
        System.exit(0);
    }
}
