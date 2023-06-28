package com.yu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.domain.Student;
import com.yu.mapper.StudentMapper;
import com.yu.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author za'y
 * @description 针对表【students(学生表)】的数据库操作Service实现
 * @createDate 2023-06-26 20:36:27
 */
@Service
public class StudentServiceImpl extends ServiceImpl<StudentMapper, Student>
        implements StudentService {

    @Autowired
    private StudentMapper mapper;


    @Override
    public boolean add(Student student) {
        if (student.getPassword() == null) {
            student.setPassword("123456789");
        }
        save(student);
        return true;
    }

}




