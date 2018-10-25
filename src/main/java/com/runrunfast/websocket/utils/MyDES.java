package com.runrunfast.websocket.utils;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * @author : 邹智敏
 * @data : 2018年-03月-05日 11:49
 * @Description : DES加密解密
 **/
public class MyDES {

    /**
     * 利用SHIRO 进行MD5加密
     * @param password  用户的明文密码
     * @param salt  用户密码加密所需的盐,这里用账号
     * @return
     */
    public static String GetPwd(String password,String salt)
    {
        String newMd5Password = new SimpleHash("MD5", password, salt,  2048).toHex();
        return newMd5Password;
    }

    /**
     * shiro MD5 main
     * @param args
     */
    public static void main(String[] args) {
        System.out.println(GetPwd("123456","zzm"));
    }
}
