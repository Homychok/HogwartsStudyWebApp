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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

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
        faculty.setFacultyName("Звездочки");
        faculty.setFacultyColor("золотой");
        facultyRepository.save(faculty);

        jsonObject.put("name", "Слизерин");
        jsonObject.put("color", "зеленый");

        Student student1 = new Student();
        student1.setStudentName("Geil");
        student1.setStudentAge(12);
        student1.setFaculty(faculty);
        studentRepository.save(student1);
        Student student2 = new Student();
        student2.setStudentName("Tom");
        student2.setStudentAge(21);
        student2.setFaculty(faculty);
        studentRepository.save(student2);

        List<Student> students = new ArrayList<>();
        students.add(student1);
        students.add(student2);
        faculty.setStudents(students);
    }


    @Test
    public void testCreateFaculty() throws Exception {

        mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.facultyName").value("Слизерин"))
                .andExpect(jsonPath("$.facultyColor").value("зеленый"));

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].facultyName").value("Слизерин"))
                .andExpect(jsonPath("$[1].facultyColor").value("зеленый"));
    }

//            @Test
//            public void testUpdateFaculty() throws Exception {
//
//        jsonObject.put("name", "Пуффендуй");
//        jsonObject.put("color", "желтый");
//
//        mockMvc.perform(patch("/faculty")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(jsonObject.toString()))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$.id").isNotEmpty())
//                .andExpect(jsonPath("$.id").isNumber())
//                .andExpect(jsonPath("$.name").value("Пуффендуй"))
//                .andExpect(jsonPath("$.color").value("желтый"));
//
//        mockMvc.perform(get("/faculty"))
//                .andExpect(status().isOk())
//                .andExpect(jsonPath("$").isArray())
//                .andExpect(jsonPath("$.length()").value(2))
//                .andExpect(jsonPath("$[1].name").value("Пуффендуй"))
//                .andExpect(jsonPath("$[1].color").value("желтый"));
//    }

    @Test
    public void testUpdateFaculty() throws Exception {
        jsonObject.put("name", "Слизерин");
        jsonObject.put("color", "зеленый");

        mockMvc.perform(patch("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonObject.toString()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.idF").isNotEmpty())
                .andExpect(jsonPath("$.idF").isNumber())
                .andExpect(jsonPath("$.facultyName").value("Слизерин"))
                .andExpect(jsonPath("$.facultyColor").value("зеленый"));

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[1].facultyName").value("Слизерин"))
                .andExpect(jsonPath("$[1].facultyColor").value("зеленый"));
    }


    @Test
    public void testDeleteFaculty() throws Exception {
        studentRepository.deleteAll();

        mockMvc.perform(delete("/faculty/" + faculty.getFacultyId()))
                .andExpect(status().isOk());

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(0));
    }

    @Test
    public void testGetFaculty() throws Exception {

        mockMvc.perform(get("/faculty/" + faculty.getFacultyId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.facultyName").value("Гриффиндор"))
                .andExpect(jsonPath("$.facultyColor").value("красный"));
    }

    @Test
    public void testGetFacultyWhenFacultyIsEmpty() throws Exception {
        studentRepository.deleteAll();
        faculty.setStudents(Collections.emptyList());
        facultyRepository.delete(faculty);

        mockMvc.perform(get("/faculty/" + faculty.getFacultyId()))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testGetListFaculty() throws Exception {

        mockMvc.perform(get("/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

    @Test
    public void testGetFacultiesByColor() throws Exception {
        String color = "green";

        mockMvc.perform(get("/faculty?definiteColor=" + color))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].facultyName").value(faculty.getFacultyName()))
                .andExpect(jsonPath("$[0].facultyColor").value(faculty.getFacultyColor()));
    }

    @Test
    public void testGetFacultiesByName() throws Exception {

        mockMvc.perform(get("/faculty?name=" + faculty.getFacultyName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].facultyName").value(faculty.getFacultyName()))
                .andExpect(jsonPath("$[0].facultyColor").value(faculty.getFacultyColor()));
    }

    @Test
    public void getAllStudentsByFacultyId() throws Exception {

        mockMvc.perform(get("/faculty/" + faculty.getFacultyId() + "/students"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(faculty.getStudents().size()));
    }
}
