package com.wcy.tai.lab1.mappers;

import com.wcy.tai.lab1.data.Student;
import com.wcy.tai.lab1.data.Teacher;
import com.wcy.tai.lab1.dtos.CreateStudentRequest;
import org.springframework.stereotype.Component;

@Component
public class CreateStudentRequestToStudentMapper {

    public static Student mapToStudent(CreateStudentRequest req, Teacher teacher) {
        Student student = new Student();

        student.setName(req.getName());
        student.setSurname(req.getSurname());
        student.setTeacher(teacher);

        return student;
    }

    public static Student mapToStudent(CreateStudentRequest req) {
        Student student = new Student();

        student.setName(req.getName());
        student.setSurname(req.getSurname());

        return student;
    }
}
