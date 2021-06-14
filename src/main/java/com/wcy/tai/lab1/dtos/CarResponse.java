package com.wcy.tai.lab1.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CarResponse {
    private long id;
    private String producer;
    private String model;
    private Long topSpeed;

    public CarResponse(long id, String producer, String model) {
        this.id = id;
        this.producer = producer;
        this.model = model;
    }
}
