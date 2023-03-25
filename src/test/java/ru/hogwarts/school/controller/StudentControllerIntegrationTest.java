package ru.hogwarts.school.controller;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;


import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class StudentControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private StudentRepository studentRepository;

    private final Faculty faculty = new Faculty();
    private final JSONObject jsonObject = new JSONObject();
    private final Student student = new Student();

    @BeforeEach
    public void setUp() throws JSONException {
        faculty.setFacultyName("Griffindor");
        faculty.setFacultyColor("green");
        facultyRepository.save(faculty);

        jsonObject.put("name", "Alfred");
        jsonObject.put("age", 25);
        jsonObject.put("facultyId", faculty.getFacultyId());

        student.setStudentName("Victor");
        student.setStudentAge(44);
        student.setFaculty(faculty);
        studentRepository.save(student);
    }

    @Test
    void testCreateStudentReturnsAddedStudent() throws Exception {

        mockMvc.perform(post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Alfred"))
                .andExpect(jsonPath("$.age").value(25))
                .andExpect(jsonPath("$.facultyId").value(faculty.getFacultyId()));

        mockMvc.perform(get("/student?pageNumber=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].name").value("Alfred"))
                .andExpect(jsonPath("$[1].age").value(25))
                .andExpect(jsonPath("$[1].facultyId").value(faculty.getFacultyId()));
    }

    @Test
    void testUpdateStudentReturnsUpdatedStudent() throws Exception {

        jsonObject.put("name", "Cooper");
        jsonObject.put("age", 37);

        mockMvc.perform(patch("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Cooper"))
                .andExpect(jsonPath("$.age").value(37))
                .andExpect(jsonPath("$.facultyId").value(faculty.getFacultyId()));

        mockMvc.perform(get("/student?pageNumber=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].name").value("Cooper"))
                .andExpect(jsonPath("$[1].age").value(37))
                .andExpect(jsonPath("$[1].facultyId").value(faculty.getFacultyId()));
    }

    @Test
    void testDeleteStudentRemovesStudentFromDatabase() throws Exception {

        mockMvc.perform(delete("/student/" + student.getStudentId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/student?pageNumber=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void testGetStudentGetsStudentFromDatabase() throws Exception {

        mockMvc.perform(get("/student/" + student.getStudentId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Victor"))
                .andExpect(jsonPath("$.age").value(44))
                .andExpect(jsonPath("$.facultyId").value(faculty.getFacultyId()));
    }

    @Test
    void testGetStudentsGetsListOfStudentsFromDatabase() throws Exception { //надо ли проверять if'ы?

        mockMvc.perform(get("/student?pageNumber=1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void testGetFacultyByStudentIdReturnsFaculty() throws Exception {

        mockMvc.perform(get("/student/" + student.getStudentId() + "/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Griffindor"))
                .andExpect(jsonPath("$.color").value("green"))
                .andExpect(jsonPath("$.id").value(faculty.getFacultyId()));
    }

//    @Test
//    void testGetTotalStudentCountReturnsCount() throws Exception {
//
//        mockMvc.perform(get("/student/count"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").value(1));
//    }
}
