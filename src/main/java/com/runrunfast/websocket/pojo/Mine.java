package com.runrunfast.websocket.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @version <p>V1.0</p>
 * @Author: <H2>james</H2>
 * @Description: <P>我的信息</P>
 * @Date: <P>CREATE IS 2018/9/21 16:11</P>
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mine implements Serializable {
    private String id;
    private String username;
    private String sign;
    private String avatar;
    private String status;
    private String number;
    private String password;
}
