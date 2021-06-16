package com.wcy.tai.lab1.requests;

import com.wcy.tai.lab1.data.Teacher;
import com.wcy.tai.lab1.dtos.CreateStudentRequest;
import com.wcy.tai.lab1.dtos.StudentResponse;
import com.wcy.tai.lab1.dtos.UpdateStudentRequest;
import com.wcy.tai.lab1.repositories.StudentRepository;
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
class StudentsRequestsTests {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private TeacherRepository teacherRepo;

    private Long teacherId;

    @BeforeEach
    void setUp() {
        teacherId = teacherRepo.save(new Teacher(1, "sample", "foo")).getId();
    }

    @AfterEach
    void cleanUp() {
        studentRepository.deleteAll();
        teacherRepo.deleteAll();
    }

    @Test()
    public void validatesParamsDuringCreation() {
        var studentDto = new CreateStudentRequest("", "foo", null);

        var res = createStudent(studentDto);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);
        assertThat(listStudents().length).isEqualTo(0);
    }

    @Test
    public void createsAndListStudents() {
        var studentDto = new CreateStudentRequest("bar", "foo", null);
        var studentDtoWithInvalidTeacherId = new CreateStudentRequest("tail", "fox", Optional.of(2999L));
        var studentDtoWithValidTeacherId = new CreateStudentRequest("boom", "pow", Optional.of(teacherId));

        // DB has empty state
        var students = listStudents();

        assertThat(students.length).isEqualTo(0);

        // Creates student
        var res = createStudent(studentDto);
        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        var res2 = createStudent(studentDtoWithInvalidTeacherId);
        assertThat(res2.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        var res3 = createStudent(studentDtoWithValidTeacherId);
        assertThat(res3.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Returns student
        students = listStudents();

        assertThat(students.length).isEqualTo(3);
        assertThat(students[0].getName()).isEqualTo("bar");
        assertThat(students[0].getSurname()).isEqualTo("foo");
        assertThat(students[0].getId()).isEqualTo(Long.parseLong(res.getBody()));
        assertThat(students[0].getTeacherId()).isEqualTo(null);

        assertThat(students[1].getName()).isEqualTo(studentDtoWithInvalidTeacherId.getName());
        assertThat(students[1].getSurname()).isEqualTo(studentDtoWithInvalidTeacherId.getSurname());
        assertThat(students[1].getId()).isEqualTo(Long.parseLong(res2.getBody()));
        assertThat(students[1].getTeacherId()).isEqualTo(null);

        assertThat(students[2].getName()).isEqualTo(studentDtoWithValidTeacherId.getName());
        assertThat(students[2].getSurname()).isEqualTo(studentDtoWithValidTeacherId.getSurname());
        assertThat(students[2].getId()).isEqualTo(Long.parseLong(res3.getBody()));
        assertThat(students[2].getTeacherId()).isEqualTo(teacherId);
    }

    @Nested
    class TestWithExitingStudent {
        Long studentId;

        @BeforeEach
        void setUp() {
            var studentDto = new CreateStudentRequest("bar", "foo", null);

            var res = createStudent(studentDto);
            assertThat(res.getStatusCode()).isEqualTo(HttpStatus.CREATED);

            studentId = Long.parseLong(res.getBody());
        }

        @AfterEach
        void cleanUp() {
            deleteStudent(studentId);
        }

        @Test
        public void listStudentWithCorrectId() {
            var res = getStudent(studentId);

            assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
            assertThat(res.getBody().getName()).isEqualTo("bar");
            assertThat(res.getBody().getSurname()).isEqualTo("foo");
            assertThat(res.getBody().getTeacherId()).isEqualTo(null);
        }

        @Test
        public void listStudentWithIncorrectId() {
            var res = getStudent(99999999L);

            assertThat(res.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
        }

        @Test
        public void updatesStudentDataWithCorrectId() {
            var updateStudentDto = new UpdateStudentRequest(null, Optional.of("dope"), null);

            updateStudent(studentId, updateStudentDto);

            var studentRes = getStudent(studentId);

            assertThat(studentRes.getBody().getName()).isEqualTo("bar");
            assertThat(studentRes.getBody().getSurname()).isEqualTo("dope");
            assertThat(studentRes.getBody().getTeacherId()).isEqualTo(null);
        }

        @Test
        public void updatesStudentDataWithCorrectIdAndIncorrectTeacherId() {
            var updateStudentDto = new UpdateStudentRequest(null, Optional.of("dope"), Optional.of(99999999L));

            updateStudent(studentId, updateStudentDto);

            var studentRes = getStudent(studentId);

            assertThat(studentRes.getBody().getName()).isEqualTo("bar");
            assertThat(studentRes.getBody().getSurname()).isEqualTo("dope");
            assertThat(studentRes.getBody().getTeacherId()).isEqualTo(null);
        }

        @Test
        public void updatesStudentDataWithIncorrectId() {
            var updateStudentDto = new UpdateStudentRequest(null, Optional.of("dope"), null);

            updateStudent(99999999L, updateStudentDto);
        }

        @Test
        public void deletesStudentWithCorrectId() {
            var res = deleteStudent(studentId);

            assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);

            var students = listStudents();
            assertThat(students.length).isEqualTo(0);
        }

        @Test
        public void deletesStudentWithInCorrectId() {
            var res = deleteStudent(99999999L);

            assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
        }
    }

    private String baseUrl() {
        return "http://localhost:" + port + "/students";
    }

    private StudentResponse[] listStudents() {
        var responseEntity = restTemplate.getForEntity(baseUrl(), StudentResponse[].class);

        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.OK);

        return responseEntity.getBody();
    }

    private ResponseEntity<StudentResponse> getStudent(Long id) {
        var getStudentUrl = String.format("%s/%s", baseUrl(), id);

        return restTemplate.getForEntity(getStudentUrl, StudentResponse.class);
    }

    private ResponseEntity<String> createStudent(CreateStudentRequest studentDto) {
        var body = new HttpEntity<>(studentDto);

        return restTemplate.postForEntity(baseUrl(), body, String.class);
    }

    private ResponseEntity<Void> deleteStudent(Long id) {
        var studentDeleteUrl = String.format("%s/%s", baseUrl(), id);

        return restTemplate.exchange(studentDeleteUrl, HttpMethod.DELETE, null, Void.class);
    }

    private void updateStudent(Long id, UpdateStudentRequest updateStudentDto) {
        var studentUpdateUrl = String.format("%s/%s", baseUrl(), id);

        var body = new HttpEntity<>(updateStudentDto);

        var res = restTemplate.exchange(studentUpdateUrl, HttpMethod.PUT, body, Void.class);

        assertThat(res.getStatusCode()).isEqualTo(HttpStatus.OK);
    }
}
