package com.yu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.domain.Repair;
import com.yu.service.RepairService;
import com.yu.mapper.RepairMapper;
import org.springframework.stereotype.Service;

/**
* @author za'y
* @description 针对表【repair(维修表)】的数据库操作Service实现
* @createDate 2023-06-26 20:36:26
*/
@Service
public class RepairServiceImpl extends ServiceImpl<RepairMapper, Repair>
    implements RepairService{

}




