package com.runrunfast.websocket.utils;

import com.alibaba.fastjson.JSONObject;
/**
 * @version <p>V1.0</p>
 * @Author: <H2>james</H2>
 * @Description: <P>图灵机器人API2.0工具类</P>
 * @Date: <P>CREATE IS 2018/9/25 11:25</P>
 **/
public class TulingUtil {

    /**
     * 存储APIkey
     */
    public static final String API_KEY = "432f550f108d4a1bba1387e0fd611159";
    /**
     * 存储接口请求地址
     */
    public static final String API_URL = "http://openapi.tuling123.com/openapi/api/v2";

    /**
     * 图灵请求参数 json
     * @param text
     * @return
     */
    public static JSONObject transJosn(String text,String userId) {

        //创建JSONOBJECT对象存入值
        JSONObject tulingObject = new JSONObject();

        //1.输入类型:0-文本(默认)、1-图片、2-音频
        tulingObject.put("reqType", "0");

        // 2.输入信息
        JSONObject perception = new JSONObject();

        //2.1文本信息
        JSONObject inputText = new JSONObject();

        //2.1.1直接输入文本
        inputText.put("text", text);

        //把2.1.1inputText文本值放入父级中
        perception.put("inputText", inputText);

        tulingObject.put("perception", perception);

        // 3.用户参数
        JSONObject userInfo = new JSONObject();

        //3.1机器人标识
        userInfo.put("apiKey", API_KEY);

        //3.2用户唯一标识
        userInfo.put("userId", userId);

        tulingObject.put("userInfo", userInfo);

        return tulingObject;
    }

    /**
     * @description     图灵机器人对话工具类
     * @param text      文本信息
     * @param userId    用户唯一标识
     * @return
     */
    public static JSONObject turingChat(String text,String userId) {

        /**
         * 请求成功放回的数据
         */
        String data = OkHttpUtil.postJsonParams(API_URL, transJosn(text,userId).toJSONString());

        return JSONObject.parseObject(data);

    }

}
