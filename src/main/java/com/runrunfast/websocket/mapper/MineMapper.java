package com.runrunfast.websocket.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.runrunfast.websocket.pojo.Group;
import com.runrunfast.websocket.pojo.Grouping;
import com.runrunfast.websocket.pojo.Mine;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @version <p>V1.0</p>
 * @Author: <H2>james</H2>
 * @Description: <P>添加说明</P>
 * @Date: <P>CREATE IS 2018/9/21 16:52</P>
 **/
public interface MineMapper extends BaseMapper<Mine> {

    /**
     * 个人信息查询 个人id
     * @param id
     * @return
     */
    Mine selectMine(@Param("id") String id);

    /**
     * 个人分组查询
     * @param id 个人id
     * @return
     */
    List<Grouping> selectGroupings(@Param("id") String id);

    /**
     * 查询分组中的好友信息
     * @param id 分组id
     * @return
     */
    List<Mine> selectFriends(@Param("id") String id);

    /**
     * 查询我的群
     * @param id 个人id
     * @return
     */
    List<Group> selectGroups(@Param("id") String id);
}
