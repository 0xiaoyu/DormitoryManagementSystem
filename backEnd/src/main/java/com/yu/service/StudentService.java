package com.yu.service;

import com.yu.domain.Student;
import com.baomidou.mybatisplus.extension.service.IService;

/**
* @author za'y
* @description 针对表【students(学生表)】的数据库操作Service
* @createDate 2023-06-26 20:36:27
*/
public interface StudentsService extends IService<Student> {


    boolean add(Student student);
}
