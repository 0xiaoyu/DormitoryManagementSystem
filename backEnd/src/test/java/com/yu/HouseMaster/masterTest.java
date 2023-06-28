package com.yu.HouseMaster;

import com.yu.domain.Housemaster;
import com.yu.service.HousemasterService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class masterTest {
    @Autowired
    private HousemasterService service;

    @Test
    public void testAdd(){
        Housemaster root = new Housemaster();
        root.setDormitoryid(0);
        root.setPassword("root");
        root.setName("root");
        root.setGender("男");
        service.save(root);
    }

    @Test
    public void testGet(){
        System.out.println(service.list());
    }
}
