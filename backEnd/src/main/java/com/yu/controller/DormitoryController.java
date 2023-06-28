package com.yu.controller;

import com.yu.common.R;
import com.yu.domain.Dormitory;
import com.yu.service.DormitoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dormitory")
public class DormitoryController {

    @Autowired
    private DormitoryService service;
    @GetMapping("/list")
    public R getList(){
        List<Dormitory> list = service.list();
        return R.ok(list);
    }

}
