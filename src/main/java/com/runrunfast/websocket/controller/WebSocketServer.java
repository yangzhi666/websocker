package com.runrunfast.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @version <p>V1.0</p>
 * @Author: <H2>james</H2>
 * @Description: <P>添加说明</P>
 * @Date: <P>CREATE IS 2018/9/27 16:00</P>
 **/
@ServerEndpoint("/websocket/{username}")
@Component
public class WebSocketServer {

    private Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    private static int onlineCount = 0;
    // public static Map<String,Session> mapUS=new HashMap<String,Session>();//根据用户找session
    // public static Map<Session,String> mapSU=new HashMap<Session,String>();//根据session找用户
    //ConcurrentHashMap是线程安全的，而HashMap是线程不安全的。
    public static ConcurrentHashMap<String,Session> mapUS = new ConcurrentHashMap<String,Session>();
    private static ConcurrentHashMap<Session,String> mapSU = new ConcurrentHashMap<Session,String>();

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("username") String username){
        //this.session=session;
        mapUS.put(username,session);
        mapSU.put(session,username);
        //上线通知由客户端自主发起
        onlineCount++;           //在线数加1
        System.out.println("用户["+username+"]进入wsll！当前在线人数为" + onlineCount);
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose(Session session){
        String username = mapSU.get(session);
        if(username != null && username != ""){
            //下线通知
            JSONObject jsonObject=new JSONObject();
            jsonObject.put("type", 3);
            jsonObject.put("userName", username);
            jsonObject.put("id", username);
            jsonObject.put("contentType", "offline");
            jsonObject.put("to", "all");
            String jsonString=jsonObject.toString();
            //循环发给所有在线的人
            for(Session s:session.getOpenSessions()){
                s.getAsyncRemote().sendText(jsonString);
            }
            onlineCount--;           //在线数减1
            System.out.println("用户["+username+"]退出聊天！当前在线人数为" + onlineCount);
            mapUS.remove(username);
            mapSU.remove(session);
        }
    }
    /**
     * 收到客户端消息后调用的方法
     */
    @OnMessage
    public void onMessage(String message,Session session) {
        System.out.println("[客户端消息]\t" + message);
        JSONObject jsonObject=JSONObject.parseObject(message);
        String type = (String)jsonObject.get("type");
        JSONObject jsonObjectTo = (JSONObject) jsonObject.get("to");
        String to = (String)jsonObjectTo.get("username");
        jsonObject.remove("to");
        if(type == null){return ;}
        switch (type) {
            //单聊
            case "friend":
                Session session_to = mapUS.get(to);
                if(session_to!=null){
                    session_to.getAsyncRemote().sendText(jsonObject.toString());
                    System.out.println("发给["+to+"]\t"+jsonObject.toString());
                }
                break;
            //群聊
            case "group":
                String[] members=to.split(",");
                //发送到在线用户
                for(String member:members){
                    if(mapUS.containsKey(member)){
                        session = mapUS.get(member);
                        session.getAsyncRemote().sendText(jsonObject.toString());
                        System.out.println("发给群里所有在线的人"+member+"："+jsonObject.toString());
                    }
                }
                break;
            case "toz":
                //循环发给所有在线的人
                for(Session s:session.getOpenSessions()){
                    s.getAsyncRemote().sendText(jsonObject.toString());
                    System.out.println("发给系统所有在线的人"+session.getOpenSessions()+"："+jsonObject.toString());
                }
                break;
            default:
                break;
        }
    }
    /**
     * 发生错误时调用
     * @param error
     */
    @OnError
    public void onError(Throwable error){
        System.out.println("平台发生错误");
        error.printStackTrace();
    }
}
