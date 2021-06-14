package com.wcy.tai.lab1.requests;

import com.wcy.tai.lab1.dtos.CarRequest;
import com.wcy.tai.lab1.dtos.CarResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class CarRequestsTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    public void addsAndListCars() throws Exception {
        var carDto = new CarRequest(1, "foo");

        // DB has empty state
        var cars = getCars();
        assertThat(cars.length).isEqualTo(0);

        // Creates car
        createCar(carDto);

        // Returns car
        cars = getCars();

        assertThat(cars.length).isEqualTo(1);
        assertThat(cars[0].getModel()).isEqualTo("foo");
    }

    private String baseUrl() {
        return "http://localhost:" + port;
    }

    private CarResponse[] getCars() {
        var carGetUrl = baseUrl() + "/";

        var responseEntity = restTemplate.getForEntity(carGetUrl, CarResponse[].class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        return responseEntity.getBody();
    }

    private void createCar(CarRequest carDto) {
        var carPostUrl = baseUrl() + "/add";
        var body = new HttpEntity<>(carDto);

        var res = restTemplate.postForEntity(carPostUrl, body, void.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
