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

    /**
     * 日志打印对象
     */
    private Logger logger = LoggerFactory.getLogger(WebSocketServer.class);

    //在线人数初始值
    private static int onlineCount = 0;

    // public static Map<String,Session> mapUS=new HashMap<String,Session>();//根据用户找session
    // public static Map<Session,String> mapSU=new HashMap<Session,String>();//根据session找用户

    /**
     * ConcurrentHashMap是线程安全的，而HashMap是线程不安全的。
     */
    public static ConcurrentHashMap<String,Session> mapUS = new ConcurrentHashMap<String,Session>();
    private static ConcurrentHashMap<Session,String> mapSU = new ConcurrentHashMap<Session,String>();

    /**
     * 连接建立成功调用的方法
     * @param session  当前建立连接用户session
     * @param username 当前建立连接用户名称
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("username") String username){
        //this.session=session;
        mapUS.put(username,session);
        mapSU.put(session,username);
        //上线通知由客户端自主发起
        onlineCount++;           //在线数加1
        logger.info("用户["+username+"]进入聊天系统，[当前在线人数][" + onlineCount+"]");
    }

    /**
     * 收到客户端消息后调用的方法
     * @param message
     * @param session
     */
    @OnMessage
    public void onMessage(String message,Session session) {

        logger.info("[客户端消息]\t[" + message+"]");

        //解析客户端传过来的值
        JSONObject jsonObject = JSONObject.parseObject(message);

        //获取被推送人的信息
        JSONObject jsonObjectTo = (JSONObject) jsonObject.get("to");

        //从客户端传过来的信息中获取推送人和被推送人的关系
        String type = (String)jsonObjectTo.get("type");

        //设置类型
        jsonObject.put("type",type);

        //被推送人的名称
        String to = (String)jsonObjectTo.get("username");

        //删除被推送人的信息
        jsonObject.remove("to");

        //判断聊天类型是否为空
        if(type == null){return ;}

        //根据不同的类型执行不同的操作
        switch (type) {

            //单聊
            case "friend":

                //通过用户名称，获取session
                Session session_to = mapUS.get(to);

                //判断session存不存在
                if(session_to != null){

                    //发送推送消息人的个人信息
                    session_to.getAsyncRemote().sendText(jsonObject.toString());

                    logger.info("发给["+to+"]\t{}","[发送信息]["+jsonObject.toString()+"]");

                }

                break;

            //群聊
            case "group":

                //获取被推送人的信息
                JSONObject jsonObjectMine = (JSONObject) jsonObject.get("mine");

                String username = jsonObjectMine.get("username").toString();

                //发送到在线用户
                if(mapUS.containsKey(username)){
                    session = mapUS.get(username);
                    jsonObject.put("id",jsonObjectTo.get("id").toString());
                    session.getAsyncRemote().sendText(jsonObject.toString());

                    System.out.println("发给群里所有在线的人"+username+"："+jsonObject.toString());
                }
                break;
            case "3":
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
     * 连接关闭调用的方法
     * @param session
     */
    @OnClose
    public void onClose(Session session){

        //通过session，获取相关的用户名称
        String username = mapSU.get(session);

        //根据用户名称判断是不是为空
        if(username != null && username != ""){

            //下线通知
            JSONObject jsonObject=new JSONObject();

            //设置类型的值为3
            jsonObject.put("type", 3);
            jsonObject.put("userName", username);
            jsonObject.put("contentType", "offline");
            jsonObject.put("to", "all");

            //将json装换成字符串
            String jsonString=jsonObject.toString();

            //循环发给所有在线的人
            for(Session s:session.getOpenSessions()){
                s.getAsyncRemote().sendText(jsonString);
            }

            //在线数减1
            onlineCount--;

            logger.info("用户["+username+"]退出聊天！\t[当前在线人数为][" + onlineCount+"]");

            //根据用户名称，删除对应的session
            mapUS.remove(username);

            //根据session，删除对应的username
            mapSU.remove(session);
        }
    }

    /**
     * 发生错误时调用
     * @param error
     */
    @OnError
    public void onError(Throwable error){
        logger.info("[平台发生错误]{}","["+error.getMessage()+"]");
        error.printStackTrace();
    }

}
