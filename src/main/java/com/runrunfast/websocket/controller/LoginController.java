package com.runrunfast.websocket.controller;

import com.alibaba.fastjson.JSONObject;
import com.runrunfast.websocket.pojo.Mine;
import com.runrunfast.websocket.service.MineService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

/**
 * @version <p>V1.0</p>
 * @Author: <H2>james</H2>
 * @Description: <P>添加说明</P>
 * @Date: <P>CREATE IS 2018/9/28 10:22</P>
 **/
@Controller
public class LoginController {

    @Autowired
    private MineService mineService;


    /**
     * 跳转到登录页面
     */
    @RequestMapping("/index")
    public String loginPage(){
        return "login";
    }

    /**
     * 登录接口
     */
    @PostMapping("/login")
    @ResponseBody
    public JSONObject toLogin(@RequestBody Mine mine){
        JSONObject json = null;
        try{
            json = new JSONObject();
            if(null == mine.getNumber()){
                json.put("code","100");
                json.put("message","账号不能为空");
                return json;
            }else if(null == mine.getPassword()){
                json.put("code","100");
                json.put("message","密码不能为空");
                return json;
            }
            System.out.println("账号："+mine.getNumber()+"\t"+mine.getPassword());
            Subject subject = SecurityUtils.getSubject();
            UsernamePasswordToken token = new UsernamePasswordToken(mine.getNumber(), mine.getPassword());
            subject.login(token);
            if (subject.isAuthenticated()) {
                Mine mineOne = mineService.selectMineNumber(mine.getNumber());
                json.put("code",200);
                json.put("username", mineOne.getUsername());
            }
        }catch (UnknownAccountException e) {
            json.put("code","100");
            json.put("message","该账号不存在");
        }catch (IncorrectCredentialsException e) {
            json.put("code","100");
            json.put("message","登录密码错误");
        }
        return json;
    }

    /**
     * 跳转到错误页面
     */
    @GetMapping("/error")
    public String error(){
        return "error";
    }

}
