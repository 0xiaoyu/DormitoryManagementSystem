package com.yu.repair;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.domain.Repair;
import com.yu.service.RepairService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RepairTest {
    @Autowired
    private RepairService service;


    @Test
    public void testAdd(){
        Repair repair = new Repair();
        repair.setRoomid(3);
        repair.setDetail("水龙头坏了");
        repair.setStatus(0);
        service.save(repair);
    }

    @Test
    public void getPageList(){
        System.out.println(service.getList(new Page<>(1, 5)).getRecords());
    }
}
