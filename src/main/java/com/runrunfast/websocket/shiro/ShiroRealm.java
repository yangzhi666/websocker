package com.runrunfast.websocket.shiro;

import com.runrunfast.websocket.pojo.Mine;
import com.runrunfast.websocket.service.MineService;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;

/**
 * @author : 邹智敏
 * @data : 2018年-03月-02日 13:58
 * @Description :自定义shiro中的realm
 */
public class ShiroRealm extends AuthorizingRealm {

    private Logger logger = LoggerFactory.getLogger(ShiroRealm.class);

    @Autowired
    private MineService mineService;

    /**
     * 登陆认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //获取当前登入的用户名
        String userNumber = (String)token.getPrincipal();
        //根据账号查询相关信息
        Mine mine = mineService.selectMineNumber(userNumber);
        //判断账号是否存在
        if(null == mine)
        {
            return null;
        }
        //开始登陆认证
        AuthenticationInfo authcInfo=new SimpleAuthenticationInfo(userNumber,mine.getPassword(),ByteSource.Util.bytes(userNumber),"ShiroRealm");
        return authcInfo;
    }

    /**
     * 为当限前登录的用户授予角色和权限
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        //获取当前登入的用户名
        String userNumber=(String)principals.getPrimaryPrincipal();
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        //根据此用户名查询是否拥有   此角色
        authorizationInfo.setRoles(getRoles(userNumber,0));
        //根据用户名查询是否拥有   此权限
        authorizationInfo.setStringPermissions(getRoles(userNumber,1));
        return authorizationInfo;
    }

    /**
     * 角色和角色转换
     * @param userName
     * @param type
     * @return
     */
    private Set<String> getRoles(String userName,Integer type){
        /*List<Systemrole> role = userBll.findRoleByUid(userName);
        if (role==null || role.size()==0) return  null;
        Set<String> roles=new HashSet<String>();
        for(Systemrole a:role) {
            //0 表示查询角色
            if(type == 0){
                roles.add(a.getRolename());
            }else{
                List<Systemmenu> permission = roleBll.findPermissionByUid(a.getRoleid());
                if (permission==null || permission.size()==0) return  null;
                for(Systemmenu p:permission) {
                    roles.add(p.getAddress());
                }
            }
        }*/
        return null;
    }
}
