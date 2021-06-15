package com.wcy.tai.lab1.mappers;

import com.wcy.tai.lab1.data.Teacher;
import com.wcy.tai.lab1.dtos.CreateTeacherRequest;
import org.springframework.stereotype.Component;

@Component
public class CreateTeacherRequestToTeacherMapper {

    public static Teacher mapToTeacher(CreateTeacherRequest req) {
        Teacher teacher = new Teacher();

        teacher.setName(req.getName());
        teacher.setSurname(req.getSurname());
       
        return teacher;
    }
}
