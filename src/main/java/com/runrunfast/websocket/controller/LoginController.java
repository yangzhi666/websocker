package com.runrunfast.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import com.runrunfast.websocket.pojo.Mine;
import com.runrunfast.websocket.service.MineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @version <p>V1.0</p>
 * @Author: <H2>james</H2>
 * @Description: <P>添加说明</P>
 * @Date: <P>CREATE IS 2018/9/28 10:22</P>
 **/
@Controller
@RequestMapping("Clogin")
public class LoginController {

    @Autowired
    private MineService mineService;


    /**
     * 跳转到登录页面
     */
    @RequestMapping("/loginPage")
    public String loginPage(){
        return "login";
    }

    /**
     * 登录接口
     */
    @PostMapping("/login")
    @ResponseBody
    public JSONObject toLogin(@RequestBody Mine mine){

        JSONObject json = new JSONObject();
        if(null == mine.getNumber()){
            json.put("code","100");
            json.put("message","账号不能为空");
            return json;
        }else if(null == mine.getPassword()){
            json.put("code","100");
            json.put("message","密码不能为空");
            return json;
        }

        mine = mineService.selectMineNumber(mine.getNumber());
        if(null == mine){
            json.put("code","100");
            json.put("message","该账号不存在");
            return json;
        }else{
            json.put("code",200);
            json.put("username", mine.getUsername());
        }
        return json;
    }

}
