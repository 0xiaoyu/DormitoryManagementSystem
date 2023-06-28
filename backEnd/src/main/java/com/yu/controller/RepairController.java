package com.yu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.common.R;
import com.yu.domain.Repair;
import com.yu.domain.Student;
import com.yu.service.RepairService;
import com.yu.service.StudentService;
import com.yu.vo.RepairVO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/repair")
public class RepairController {
    @Autowired
    private RepairService service;
    @Autowired
    private StudentService studentService;



    record RepairPage(Integer page, Integer limit, Integer type,
                      Integer status, LocalDateTime startTime, LocalDateTime endTime, String repairname) {
    }

    @PostMapping("/page")
    public R getPage(@RequestBody RepairPage page) {
        Page<Repair> pages = new Page<>(page.page, page.limit);
        LambdaQueryWrapper<Repair> q = new LambdaQueryWrapper<>();
        q.like(StringUtils.hasText(page.repairname), Repair::getRepairname, page.repairname);
        q.eq(!ObjectUtils.isEmpty(page.status), Repair::getStatus, page.status);
        q.eq(!ObjectUtils.isEmpty(page.type), Repair::getType, page.type);
        q.ge(!ObjectUtils.isEmpty(page.startTime), Repair::getUpdatedate, page.startTime);
        q.le(!ObjectUtils.isEmpty(page.endTime), Repair::getUpdatedate, page.endTime);
        Page<Repair> repairs = service.page(pages, q);
        List<RepairVO> list = repairs.getRecords().stream().map(o -> {
            RepairVO vo = new RepairVO();
            BeanUtils.copyProperties(o, vo);
            Student student = studentService.getById(o.getStudentid());
            if (student == null) {
                vo.setRoomid(205);
                vo.setDormitoryid(10);
            } else {
                vo.setRoomid(student.getDormitoryid());
                vo.setDormitoryid(student.getDormitoryid());
            }
            return vo;
        }).toList();
        return R.ok(Map.of("items", list, "total", repairs.getTotal()));
    }

    @PostMapping("/addorupdate")
    public R addOrUpdate(@RequestBody Repair repair) {
        if (repair.getStatus() == null) {
            repair.setStatus(0);
        } else {
            repair.setStatus((repair.getStatus() + 1)%3);
        }
        service.saveOrUpdate(repair);
        return R.ok();
    }

    @GetMapping("/getNumber")
    public R getNumber(){
        long repair = service.count();
        LambdaQueryWrapper<Repair> q = new LambdaQueryWrapper<>();
        q.eq(Repair::getStatus,0);
        long repairN = service.count(q);

        return R.ok(Map.of("repair",repair,"repairN",repairN,"repairC",repair-repairN));
    }
    @GetMapping("/getNumber/{name}")
    public R getNumber(@PathVariable String name){
        LambdaQueryWrapper<Repair> q = new LambdaQueryWrapper<>();
        q.eq(Repair::getRepairname,name);
        long repair = service.count(q);
        q.eq(Repair::getStatus,0);
        long repairN = service.count(q);
        return R.ok(Map.of("repair",repair,"repairN",repairN,"repairC",repair-repairN));
    }

}
