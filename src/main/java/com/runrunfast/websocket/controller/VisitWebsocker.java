package com.runrunfast.websocket.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.runrunfast.websocket.pojo.DataInfo;
import com.runrunfast.websocket.pojo.Group;
import com.runrunfast.websocket.pojo.Grouping;
import com.runrunfast.websocket.pojo.Mine;
import com.runrunfast.websocket.service.MineService;
import com.runrunfast.websocket.utils.TulingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @version <p>V1.0</p>
 * @Author: <H2>james</H2>
 * @Description: <P>访问websocker页面</P>
 * @Date: <P>CREATE IS 2018/9/21 15:16</P>
 **/
@Controller
@RequestMapping("/Cvisit")
public class VisitWebsocker {

    @Autowired
    private MineService mineService;

    @GetMapping("/index/{username}")
    public String visitWebsocker(@PathVariable String username,Model model){
        model.addAttribute("username",username);
        return "websocker";
    }

    /**
     * 查询单个人的信息
     */
    @GetMapping("/getOne/{username}")
    @ResponseBody
    public JSONObject getOne(@PathVariable String username){

        JSONObject jsonObject = new JSONObject();

        jsonObject.put("code",0);
        jsonObject.put("msg","");
        jsonObject.put("data",mineService.selectMine(username));

        return jsonObject;
    }

    /**
     * 获取个人信息
     * @return
     */
    @GetMapping("/all/{username}")
    @ResponseBody
    public JSONObject getAll(@PathVariable String username){

        JSONObject jsonObject = new JSONObject();

        //查询个人信息
        Mine mine = mineService.selectMine(username.replaceAll("\"",""));

        //查询分组
        List<Grouping> groupings = null;

        //查询群
        List<Group> groups = null;

        if(mine != null){
            groupings = mineService.selectGroupings(mine.getId());
             groups = mineService.selectGroups(mine.getId());
        }

        jsonObject.put("code",0);
        jsonObject.put("msg","");
        jsonObject.put("data",new DataInfo(mine,groupings,groups));

        return jsonObject;
    }

    /**
     * 获取群员接口
     */
    @GetMapping("/getGroupMembers")
    @ResponseBody
    public JSONObject getGroupMembers(String id){
        JSONObject jsonObject = new JSONObject();
        JSONObject list = new JSONObject();
        list.put("list",mineService.getGroupMembers(id));
        jsonObject.put("code",0);
        jsonObject.put("msg","");
        jsonObject.put("data",list);
        return jsonObject;
    }

    /**
     * 客服机器人
     */
    @GetMapping("/getTulingMessage/{id}/{msg}")
    @ResponseBody
    public JSONObject getTulingMessage(@PathVariable String id,@PathVariable String msg){
        JSONObject jsonObject = new JSONObject();
        //获取图灵的信息
        JSONObject result = TulingUtil.turingChat(msg,id);
        JSONArray jsonArray = (JSONArray) result.get("results");
        //放回的信息
        List<HashMap<String,String>> maps = new ArrayList<>();
        for(int i = 0;i<jsonArray.size();i++){
            HashMap<String,String> map = new HashMap<>(10);
            JSONObject arrayObject = (JSONObject)jsonArray.get(i);
            JSONObject values = (JSONObject)arrayObject.get("values");
            switch (arrayObject.get("resultType").toString()){
                case "image":
                    map.put("image",values.get("image").toString());
                    maps.add(map);
                    continue;
                case "url":
                    map.put("url",values.get("url").toString());
                    maps.add(map);
                    continue;
                case "news":
                    map.put("news",values.get("news").toString());
                    maps.add(map);
                    continue;
                default:
                    map.put("text",values.get("text").toString());
                    maps.add(map);
                    continue;
            }
        }
        jsonObject.put("data",maps);
        jsonObject.put("code",0);
        jsonObject.put("msg","");
        return jsonObject;
    }


}
