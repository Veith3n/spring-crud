package com.wcy.tai.lab1.mappers;

import com.wcy.tai.lab1.data.Student;
import com.wcy.tai.lab1.dtos.StudentResponse;
import org.springframework.stereotype.Component;

@Component
public class StudentToResponseMapper {

    public static StudentResponse toResponse(Student student) {
        var teacher = student.getTeacher();
        var teacherId = teacher == null ? null : teacher.getId();

        return new StudentResponse(student.getId(), student.getName(), student.getSurname(), teacherId);
    }
}
