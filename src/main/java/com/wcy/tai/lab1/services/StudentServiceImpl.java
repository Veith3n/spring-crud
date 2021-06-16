package com.wcy.tai.lab1.services;

import com.wcy.tai.lab1.data.Student;
import com.wcy.tai.lab1.data.Teacher;
import com.wcy.tai.lab1.dtos.CreateStudentRequest;
import com.wcy.tai.lab1.dtos.StudentResponse;
import com.wcy.tai.lab1.dtos.UpdateStudentRequest;
import com.wcy.tai.lab1.mappers.CreateStudentRequestToStudentMapper;
import com.wcy.tai.lab1.mappers.StudentToResponseMapper;
import com.wcy.tai.lab1.repositories.StudentRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;
    private final TeacherService teacherService;

    @Override
    public List<StudentResponse> listStudents() {
        return studentRepository.findAll().stream().map(StudentToResponseMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public Optional<Student> getStudent(Long studentId) {
        return studentRepository.findById(studentId);
    }

    @Override
    public void deleteStudent(Long studentId) {
        try {
            studentRepository.deleteById(studentId);
        } catch (EmptyResultDataAccessException ignored) {}
    }

    @Override
    public Long addStudent(CreateStudentRequest createStudentRequest) {
        Optional<Teacher> optionalTeacher = Optional.empty();

        if (createStudentRequest.getTeacherId().isPresent())
            optionalTeacher = teacherService.getTeacher(createStudentRequest.getTeacherId().get());

        if (optionalTeacher.isEmpty())
            return studentRepository.save(CreateStudentRequestToStudentMapper.mapToStudent(createStudentRequest)).getId();
        else
            return studentRepository.save(CreateStudentRequestToStudentMapper.mapToStudent(createStudentRequest, optionalTeacher.get())).getId();
    }

    @Override
    public void updateStudent(Long id, UpdateStudentRequest updateStudentRequest) {
        var optionalStudent = getStudent(id);
        Optional<Teacher> optionalTeacher = Optional.empty();

        if (updateStudentRequest.getTeacherId().isPresent())
            optionalTeacher = teacherService.getTeacher(updateStudentRequest.getTeacherId().get());

        Optional<Teacher> finalOptionalTeacher = optionalTeacher;

        optionalStudent.ifPresent(student ->

        {
            updateStudentRequest.getName().ifPresent(student::setName);
            updateStudentRequest.getSurname().ifPresent(student::setSurname);
            finalOptionalTeacher.ifPresent(student::setTeacher);

            studentRepository.save(student);
        });
    }
}
