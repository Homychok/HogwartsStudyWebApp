package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.dto.FacultyDTO;
import ru.hogwarts.school.dto.StudentDTO;


import java.util.Collection;
import java.util.HashMap;
import java.util.stream.Collectors;

@Service
public class StudentService {

    /*private final HashMap<Long, Student> students = new HashMap<>();
       private long lastId = 0; */
    private final StudentRepository studentRepository;
    private FacultyRepository facultyRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    //    public Student createStudent (Student student) {
///*        student.setStudentId(++lastId);
////        students.put(lastId, student);
//       return student;
//       */
//        return studentRepository.save(student);
//    }
//    public  Student findStudent (Long studentId) {
///*        if (students.containsKey(studentId)) {
////            return students.get(studentId);
////        }
//       return null;
//        */
//        return studentRepository.findById(studentId).get();
//    }
//    public Student editStudent(Student student) {
///*        if (students.containsKey(student.getStudentId())) {
//            students.put(student.getStudentId(), student);
//            return student;
//        }
//        return null;
//
// */
//        return studentRepository.save(student);
//    }
//    public void deleteStudent(Long studentId) {
///*        if (students.containsKey(studentId)) {
//            return students.remove(studentId);
//        }return null;
//
// */
//        studentRepository.deleteById(studentId);
//    }
//    public Collection<Student> getAllStudents(int studentAge) {
//        return studentRepository.findAll().stream().filter(student -> student.getStudentAge() == studentAge
//        ).collect(Collectors.toList());
//    }
    @Autowired
    public StudentService(StudentRepository studentRepository, FacultyRepository facultyRepository) {
        this.studentRepository = studentRepository;
        this.facultyRepository = facultyRepository;
    }

    public StudentDTO createStudent(StudentDTO studentDTO) {
        Faculty faculty = facultyRepository.findById(studentDTO.getFacultyId()).get();
        Student student = studentDTO.toStudent();
        student.setFaculty(faculty);
        Student createdStudent = studentRepository.save(student);
        return StudentDTO.fromStudent(createdStudent);
    }

    public StudentDTO updateStudent(StudentDTO studentDTO) {
        Faculty faculty = facultyRepository.findById(studentDTO.getFacultyId()).get();
        Student student = studentDTO.toStudent();
        student.setFaculty(faculty);
        Student updatedStudent = studentRepository.save(student);
        return StudentDTO.fromStudent(updatedStudent);
    }

    public void deleteStudent(Long studentId) {
        studentRepository.deleteById(studentId);
    }

    public StudentDTO getStudentById(Long studentId) {
        return StudentDTO.fromStudent(studentRepository.findById(studentId).get());
    }

    public Collection<StudentDTO> getStudents() {
        return studentRepository.findAll()
                .stream()
                .map(StudentDTO::fromStudent)
                .collect(Collectors.toList());
    }

    public Collection<StudentDTO> getStudentsByAge(int studentAge) {
        return studentRepository.findByStudentAge(studentAge)
                .stream()
                .map(StudentDTO::fromStudent)
                .collect(Collectors.toList());
    }

    public Collection<StudentDTO> getStudentsByAgeBetween(int studentAgeMin, int studentAgeMax) {
        return studentRepository.findByStudentAgeBetween(studentAgeMin, studentAgeMax)
                .stream()
                .map(StudentDTO::fromStudent)
                .collect(Collectors.toList());
    }

    public FacultyDTO getFacultyByStudentId(Long studentId) {
        Faculty faculty = facultyRepository.findById(getStudentById(studentId).getFacultyId()).get();
        return FacultyDTO.fromFaculty(faculty);
    }
}
