package com.runrunfast.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import com.runrunfast.websocket.pojo.DataInfo;
import com.runrunfast.websocket.pojo.Group;
import com.runrunfast.websocket.pojo.Grouping;
import com.runrunfast.websocket.pojo.Mine;
import com.runrunfast.websocket.service.MineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

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

    @GetMapping("/index")
    public String visitWebsocker(){
        return "websocker";
    }

    /**
     * 获取个人信息
     * @return
     */
    @GetMapping("/all/{id}")
    @ResponseBody
    public JSONObject getAll(@PathVariable String id){

        JSONObject jsonObject = new JSONObject();

        //查询个人信息
        Mine mine = mineService.selectMine(id);
        //查询分组
        List<Grouping> groupings = mineService.selectGroupings(id);
        //查询群
        List<Group> groups = mineService.selectGroups(id);

        jsonObject.put("code",0);
        jsonObject.put("msg","");
        jsonObject.put("data",new DataInfo(mine,groupings,groups));

        return jsonObject;
    }

}
