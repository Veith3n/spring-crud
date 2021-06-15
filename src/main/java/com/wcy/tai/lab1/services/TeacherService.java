package com.wcy.tai.lab1.services;

import com.wcy.tai.lab1.dtos.CreateTeacherRequest;
import com.wcy.tai.lab1.dtos.TeacherResponse;

import java.util.List;

public interface TeacherService {
    List<TeacherResponse> listTeachers();

    Long addTeacher(CreateTeacherRequest createTeacherRequest);
}
