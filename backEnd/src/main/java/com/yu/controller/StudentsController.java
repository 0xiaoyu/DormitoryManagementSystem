package com.yu.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.yu.common.Code;
import com.yu.common.R;
import com.yu.domain.Student;
import com.yu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * @author za'y
 * @description 针对表【students(学生表)】的数据库操作Controller
 * @createDate 2023-06-26 20:36:27
 */
@RestController
@RequestMapping("/students")
public class StudentsController {
    @Autowired
    private StudentService service;

    record StudentPage(Integer page, Integer limit, String name, String gender, String dormitoryid) {
    }

    /**
     * 分页模糊查询
     *
     * @param student 分页查询条件
     * @return 分页查询结果
     */
    @PostMapping("/page")
    public R getPage(@RequestBody StudentPage student) {
        Page<Student> pages = new Page<>(student.page, student.limit);
        LambdaQueryWrapper<Student> q = new LambdaQueryWrapper<>();
        q.like(!"".equals(student.name), Student::getName, student.name);
        q.eq(!"".equals(student.gender), Student::getGender, student.gender);
        q.eq(!"".equals(student.dormitoryid), Student::getDormitoryid, student.dormitoryid);
        Page<Student> data = service.page(pages, q);
        return R.ok(Map.of("items", data.getRecords(), "total", data.getTotal()));
    }

    /**
     * 更新学生信息
     *
     * @param s 学生信息
     * @return 更新结果
     */
    @PostMapping("/update")
    public R updateStudent(@RequestBody Student s) {
        boolean b = service.updateById(s);
        if (b)
            return R.ok();
        else
            return R.error();
    }

    /**
     * 添加学生
     *
     * @param student 学生信息
     * @return 添加结果
     */
    @PostMapping("/add")
    public R addStudent(@RequestBody Student student) {
//        System.out.println(student);
        if (student.getName() == null || student.getGender() == null || student.getAge() == null)
            return R.error(Code.NOT_NULL);
        boolean b = service.add(student);
        if (b)
            return R.ok();
        else
            return R.error();
    }

    /**
     * 删除学生
     *
     * @param id 学生id
     * @return 删除结果
     */
    @DeleteMapping("/{id}")
    public R deleteStudent(@PathVariable Integer id) {
        boolean b = service.removeById(id);
        if (b)
            return R.ok();
        else
            return R.error();
    }
    @GetMapping("/count")
    public R count(){
        return R.ok(service.count());
    }
}
