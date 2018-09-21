package com.runrunfast.websocket.pojo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @version <p>V1.0</p>
 * @Author: <H2>james</H2>
 * @Description: <P>添加说明</P>
 * @Date: <P>CREATE IS 2018/9/21 18:56</P>
 **/
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DataInfo {
    private Mine mine;
    private List<Grouping> friend;
    private List<Group> group;
}
