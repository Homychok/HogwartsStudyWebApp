package com.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;

import java.util.Collection;
import java.util.HashMap;

@Service
public class StudentService {

private final HashMap<Long, Student> students = new HashMap<>();
    private long lastId = 0;
    public Student createStudent (Student student) {
        student.setStudentId(++lastId);
        students.put(lastId, student);
        return student;
    }
    public  Student findStudent (long studentId) {
        if (students.containsKey(studentId)) {
            return students.get(studentId);
        }
        return null;
    }
    public Student editStudent(Student student) {
        if (students.containsKey(student.getStudentId())) {
            students.put(student.getStudentId(), student);
            return student;
        }
        return null;
    }
    public Student deleteStudent(long studentId) {
        if (students.containsKey(studentId)) {
            return students.remove(studentId);
        }return null;
    }
    public Collection<Student> getAllStudents() {
        return students.values();
    }
}
