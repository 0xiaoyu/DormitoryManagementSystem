package com.yu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yu.domain.Student;

/**
* @author za'y
* @description 针对表【students(学生表)】的数据库操作Service
* @createDate 2023-06-26 20:36:27
*/
public interface StudentService extends IService<Student> {

    /**
     * 添加学生
     * 如果学生的密码为空，则默认密码为123456789
     * @param student  学生
     * @return 是否添加成功
     */
    boolean add(Student student);


}
