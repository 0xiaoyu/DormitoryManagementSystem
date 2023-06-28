package com.yu.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.domain.Repair;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yu.vo.RepairVO;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

/**
* @author za'y
* @description 针对表【repair(维修表)】的数据库操作Mapper
* @createDate 2023-06-26 20:36:26
* @Entity com.yu.domain.Repair
*/
@Mapper
public interface RepairMapper extends BaseMapper<Repair> {

    @Select("select r.id, r.type, r.detail, detail, status, updateDate, repairName,s.dormitoryid,s.roomid" +
            " from repair r left join student s on r.studentid = s.id ")
    Page<RepairVO> getPageList(Page<RepairVO> page);

}




