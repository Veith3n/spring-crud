package com.wcy.tai.lab1.dtos;

import lombok.Data;

@Data
public class CarRequest {
    private long producerId;
    private String model;

    public CarRequest(long producerId, String model) {
        this.producerId = producerId;
        this.model = model;
    }
}
