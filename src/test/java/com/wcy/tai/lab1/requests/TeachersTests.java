package com.wcy.tai.lab1.requests;

import com.wcy.tai.lab1.dtos.CreateTeacherRequest;
import com.wcy.tai.lab1.dtos.TeacherResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeachersTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test()
    public void validatesParamsDuringCreation() {
        var teacherDto = new CreateTeacherRequest("", "foo");

        var res = createTeacher(teacherDto);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(listTeachers().length).isEqualTo(0);
    }

    @Test
    public void createsAndListTeachers() {
        var teacherDto = new CreateTeacherRequest("bar", "foo");

        // DB has empty state
        var teachers = listTeachers();

        assertThat(teachers.length).isEqualTo(0);

        // Creates teacher
        var res = createTeacher(teacherDto);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Returns teacher
        teachers = listTeachers();

        assertThat(teachers.length).isEqualTo(1);
        assertThat(teachers[0].getName()).isEqualTo("bar");
        assertThat(teachers[0].getSurname()).isEqualTo("foo");
        assertThat(teachers[0].getId()).isEqualTo(Long.parseLong(res.getBody()));
    }

    private String baseUrl() {
        return "http://localhost:" + port + "/teachers/";
    }

    private TeacherResponse[] listTeachers() {
        var responseEntity = restTemplate.getForEntity(baseUrl(), TeacherResponse[].class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        return responseEntity.getBody();
    }

    private ResponseEntity<String> createTeacher(CreateTeacherRequest teacherDto) {
        var body = new HttpEntity<>(teacherDto);

        return restTemplate.postForEntity(baseUrl(), body, String.class);
    }
}
