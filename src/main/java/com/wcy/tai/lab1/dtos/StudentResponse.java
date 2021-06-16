package com.wcy.tai.lab1.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class StudentResponse {
    private final Long id;
    private final String name;
    private final String surname;
    private final Long teacherId;
}
