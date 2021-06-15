package com.wcy.tai.lab1.services;

import com.wcy.tai.lab1.data.Teacher;
import com.wcy.tai.lab1.dtos.CreateTeacherRequest;
import com.wcy.tai.lab1.dtos.TeacherResponse;
import com.wcy.tai.lab1.mappers.CreateTeacherRequestToTeacherMapper;
import com.wcy.tai.lab1.mappers.TeacherToResponseMapper;
import com.wcy.tai.lab1.repositories.TeacherRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TeacherServiceImpl implements TeacherService {

    private final TeacherRepository teacherRepository;

    @Override
    public List<TeacherResponse> listTeachers() {
        return teacherRepository.findAll().stream().map(TeacherToResponseMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public Optional<Teacher> getTeacher(Long teacherId) {
        return teacherRepository.findById(teacherId);
    }

    @Override
    public Long addTeacher(CreateTeacherRequest createTeacherRequest) {
        return teacherRepository.save(CreateTeacherRequestToTeacherMapper.mapToTeacher(createTeacherRequest)).getId();
    }
}
