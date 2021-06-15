package com.wcy.tai.lab1.controllers;

import com.wcy.tai.lab1.dtos.CreateTeacherRequest;
import com.wcy.tai.lab1.dtos.TeacherResponse;
import com.wcy.tai.lab1.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping()
    public List<TeacherResponse> listTeachers() {
        return teacherService.listTeachers();
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public void addTeacher(@RequestBody @Valid CreateTeacherRequest teacherRequest) {
        teacherService.addTeacher(teacherRequest);
    }
}
