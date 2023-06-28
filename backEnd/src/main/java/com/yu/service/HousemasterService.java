package com.yu.service;

import com.yu.domain.Housemaster;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author za'y
* @description 针对表【housemaster(宿管人员表)】的数据库操作Service
* @createDate 2023-06-26 20:36:26
*/
public interface HousemasterService extends IService<Housemaster> {

    Housemaster login(String username, String password);
}
