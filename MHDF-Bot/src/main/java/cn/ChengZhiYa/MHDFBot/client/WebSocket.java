package cn.ChengZhiYa.MHDFBot.client;

import cn.ChengZhiYa.MHDFBot.api.MHDFBot;
import cn.ChengZhiYa.MHDFBot.api.enums.notice.NotifySubType;
import cn.ChengZhiYa.MHDFBot.event.bot.HeartbeatEvent;
import cn.ChengZhiYa.MHDFBot.event.bot.LifecycleEvent;
import cn.ChengZhiYa.MHDFBot.event.message.GroupMessageEvent;
import cn.ChengZhiYa.MHDFBot.event.message.PrivateMessageEvent;
import cn.ChengZhiYa.MHDFBot.event.notice.*;
import cn.ChengZhiYa.MHDFBot.event.request.FriendRequestEvent;
import cn.ChengZhiYa.MHDFBot.event.request.GroupRequestEvent;
import cn.ChengZhiYa.MHDFBot.util.ConfigUtil;
import com.alibaba.fastjson2.JSONObject;
import jakarta.websocket.*;
import lombok.Getter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;

import static cn.ChengZhiYa.MHDFBot.util.ListenerUtil.callEvent;
import static cn.ChengZhiYa.MHDFBot.util.LogUtil.colorLog;

@ClientEndpoint
public final class WebSocket {
    private static final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    public static Session session;
    @Getter
    static HashMap<String, JSONObject> echoHashMap = new HashMap<>();

    public static void connectOneBotServer() {
        try {
            container.connectToServer(new WebSocket(), new URI(ConfigUtil.getConfig().getString("OneBotSettings.Host")));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            colorLog("&c无法正常连接至websocket服务端!");
            e.printStackTrace();
            MHDFBot.getScheduler().runTaskLater(WebSocket::connectOneBotServer, 5L);
        }
    }

    public static void send(String message) {
        try {
            if (WebSocket.session != null) {
                if (WebSocket.session.isOpen()) {
                    WebSocket.session.getAsyncRemote().sendText(message);
                } else {
                    WebSocket.session = null;
                }
            }
        } catch (Exception ignored) {
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        WebSocket.session = session;
    }

    @OnMessage
    public void onMessage(String message) {
        JSONObject data = JSONObject.parseObject(message);
        if (data.getString("post_type") != null) {
            switch (data.getString("post_type")) {
                case "message" -> {
                    switch (data.getString("message_type")) {
                        case "group" -> callEvent(new GroupMessageEvent(data));
                        case "private" -> callEvent(new PrivateMessageEvent(data));
                        default -> colorLog(message);
                    }
                }
                case "meta_event" -> {
                    switch (data.getString("meta_event_type")) {
                        case "lifecycle" -> callEvent(new LifecycleEvent(data));
                        case "heartbeat" -> callEvent(new HeartbeatEvent(data));
                        default -> colorLog(message);
                    }
                }
                case "notice" -> {
                    switch (data.getString("notice_type")) {
                        case "group_upload" -> callEvent(new GroupUploadEvent(data));
                        case "group_admin" -> callEvent(new GroupAdminEvent(data));
                        case "group_decrease" -> callEvent(new GroupDecreaseEvent(data));
                        case "group_increase" -> callEvent(new GroupIncreaseEvent(data));
                        case "group_recall" -> callEvent(new GroupRecallEvent(data));
                        case "group_card" -> callEvent(new GroupCardEvent(data));
                        case "friend_recall" -> callEvent(new FriendRecallEvent(data));
                        case "notify" -> {
                            switch (NotifySubType.getSubType(data.getString("sub_type"))) {
                                case POKE -> callEvent(new PokeEvent(data));
                                case LUCKY_KING -> callEvent(new LuckyKingEvent(data));
                                case HONOR -> callEvent(new HonorEvent(data));
                                default -> colorLog(message);
                            }
                        }
                        default -> colorLog(message);
                    }
                }
                case "request" -> {
                    switch (data.getString("request_type")) {
                        case "friend" -> callEvent(new FriendRequestEvent(data));
                        case "group" -> callEvent(new GroupRequestEvent(data));
                        default -> colorLog(message);
                    }
                }
                default -> colorLog(message);
            }
        } else if (data.getString("status") != null) {
            switch (data.getString("status")) {
                case "ok" -> getEchoHashMap().put(data.getString("echo"), data);
                case "failed" -> colorLog("&c{}", message);
                default -> colorLog(message);
            }
        }
    }

    @OnClose
    public void onClose() {
        WebSocket.session = null;
        connectOneBotServer();
    }

    @OnError
    public void onError(Throwable e) {
        WebSocket.session = null;
        connectOneBotServer();
        e.printStackTrace();
    }
}
