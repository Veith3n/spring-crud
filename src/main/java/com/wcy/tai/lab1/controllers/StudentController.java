package com.wcy.tai.lab1.controllers;

import com.wcy.tai.lab1.dtos.CreateStudentRequest;
import com.wcy.tai.lab1.dtos.StudentResponse;
import com.wcy.tai.lab1.dtos.UpdateStudentRequest;
import com.wcy.tai.lab1.mappers.StudentToResponseMapper;
import com.wcy.tai.lab1.services.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/students")
public class StudentController {
    private final StudentService studentService;

    @GetMapping()
    public List<StudentResponse> listStudents() {
        return studentService.listStudents();
    }

    @RequestMapping(value = "/{id}")
    public StudentResponse getStudent(@PathVariable Long id) {
        var maybeStudent = studentService.getStudent(id);

        if (maybeStudent.isEmpty())
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        else
            return StudentToResponseMapper.toResponse(maybeStudent.get());
    }

    @DeleteMapping(value = "/{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @PostMapping()
    @ResponseStatus(HttpStatus.CREATED)
    public Long addStudent(@RequestBody @Valid CreateStudentRequest studentRequest) {
        return studentService.addStudent(studentRequest);
    }

    @PutMapping(value = "/{id}")
    public void addStudent(@PathVariable Long id, @RequestBody @Valid UpdateStudentRequest updateStudentRequest) {
        studentService.updateStudent(id, updateStudentRequest);
    }
}