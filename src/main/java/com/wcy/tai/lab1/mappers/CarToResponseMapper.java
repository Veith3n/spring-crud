package com.wcy.tai.lab1.mappers;

import com.wcy.tai.lab1.data.Car;
import com.wcy.tai.lab1.dtos.CarResponse;
import org.springframework.stereotype.Component;

@Component
public class CarToResponseMapper {

    public CarResponse toResponse(Car car){
        return new CarResponse(
                car.getId(),
                car.getProducer().getName(),
                car.getModel()
        );
    }
}
