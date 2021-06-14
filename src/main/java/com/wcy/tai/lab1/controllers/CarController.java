package com.wcy.tai.lab1.controllers;

import com.wcy.tai.lab1.dtos.CarRequest;
import com.wcy.tai.lab1.dtos.CarResponse;
import com.wcy.tai.lab1.services.CarService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController("/cars")
@RequiredArgsConstructor
public class CarController {
    private final CarService carService;

    @GetMapping()
    public List<CarResponse> getAllCars() {
        return carService.getAllCars();
    }

    @PostMapping("/add")
    public void addNewCar(@RequestBody CarRequest carRequest) {
        carService.addCar(carRequest);
    }
}
