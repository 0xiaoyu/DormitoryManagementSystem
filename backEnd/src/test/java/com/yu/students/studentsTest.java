package com.yu.students;

import com.yu.domain.Student;
import com.yu.service.StudentService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class studentsTest {

    @Autowired
    private StudentService service;
    @Test
    public void testAddStudent(){
        Student s = new Student();
        s.setAge(1);
        s.setGender("男");
        s.setName("root");
        service.add(s);
    }

    @Test
    public void update(){
        Student studentsVO = new Student();
        studentsVO.setId(20200001);
        studentsVO.setAge(1);
        studentsVO.setName("小明");
        studentsVO.setGender("女");
        studentsVO.setDormitoryid(1);
        studentsVO.setPassword("123456789");
        studentsVO.setPhone("17608444444");
        studentsVO.setRoomid(320);
        studentsVO.setDormitoryid(5);
        service.updateById(studentsVO);

    }
}
