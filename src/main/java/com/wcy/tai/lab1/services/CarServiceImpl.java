package com.wcy.tai.lab1.services;

import com.wcy.tai.lab1.dtos.CarRequest;
import com.wcy.tai.lab1.dtos.CarResponse;
import com.wcy.tai.lab1.mappers.CarToResponseMapper;
import com.wcy.tai.lab1.mappers.RequestToCarMapper;
import com.wcy.tai.lab1.repositories.CarRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class CarServiceImpl implements CarService {

    private final CarRepository carRepository;
    private final CarToResponseMapper carToResponseMapper;
    private final RequestToCarMapper requestToCarMapper;

    @Override
    public List<CarResponse> getAllCars() {
        return carRepository.findAll().stream().map(CarToResponseMapper::toResponse).collect(Collectors.toList());
    }

    @Override
    public void addCar(CarRequest carRequest) {
        carRepository.save(RequestToCarMapper.mapToCar(carRequest));
    }
}
