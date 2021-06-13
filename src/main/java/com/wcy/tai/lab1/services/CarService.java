package com.wcy.tai.lab1.services;

import com.wcy.tai.lab1.dtos.CarRequest;
import com.wcy.tai.lab1.dtos.CarResponse;
import java.util.List;

public interface CarService {
    List<CarResponse> getAllCars();
    void addCar(CarRequest carRequest);
}
