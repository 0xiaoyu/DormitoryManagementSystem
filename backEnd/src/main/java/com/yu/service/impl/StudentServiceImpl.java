package com.yu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yu.domain.Student;
import com.yu.service.StudentsService;
import com.yu.mapper.StudentsMapper;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

/**
* @author za'y
* @description 针对表【students(学生表)】的数据库操作Service实现
* @createDate 2023-06-26 20:36:27
*/
@Service
public class StudentsServiceImpl extends ServiceImpl<StudentsMapper, Student>
    implements StudentsService{


    @Override
    public boolean add(Student student) {
        LocalDate birthday = student.getBirthday();
        student.setPassword(String.valueOf(birthday.getYear()+birthday.getMonthValue()+birthday.getDayOfMonth()));
        save(student);
        return true;
    }
}




