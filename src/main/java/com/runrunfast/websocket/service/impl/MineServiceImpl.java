package com.runrunfast.websocket.service.impl;

import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.runrunfast.websocket.mapper.MineMapper;
import com.runrunfast.websocket.pojo.Group;
import com.runrunfast.websocket.pojo.Grouping;
import com.runrunfast.websocket.pojo.Mine;
import com.runrunfast.websocket.service.MineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @version <p>V1.0</p>
 * @Author: <H2>james</H2>
 * @Description: <P>添加说明</P>
 * @Date: <P>CREATE IS 2018/9/21 16:53</P>
 **/
@Service
public class MineServiceImpl extends ServiceImpl<MineMapper,Mine> implements MineService {

    @Autowired
    private MineMapper mineMapper;

    @Override
    public Mine selectMine(String id) {
        return mineMapper.selectMine(id);
    }

    @Override
    public List<Grouping> selectGroupings(String id) {
        return mineMapper.selectGroupings(id);
    }

    @Override
    public List<Mine> selectFriends(String id) {
        return mineMapper.selectFriends(id);
    }

    @Override
    public List<Group> selectGroups(String id) {
        return mineMapper.selectGroups(id);
    }
}
