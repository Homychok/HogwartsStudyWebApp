package ru.hogwarts.school.controller;

import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;
import ru.hogwarts.school.dto.StudentDTO;
import ru.hogwarts.school.dto.FacultyDTO;

import org.springframework.data.domain.Pageable;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("students")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    //    @GetMapping("{id}") // GET http://localhost:8080/student/3
//    public ResponseEntity<Student> getStudentInfo (@PathVariable Long studentId) {
//       Student student = studentService.findStudent(studentId);
//        if (studentId == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.ok(student);
//    }
///*    @GetMapping// GET http://localhost:8080/student
//    public ResponseEntity<Collection<Student>> getAllStudents() {
//        return ResponseEntity.ok(studentService.getAllStudents());
//    }
// */
//    @GetMapping("/age")
//    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam int studentAge) {
//        return ResponseEntity.ok(studentService.getAllStudents(studentAge));
////                .stream()
////                .filter(student -> student.getStudentAge() == studentAge)
////                .collect(Collectors.toList()));
//    }
//
//    @PostMapping // POST http://localhost:8080/student
//    public Student createStudent (@RequestBody Student student) {
//        return studentService.createStudent(student);
//    }
//    @PutMapping  // PUT http://localhost:8080/student
//    public ResponseEntity updateStudent(@RequestBody Student student) {
//        Student updatedStudent = studentService.updateStudent(student);
//        if (updatedStudent == null) {
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//        return ResponseEntity.ok(updatedStudent);
//    }
//    @DeleteMapping("{id}") // DELETE http://localhost:8080/student/3
//    public ResponseEntity deleteStudent(@PathVariable Long studentId) {
//        studentService.deleteStudent(studentId);
///*        if (deletedStudent == null) {
////            return ResponseEntity.notFound().build();
//            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
//        }
//
// */
//        return ResponseEntity.ok().build();
//    }
    @GetMapping("{studentId}")// GET http://localhost:8080/students/3
    public ResponseEntity<StudentDTO> getStudent(@PathVariable Long studentId) {
        StudentDTO student = studentService.getStudentById(studentId);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student);
    }

    @GetMapping // GET http://localhost:8080/students
    public ResponseEntity<Collection<StudentDTO>> getStudents(@RequestParam(required = false) Integer studentAge,
                                                              @RequestParam(required = false) Integer studentAgeMin, @RequestParam(required = false) Integer studentAgeMax,
                                                              @PageableDefault(size=50) Pageable pageable) {
        if (studentAge != null) {
            return ResponseEntity.ok(studentService.getStudentsByAge(studentAge));
        }
        if (studentAgeMin != null && studentAgeMax != null) {
            return ResponseEntity.ok(studentService.getStudentsByAgeBetween(studentAgeMin, studentAgeMax));
        }
        return ResponseEntity.ok(studentService.getStudents(pageable));
    }

    @GetMapping("{studentId}/students") // GET http://localhost:8080/students/3/students
    public ResponseEntity<FacultyDTO> getFacultyByStudentId(@PathVariable Long studentId) {
        return ResponseEntity.ok(studentService.getFacultyByStudentId(studentId));
    }
    @GetMapping("/sumAges")
    public ResponseEntity<Long> getSumStudentAge() {
        return ResponseEntity.ok(studentService.getSumStudentAge());
    }

    @GetMapping("/averageAge")
    public ResponseEntity<Long> getAverageAge() {
        return ResponseEntity.ok(studentService.getAverageAge());
    }

    @GetMapping("/youngestStudents")
    public ResponseEntity<Collection<StudentDTO>> getFiveYoungestStudents() {
        return ResponseEntity.ok(studentService.getFiveYoungestStudents());
    }
    @PostMapping// POST http://localhost:8080/students
    public ResponseEntity<StudentDTO> createStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO createdStudent = studentService.createStudent(studentDTO);
        return ResponseEntity.ok(createdStudent);
    }

    @PutMapping// PUT http://localhost:8080/students
    public ResponseEntity<StudentDTO> updateStudent(@RequestBody StudentDTO studentDTO) {
        StudentDTO updatedStudent = studentService.updateStudent(studentDTO);
        if (updatedStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updatedStudent);
    }

    @DeleteMapping("{studentId}")// DELETE http://localhost:8080/students/3
    public ResponseEntity<Student> deleteStudent(@PathVariable Long studentId) {
        studentService.deleteStudent(studentId);
        return ResponseEntity.ok().build();
    }


}
