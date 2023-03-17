package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class StudentService {

    /*private final HashMap<Long, Student> students = new HashMap<>();
       private long lastId = 0; */
    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent (Student student) {
/*        student.setStudentId(++lastId);
//        students.put(lastId, student);
       return student;
       */
        return studentRepository.save(student);
    }
    public  Student findStudent (Long studentId) {
/*        if (students.containsKey(studentId)) {
//            return students.get(studentId);
//        }
       return null;
        */
        return studentRepository.findById(studentId).get();
    }
    public Student updateStudent(Student student) {
/*        if (students.containsKey(student.getStudentId())) {
            students.put(student.getStudentId(), student);
            return student;
        }
        return null;

 */
        return studentRepository.save(student);
    }
    public void deleteStudent(Long studentId) {
/*        if (students.containsKey(studentId)) {
            return students.remove(studentId);
        }return null;

 */
        studentRepository.deleteById(studentId);
    }
    public Collection<Student> getAllStudents(int studentAge) {
        return studentRepository.findAll().stream().filter(student -> student.getStudentAge() == studentAge
        ).collect(Collectors.toList());
    }
    public Collection<Student> getAllStudentsByAge(int studentAge) {
        return studentRepository.findByStudentAge(studentAge);
    }
}
