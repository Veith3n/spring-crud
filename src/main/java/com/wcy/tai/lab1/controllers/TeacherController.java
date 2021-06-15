package com.wcy.tai.lab1.controllers;

import com.wcy.tai.lab1.dtos.CreateTeacherRequest;
import com.wcy.tai.lab1.dtos.TeacherResponse;
import com.wcy.tai.lab1.mappers.TeacherToResponseMapper;
import com.wcy.tai.lab1.services.TeacherService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/teachers/")
public class TeacherController {
    private final TeacherService teacherService;

    @GetMapping()
    public List<TeacherResponse> listTeachers() {
        return teacherService.listTeachers();
    }

    @RequestMapping(value = "{id}")
    public TeacherResponse getTeacher(@PathVariable Long id) {
        var maybeTeacher = teacherService.getTeacher(id);

        if (maybeTeacher.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            return TeacherToResponseMapper.toResponse(maybeTeacher.get());
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Long addTeacher(@RequestBody @Valid CreateTeacherRequest teacherRequest) {
        return teacherService.addTeacher(teacherRequest);
    }
}