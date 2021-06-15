package com.wcy.tai.lab1.services;

import com.wcy.tai.lab1.data.Teacher;
import com.wcy.tai.lab1.dtos.CreateTeacherRequest;
import com.wcy.tai.lab1.dtos.TeacherResponse;

import java.util.List;
import java.util.Optional;

public interface TeacherService {
    List<TeacherResponse> listTeachers();

    Optional<Teacher> getTeacher(Long teacherId);

    void deleteTeacher(Long teacherId);

    Long addTeacher(CreateTeacherRequest createTeacherRequest);
}
