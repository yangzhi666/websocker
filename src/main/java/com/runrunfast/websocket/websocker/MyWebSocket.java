package com.runrunfast.websocket.websocker;

import org.springframework.stereotype.Component;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version <p>V1.0</p>
 * @Author: <H2>james</H2>
 * @Description: <P>添加说明</P>
 * @Date: <P>CREATE IS 2018/9/21 14:54</P>
 **/
@ServerEndpoint(value="/websocker")
@Component
public class MyWebSocket {
    /**
     * 统计在线人数
     */
    private static int onlineCount = 0;

    /**
     * 用本地线程保存session
     */
    private static ThreadLocal<Session> sessions = new ThreadLocal<Session>();

    /**
     * 保存所有连接上的session
     */
    private static Map<String, Session> sessionMap = new ConcurrentHashMap<String, Session>();

    /**
     * 在线人数
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    /**
     * 新增在线人数
     */
    public static synchronized void addOnlineCount() {
        onlineCount++;
    }

    /**
     * 减少在线人数
     */
    public static synchronized void subOnlineCount() {
        onlineCount--;
    }

    /**
     * 连接
     * @param session
     */
    @OnOpen
    public void onOpen(Session session) {
        sessions.set(session);
        addOnlineCount();
        sessionMap.put(session.getId(), session);
        System.out.println("【" + session.getId() + "】连接上服务器======当前在线人数【" + getOnlineCount() + "】");
        //连接上后给客户端一个消息
        sendMsg(session, "连接服务器成功！");
    }

    /**
     * 关闭
     * @param session
     */
    @OnClose
    public void onClose(Session session) {
        subOnlineCount();
        sessionMap.remove(session.getId());
        System.out.println("【" + session.getId() + "】退出了连接======当前在线人数【" + getOnlineCount() + "】");
    }

    /**
     * 接收消息   客户端发送过来的消息
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        System.out.println("【" + session.getId() + "】客户端的发送消息======内容【" + message + "】");
        String[] split = message.split(",");
        String sessionId = split[0];
        Session ss = sessionMap.get(sessionId);
        if (ss != null) {
            String msgTo = "【" + session.getId() + "】发送给【您】的消息:\n【" + split[1] + "】";
            String msgMe = "【我】发送消息给【" + ss.getId() + "】:\n" + split[1];
            sendMsg(ss, msgTo);
            sendMsg(session, msgMe);
        } else {
            for (Session s : sessionMap.values()) {
                if (!s.getId().equals(session.getId())) {
                    sendMsg(s, "【" + session.getId() + "】发送给【您】的广播消息:\n【" + message + "】");
                } else {
                    sendMsg(session, "【我】发送广播消息给大家\n" + message);
                }
            }
        }

    }

    /**
     * 异常
     * @param session
     * @param throwable
     */
    @OnError
    public void onError(Session session, Throwable throwable) {
        System.out.println("发生异常!");
        throwable.printStackTrace();
    }


    /**
     * 统一的发送消息方法
     * @param session
     * @param msg
     */
    public synchronized void sendMsg(Session session, String msg) {
        try {
            session.getBasicRemote().sendText(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}