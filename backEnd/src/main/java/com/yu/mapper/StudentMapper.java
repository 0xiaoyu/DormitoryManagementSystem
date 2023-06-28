package com.yu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yu.domain.Student;
import org.apache.ibatis.annotations.Mapper;

/**
* @author za'y
* @description 针对表【students(学生表)】的数据库操作Mapper
* @createDate 2023-06-26 20:36:26
* @Entity com.yu.domain.Students
*/
@Mapper
public interface StudentMapper extends BaseMapper<Student> {


}




