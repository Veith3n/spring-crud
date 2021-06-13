package com.wcy.tai.lab1.mappers;

import com.wcy.tai.lab1.data.Car;
import com.wcy.tai.lab1.data.Producer;
import com.wcy.tai.lab1.dtos.CarRequest;
import org.springframework.stereotype.Component;

@Component
public class RequestToCarMapper {

    public Car mapToCar(CarRequest request){
        Car car = new Car();
        car.setModel(request.getModel());
        car.setProducer(new Producer(request.getProducerId(), null));
        return car;
    }
}
