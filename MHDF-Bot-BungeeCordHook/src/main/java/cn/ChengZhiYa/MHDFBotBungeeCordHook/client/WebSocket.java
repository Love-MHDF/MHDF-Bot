package cn.ChengZhiYa.MHDFBotBungeeCordHook.client;

import cn.ChengZhiYa.MHDFBotBungeeCordHook.event.WebSocketEvent;
import cn.ChengZhiYa.MHDFBotBungeeCordHook.main;
import cn.ChengZhiYa.MHDFBotBungeeCordHook.util.ConfigUtil;
import com.alibaba.fastjson2.JSONObject;
import jakarta.websocket.*;
import lombok.Getter;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashMap;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

@ClientEndpoint
public final class WebSocket {
    private static final WebSocketContainer container = ContainerProvider.getWebSocketContainer();
    public static Session session;
    @Getter
    static HashMap<String, JSONObject> echoHashMap = new HashMap<>();

    public static void connectWebSocketServer() {
        try {
            container.connectToServer(new WebSocket(), new URI(Objects.requireNonNull(ConfigUtil.getConfig().getString("WebSocketSettings.Host"))));
        } catch (DeploymentException | IOException | URISyntaxException e) {
            main.instance.getLogger().info("&c无法正常连接至websocket服务端!");
            e.printStackTrace();
            main.instance.getProxy().getScheduler().schedule(main.instance, WebSocket::connectWebSocketServer, 5, TimeUnit.SECONDS);
        }
    }

    public static void send(JSONObject data) {
        try {
            if (WebSocket.session != null) {
                if (WebSocket.session.isOpen()) {
                    JSONObject message = new JSONObject();
                    message.put("server_type", "bungee");
                    message.put("data", data);
                    WebSocket.session.getAsyncRemote().sendText(message.toJSONString());
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
        main.instance.getProxy().getPluginManager().callEvent(new WebSocketEvent(JSONObject.parseObject(message)));
    }

    @OnClose
    public void onClose() {
        WebSocket.session = null;
    }

    @OnError
    public void onError(Throwable e) {
        WebSocket.session = null;
        connectWebSocketServer();
        e.printStackTrace();
    }
}
