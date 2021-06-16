package com.wcy.tai.lab1.requests;

import com.wcy.tai.lab1.dtos.CreateTeacherRequest;
import com.wcy.tai.lab1.dtos.TeacherResponse;
import com.wcy.tai.lab1.dtos.UpdateTeacherRequest;
import com.wcy.tai.lab1.repositories.TeacherRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class TeachersTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private TeacherRepository teacherRepo;

    @AfterEach
    void cleanUp() {
        teacherRepo.deleteAll();
    }

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

    @Nested
    class TestWithExitingTeacher {
        Long teacherId;

        @BeforeEach
        void setUp() {
            var teacherDto = new CreateTeacherRequest("bar", "foo");

            var res = createTeacher(teacherDto);
            assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);

            teacherId = Long.parseLong(res.getBody());
        }

        @AfterEach
        void cleanUp() {
            deleteTeacher(teacherId);
        }

        @Test
        public void listTeacherWithCorrectId() {
            var res = getTeacher(teacherId);

            assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(res.getBody().getName()).isEqualTo("bar");
            assertThat(res.getBody().getSurname()).isEqualTo("foo");
        }

        @Test
        public void listTeacherWithIncorrectId() {
            var res = getTeacher(99999999L);

            assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        public void updatesTeacherDataWithCorrectId() {
            var updateTeacherDto = new UpdateTeacherRequest(null, Optional.of("dope"));

            updateTeacher(teacherId, updateTeacherDto);

            var teacherRes = getTeacher(teacherId);

            assertThat(teacherRes.getBody().getName()).isEqualTo("bar");
            assertThat(teacherRes.getBody().getSurname()).isEqualTo("dope");
        }

        @Test
        public void updatesTeacherDataWithIncorrectId() {
            var updateTeacherDto = new UpdateTeacherRequest(null, Optional.of("dope"));

            updateTeacher(99999999L, updateTeacherDto);
        }

        @Test
        public void deletesTeacherWithCorrectId() throws Exception {
            var res = deleteTeacher(teacherId);

            assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);

            var teachers = listTeachers();
            assertThat(teachers.length).isEqualTo(0);
        }

        @Test
        public void deletesTeacherWithInCorrectId() throws Exception {
            var res = deleteTeacher(99999999L);

            assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    private String baseUrl() {
        return "http://localhost:" + port + "/teachers";
    }

    private TeacherResponse[] listTeachers() {
        var responseEntity = restTemplate.getForEntity(baseUrl(), TeacherResponse[].class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        return responseEntity.getBody();
    }

    private ResponseEntity<TeacherResponse> getTeacher(Long id) {
        var getTeacherUrl = String.format("%s/%s", baseUrl(), id);

        return restTemplate.getForEntity(getTeacherUrl, TeacherResponse.class);
    }

    private ResponseEntity<String> createTeacher(CreateTeacherRequest teacherDto) {
        var body = new HttpEntity<>(teacherDto);

        return restTemplate.postForEntity(baseUrl(), body, String.class);
    }

    private ResponseEntity<Void> deleteTeacher(Long id) {
        var teacherDeleteUrl = String.format("%s/%s", baseUrl(), id);

        return restTemplate.exchange(teacherDeleteUrl, HttpMethod.DELETE, null, Void.class);
    }

    private void updateTeacher(Long id, UpdateTeacherRequest updateTeacherDto) {
        var teacherUpdateUrl = String.format("%s/%s", baseUrl(), id);

        var body = new HttpEntity<>(updateTeacherDto);

        var res = restTemplate.exchange(teacherUpdateUrl, HttpMethod.PUT, body, Void.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
