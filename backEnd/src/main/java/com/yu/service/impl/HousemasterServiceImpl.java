package com.yu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.domain.Housemaster;
import com.yu.mapper.HousemasterMapper;
import com.yu.service.HousemasterService;
import org.springframework.stereotype.Service;

/**
* @author za'y
* @description 针对表【housemaster(宿管人员表)】的数据库操作Service实现
* @createDate 2023-06-26 20:36:26
*/
@Service
public class HousemasterServiceImpl extends ServiceImpl<HousemasterMapper, Housemaster>
    implements HousemasterService{

    @Override
    public Housemaster login(String username, String password) {
        LambdaQueryWrapper<Housemaster> lqw = new LambdaQueryWrapper<>();
        lqw.eq(Housemaster::getName, username);
        lqw.eq(Housemaster::getPassword, password);
        return getOne(lqw);
    }
}




