package com.wcy.tai.lab1.services;

import com.wcy.tai.lab1.data.Student;
import com.wcy.tai.lab1.dtos.CreateStudentRequest;
import com.wcy.tai.lab1.dtos.StudentResponse;
import com.wcy.tai.lab1.dtos.UpdateStudentRequest;

import java.util.List;
import java.util.Optional;

public interface StudentService {
    List<StudentResponse> listStudents();

    Optional<Student> getStudent(Long studentId);

    void deleteStudent(Long studentId);

    Long addStudent(CreateStudentRequest createStudentRequest);

    void updateStudent(Long id, UpdateStudentRequest createStudentRequest);
}
