package com.runrunfast.websocket.pojo;

import lombok.Data;

import java.util.List;

/**
 * @version <p>V1.0</p>
 * @Author: <H2>james</H2>
 * @Description: <P>分组列表</P>
 * @Date: <P>CREATE IS 2018/9/21 16:18</P>
 **/
@Data
public class Grouping {

    private String id;
    private String groupname;
    List<Mine> list;
}
