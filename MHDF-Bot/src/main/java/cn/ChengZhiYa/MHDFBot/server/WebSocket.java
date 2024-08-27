package cn.ChengZhiYa.MHDFBot.server;

import cn.ChengZhiYa.MHDFBot.api.enums.minecraft.ServerType;
import cn.ChengZhiYa.MHDFBot.event.minecraft.WebSocketEvent;
import cn.ChengZhiYa.MHDFBot.util.ConfigUtil;
import cn.ChengZhiYa.MHDFBot.util.ListenerUtil;
import com.alibaba.fastjson2.JSONObject;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import org.apache.catalina.Context;
import org.apache.catalina.LifecycleException;
import org.apache.catalina.startup.Tomcat;
import org.apache.tomcat.websocket.server.WsSci;

import java.io.File;
import java.io.IOException;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import static cn.ChengZhiYa.MHDFBot.util.LogUtil.colorLog;

@ServerEndpoint("/ws")
public final class WebSocket {
    private static final Set<Session> sessions = new CopyOnWriteArraySet<>();

    public static void startWebSocketServer() {
        try {
            Logger tomcatLogger = Logger.getLogger("org.apache");
            tomcatLogger.setLevel(Level.OFF);
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setLevel(Level.OFF);
            tomcatLogger.addHandler(consoleHandler);

            Tomcat tomcat = new Tomcat();
            tomcat.setPort(ConfigUtil.getConfig().getInt("WebSocketServerSettings.Port"));
            tomcat.getConnector();

            Context ctx = tomcat.addWebapp("", new File(".").getAbsolutePath());
            ctx.addServletContainerInitializer(new WsSci(), null);

            tomcat.start();
            tomcat.getServer().await();
        } catch (LifecycleException e) {
            throw new RuntimeException(e);
        }
    }

    public static void send(String action, JSONObject data) {
        for (Session session : sessions) {
            try {
                JSONObject message = new JSONObject();
                message.put("action", action);
                message.put("data", data);
                session.getBasicRemote().sendText(message.toJSONString());
            } catch (IOException e) {
                sessions.remove(session);
                throw new RuntimeException(e);
            }
        }
    }

    public static void send(Session session, String action, JSONObject data) {
        try {
            JSONObject message = new JSONObject();
            message.put("action", action);
            message.put("data", data);
            session.getBasicRemote().sendText(message.toJSONString());
        } catch (IOException e) {
            sessions.remove(session);
            throw new RuntimeException(e);
        }
    }

    @OnOpen
    public void onOpen(Session session) {
        sessions.add(session);
        colorLog("客户端{}连接至websocket服务端!", session.getId());
    }

    @OnClose
    @OnError
    public void onClose(Session session) {
        sessions.remove(session);
        colorLog("客户端{}断开websocket服务端!", session.getId());
    }

    @OnMessage
    public void onMessage(Session session, String message) {
        JSONObject data = JSONObject.parseObject(message);
        ListenerUtil.callEvent(new WebSocketEvent(ServerType.getSubType(data.getString("server_type")), data.getString("action"), data.getJSONObject("data")));
    }
}
