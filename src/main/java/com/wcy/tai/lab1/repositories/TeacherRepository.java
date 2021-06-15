package com.wcy.tai.lab1.repositories;

import com.wcy.tai.lab1.data.Teacher;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeacherRepository extends JpaRepository<Teacher, Long> {}
