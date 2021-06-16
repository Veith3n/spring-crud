package com.wcy.tai.lab1.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Optional;

@Getter
@AllArgsConstructor
public class UpdateTeacherRequest {
    private final Optional<String> name;

    private final Optional<String> surname;
}
