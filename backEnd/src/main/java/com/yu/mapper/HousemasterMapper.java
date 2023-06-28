package com.yu.mapper;

import com.yu.domain.Housemaster;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author za'y
* @description 针对表【housemaster(宿管人员表)】的数据库操作Mapper
* @createDate 2023-06-26 20:36:26
* @Entity com.yu.domain.Housemaster
*/
@Mapper
public interface HousemasterMapper extends BaseMapper<Housemaster> {

}




