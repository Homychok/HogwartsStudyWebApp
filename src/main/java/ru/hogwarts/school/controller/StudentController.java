package ru.hogwarts.school.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.StudentService;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("student")
public class StudentController {
    private final StudentService studentService;
    //@Autowired
    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }
    @GetMapping("{id}") // GET http://localhost:8080/student/3
    public ResponseEntity<Student> getStudentInfo (@PathVariable Long studentId) {
        Student student = studentService.findStudent(studentId);
        if (student == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(student);
    }
//    @GetMapping// GET http://localhost:8080/student
//    public ResponseEntity<Collection<Student>> getAllStudents() {
//        return ResponseEntity.ok(studentService.getAllStudents());
//    }

    private List<Student> students = new ArrayList<>();
    @GetMapping
    public ResponseEntity<Collection<Student>> getStudentsByAge(@RequestParam(required=false) Long studentAge) {
        Stream<Student> studentStream = students.stream();
        if (studentAge != null) {
            studentStream = studentStream.filter(student -> student.getStudentId().equals(studentAge));
            return (ResponseEntity<Collection<Student>>) studentStream.collect(Collectors.toList());
        }
        return ResponseEntity.ok(studentService.getAllStudents());
    }

    @PostMapping // POST http://localhost:8080/student
    public Student createStudent (@RequestBody Student student) {
        return studentService.createStudent(student);
    }
    @PutMapping  // PUT http://localhost:8080/student
    public ResponseEntity updateStudent(@RequestBody Student student) {
        Student updatedStudent = studentService.editStudent(student);
        if (updatedStudent == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(updatedStudent);
    }
    @DeleteMapping("{id}") // DELETE http://localhost:8080/student/3
    public ResponseEntity deleteStudent(@PathVariable Long studentId) {
        Student deletedStudent = studentService.deleteStudent(studentId);
        if (deletedStudent == null) {
//            return ResponseEntity.notFound().build();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        return ResponseEntity.ok(deletedStudent);
    }
}
