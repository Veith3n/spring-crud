package com.wcy.tai.lab1.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@AllArgsConstructor
@Getter
public class CreateStudentRequest {
    @NotBlank(message = "Name is mandatory")
    private final String name;

    @NotBlank(message = "Surname is mandatory")
    private final String surname;

    private final Optional<Long> teacherId;
}
