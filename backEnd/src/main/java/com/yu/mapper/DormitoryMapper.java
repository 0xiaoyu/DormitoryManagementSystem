package com.yu.mapper;

import com.yu.domain.Dormitory;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
* @author za'y
* @description 针对表【dormitory(宿舍管理)】的数据库操作Mapper
* @createDate 2023-06-26 20:36:26
* @Entity com.yu.domain.Dormitory
*/
@Mapper
public interface DormitoryMapper extends BaseMapper<Dormitory> {

}




