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

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;

@SpringBootTest
@AutoConfigureMockMvc
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
class HouseControllerIntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private FacultyRepository facultyRepository;
    @Autowired
    private StudentRepository studentRepository;

    private final Faculty faculty = new Faculty();
    private final JSONObject jsonObject = new JSONObject();



    @BeforeEach
    public void setUp() throws JSONException {
        faculty.setFacultyName("Griffindor");
        faculty.setFacultyColor("green");
        facultyRepository.save(faculty);

        jsonObject.put("name", "Slizzerin");
        jsonObject.put("color", "yellow");

        Student student1 = new Student();
        student1.setStudentName("Victor");
        student1.setStudentAge(44);
        student1.setFaculty(faculty);
        studentRepository.save(student1);
        Student student2 = new Student();
        student2.setStudentName("Konstantin");
        student2.setStudentAge(55);
        student2.setFaculty(faculty);
        studentRepository.save(student2);

    }

    @Test
    void createFaculty() throws Exception {

        mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Slizzerin"))
                .andExpect(jsonPath("$.color").value("yellow"));

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].name").value("Slizzerin"))
                .andExpect(jsonPath("$[1].color").value("yellow"));
    }

    @Test
    void updateFaculty() throws Exception {

        jsonObject.put("name", "Managment");
        jsonObject.put("color", "orange");

        mockMvc.perform(patch("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value("Managment"))
                .andExpect(jsonPath("$.color").value("orange"));

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].name").value("Managment"))
                .andExpect(jsonPath("$[1].color").value("orange"));
    }

    @Test
    void deleteFaculty() throws Exception {
        studentRepository.deleteAll();

        mockMvc.perform(delete("/faculty/" + faculty.getFacultyId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    void getFaculty() throws Exception {

        mockMvc.perform(get("/faculty/" + faculty.getFacultyId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Griffindor"))
                .andExpect(jsonPath("$.color").value("green"));
    }

    /*@Test
    void getFacultyWhenFacultyNotExist() throws Exception {
        studentRepository.deleteAll();
        facultyRepository.delete(faculty);
        mockMvc.perform(get("/faculty/" + faculty.getId()))
                .andExpect(status().isNotFound());
    }*/

    @Test
    void getFaculties() throws Exception {

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    void getAllStudentsByFacultyId() throws Exception {

        mockMvc.perform(get("/faculty/" + faculty.getFacultyId() + "/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2));
    }
}
