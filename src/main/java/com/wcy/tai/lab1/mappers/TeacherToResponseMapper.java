package com.wcy.tai.lab1.mappers;

import com.wcy.tai.lab1.data.Teacher;
import com.wcy.tai.lab1.dtos.TeacherResponse;
import org.springframework.stereotype.Component;

@Component
public class TeacherToResponseMapper {

    public static TeacherResponse toResponse(Teacher teacher) {
        return new TeacherResponse(teacher.getId(), teacher.getName(), teacher.getSurname());
    }
}
