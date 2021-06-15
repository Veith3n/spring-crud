package com.wcy.tai.lab1.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class TeacherResponse {
    private final long id;
    private final String name;
    private final String surname;
}
